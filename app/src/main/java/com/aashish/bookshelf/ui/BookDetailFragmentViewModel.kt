package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aashish.bookshelf.repository.BookRepository

class BookDetailFragmentViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _markedFavouriteLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var markedFavouriteLiveData: LiveData<Boolean> = _markedFavouriteLiveData

    fun toggleFavourite() {
        val isFavourite = _markedFavouriteLiveData.value == true
        _markedFavouriteLiveData.value = !isFavourite
    }
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