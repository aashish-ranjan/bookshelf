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
            val emailError = emailValidationErrorMsg(email)
            val passwordError = passwordValidationErrorMsg(password)

            val result = when {
                emailError != null -> Resource.Error(emailError)
                passwordError != null -> Resource.Error(passwordError)
                else -> {
                    val user = userRepository.getUserByEmail(email)
                    when {
                        user == null -> Resource.Error("Email does not exist")
                        user.password != password -> Resource.Error("Incorrect Password")
                        else -> {
                            user.id?.let {userId ->
                                authManager.updateLastLoginUserId(userId)
                                Resource.Success(user)
                            } ?: Resource.Error("Something went wrong")
                        }
                    }
                }
            }
            _loginResultLiveData.postValue(result)
        }
    }


    private fun emailValidationErrorMsg(email: String): String? {
        return null
    }

    private fun passwordValidationErrorMsg(password: String): String? {
        return null
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