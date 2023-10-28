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

    private val _bookListResourceLiveData: MutableLiveData<BookListResource> = MutableLiveData()
    val bookListResourceLiveData: LiveData<BookListResource> = _bookListResourceLiveData

    private val _highlightedYear = MutableLiveData<Int>()
    val highlightedYear: LiveData<Int> = _highlightedYear

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _bookListResourceLiveData.postValue(bookRepository.getAllBooks())
        }
    }

    fun setHighlightedYear(year: Int) {
        _highlightedYear.value = year
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