package com.aashish.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginFragmentViewModel: ViewModel() {
}

class LoginFragmentViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}