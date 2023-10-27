package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.repository.BookRepository
import com.aashish.bookshelf.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias BookListResource = Resource<List<Book>>

class BookListFragmentViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _bookListLiveData: MutableLiveData<BookListResource> = MutableLiveData()
    val bookListLiveData: LiveData<BookListResource> = _bookListLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _bookListLiveData.postValue(bookRepository.getAllBooks())
        }
    }
}

class BookListFragmentViewModelFactory(
    private val bookRepository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListFragmentViewModel::class.java)) {
            return BookListFragmentViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}