package com.aashish.bookshelf.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aashish.bookshelf.repository.BookRepository

class BookDetailFragmentViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

}

class BookDetailFragmentViewModelFactory(
    private val bookRepository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailFragmentViewModel::class.java)) {
            return BookDetailFragmentViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}