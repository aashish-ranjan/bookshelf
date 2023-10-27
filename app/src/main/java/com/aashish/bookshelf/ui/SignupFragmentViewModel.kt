package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.UserRepository
import com.aashish.bookshelf.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragmentViewModel(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _signupResultLiveData: MutableLiveData<Resource<User>> = MutableLiveData()
    val signupResultLiveData: LiveData<Resource<User>> = _signupResultLiveData
    fun processSignupCredentials(name: String, email: String, password: String, country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val nameError = nameValidationErrorMsg(name)
            val emailError = emailValidationErrorMsg(email)
            val passwordError = passwordValidationErrorMsg(password)

            val result = when {
                nameError != null -> Resource.Error(nameError)
                emailError != null -> Resource.Error(emailError)
                passwordError != null -> Resource.Error(passwordError)
                else -> {
                    val user = userRepository.getUserByEmail(email)
                    if (user != null) {
                        Resource.Error("Email already exists")
                    } else {
                        val newUser = User(name, email, password, country)
                        val generatedUserId = userRepository.registerNewUser(newUser)
                        authManager.updateLastLoginUserId(generatedUserId)
                        Resource.Success(newUser)
                    }
                }
            }
            _signupResultLiveData.postValue(result)
        }
    }

    private fun nameValidationErrorMsg(name: String): String? {
        return null
    }

    private fun emailValidationErrorMsg(email: String): String? {
        return null
    }

    private fun passwordValidationErrorMsg(password: String): String? {
        return null
    }

}

class SignupFragmentViewModelFactory(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupFragmentViewModel::class.java)) {
            return SignupFragmentViewModel(authManager, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}