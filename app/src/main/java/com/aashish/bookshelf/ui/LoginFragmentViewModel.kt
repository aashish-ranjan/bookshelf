package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.R
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.UserRepository
import com.aashish.bookshelf.utils.Constants.INVALID_USER_ID
import com.aashish.bookshelf.utils.Resource
import com.aashish.bookshelf.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val authManager: AuthManager,
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _userEmailLiveData: MutableLiveData<String> = MutableLiveData()
    val userEmailLiveData: LiveData<String> = _userEmailLiveData
    private val _loginResultLiveData: MutableLiveData<Resource<User>> = MutableLiveData()
    val loginResultLiveData: LiveData<Resource<User>> = _loginResultLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchLastLoginDetails()
        }
    }

    private suspend fun fetchLastLoginDetails() {
        val lastLoginUserId = authManager.getLastLoginUserId()
        if (lastLoginUserId == INVALID_USER_ID) {
            return
        }
        val userEmail = userRepository.getUserEmailById(lastLoginUserId)
        userEmail?.let { email ->
            _userEmailLiveData.postValue(email)
        }
    }

    fun processLoginCredentials(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val emailError = ValidationUtils.emailValidation(email, resourceProvider).message
            val result = if (emailError != null) {
                Resource.Error(emailError)
            } else {
                val user = userRepository.getUserByEmail(email)
                when {
                    user == null -> Resource.Error(resourceProvider.getString(R.string.email_does_not_exist))
                    user.password != password -> Resource.Error(resourceProvider.getString(R.string.incorrect_password))
                    user.id != INVALID_USER_ID -> {
                        authManager.updateLastLoginUserId(user.id)
                        Resource.Success(user)
                    }

                    else -> Resource.Error(resourceProvider.getString(R.string.something_went_wrong))
                }
            }
            _loginResultLiveData.postValue(result)
        }
    }

    fun logout() {
        _userEmailLiveData.value = ""
        authManager.resetLastLoginUserId()

    }
}

class LoginFragmentViewModelFactory(
    private val authManager: AuthManager,
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(authManager, userRepository, resourceProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}