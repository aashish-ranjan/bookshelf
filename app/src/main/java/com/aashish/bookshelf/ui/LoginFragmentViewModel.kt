package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    private val userRepository: UserRepository
) : ViewModel() {
    private var _userEmailLiveData: MutableLiveData<String> = MutableLiveData()
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
            val emailError = ValidationUtils.emailValidation(email).message
            val result = if (emailError != null) {
                Resource.Error(emailError)
            } else {
                val user = userRepository.getUserByEmail(email)
                when {
                    user == null -> Resource.Error("Email does not exist")
                    user.password != password -> Resource.Error("Incorrect Password")
                    user.id != INVALID_USER_ID -> {
                        authManager.updateLastLoginUserId(user.id)
                        Resource.Success(user)
                    }

                    else -> Resource.Error("Something went wrong")
                }
            }
            _loginResultLiveData.postValue(result)
        }
    }

    fun logout() {
        authManager.resetLastLoginUserId()
    }
}

class LoginFragmentViewModelFactory(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(authManager, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}