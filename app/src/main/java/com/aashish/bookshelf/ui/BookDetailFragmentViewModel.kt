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
    val markedFavouriteLiveData: LiveData<Boolean> = _markedFavouriteLiveData

    private val _noteLabelListLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val noteLabelListLiveData: LiveData<List<String>> = _noteLabelListLiveData

    fun addNewLabel(label: String) {
        val updatedNoteLabelList = _noteLabelListLiveData.value?.toMutableList() ?: mutableListOf()
        updatedNoteLabelList.add(label)
        _noteLabelListLiveData.value = updatedNoteLabelList
    }

    fun updateLabel(oldLabel: String, newLabel: String) {
        val updatedNoteLabelList = _noteLabelListLiveData.value?.toMutableList() ?: mutableListOf()
        val index = updatedNoteLabelList.indexOf(oldLabel)
        if (index != -1) {
            updatedNoteLabelList[index] = newLabel
        }
        _noteLabelListLiveData.value = updatedNoteLabelList
    }


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