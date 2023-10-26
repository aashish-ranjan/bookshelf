package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _userEmailLiveData: MutableLiveData<String> = MutableLiveData()
    val userEmailLiveData: LiveData<String> = _userEmailLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchLastLoginDetails()
        }
    }

    private suspend fun fetchLastLoginDetails() {
        val lastLoginUserId = authManager.getLastLoginUserId()
        lastLoginUserId?.let { userId ->
            val userEmail = userRepository.getUserEmailById(userId)
            userEmail?.let { email ->
                _userEmailLiveData.postValue(email)
            }
        }
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