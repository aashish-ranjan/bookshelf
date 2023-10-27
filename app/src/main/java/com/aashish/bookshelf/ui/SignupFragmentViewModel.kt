package com.aashish.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignupFragmentViewModel: ViewModel() {
}

class SignupFragmentViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupFragmentViewModel::class.java)) {
            return SignupFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}