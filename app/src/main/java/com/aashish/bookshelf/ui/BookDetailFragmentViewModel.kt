package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.model.UserBookInfo
import com.aashish.bookshelf.repository.BookRepository
import com.aashish.bookshelf.repository.UserBookInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookDetailFragmentViewModel(
    private val bookId: String,
    private val userId: Long,
    private val userBookInfoRepository: UserBookInfoRepository,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _markedFavouriteLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val markedFavouriteLiveData: LiveData<Boolean> = _markedFavouriteLiveData

    private val _noteLabelListLiveData: MutableLiveData<List<String>> = MutableLiveData()
    val noteLabelListLiveData: LiveData<List<String>> = _noteLabelListLiveData

    private val _bookLiveData: MutableLiveData<Book> = MutableLiveData()
    val bookLiveData: LiveData<Book> = _bookLiveData

    private var userBookInfo: UserBookInfo? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                val book = bookRepository.getBookById(bookId)
                book?.let {
                    _bookLiveData.postValue(it)
                }
            }
            launch {
                userBookInfo = userBookInfoRepository.getSavedBookInfo(userId, bookId)
                userBookInfo?.let {
                    _markedFavouriteLiveData.postValue(it.isFavourite)
                    _noteLabelListLiveData.postValue(it.notesTagList)
                }
            }
        }
    }

    fun addNewLabel(label: String) {
        val updatedNoteLabelList = _noteLabelListLiveData.value?.toMutableList() ?: mutableListOf()
        updatedNoteLabelList.add(label)
        _noteLabelListLiveData.value = updatedNoteLabelList
        updateUserBookInfo()
    }

    fun updateLabel(oldLabel: String, newLabel: String) {
        val updatedNoteLabelList = _noteLabelListLiveData.value?.toMutableList() ?: mutableListOf()
        val index = updatedNoteLabelList.indexOf(oldLabel)
        if (index != -1) {
            updatedNoteLabelList[index] = newLabel
        }
        _noteLabelListLiveData.value = updatedNoteLabelList
        updateUserBookInfo()
    }

    private fun updateUserBookInfo() {

        viewModelScope.launch(Dispatchers.IO) {
            if (userBookInfo == null) {
                userBookInfoRepository.addUserBookInfo(
                    UserBookInfo(
                        userId,
                        bookId,
                        markedFavouriteLiveData.value == true,
                        noteLabelListLiveData.value ?: mutableListOf()
                    )
                )
            } else {
                userBookInfo?.let {
                    userBookInfoRepository.updateUserBookInfo(
                        it.copy(
                            isFavourite = markedFavouriteLiveData.value == true,
                            notesTagList = noteLabelListLiveData.value ?: mutableListOf()
                        )
                    )
                }
            }
        }
    }


    fun toggleFavourite() {
        val isFavourite = _markedFavouriteLiveData.value == true
        _markedFavouriteLiveData.value = !isFavourite
        updateUserBookInfo()
    }
}

class BookDetailFragmentViewModelFactory(
    private val bookId: String,
    private val userId: Long,
    private val userBookInfoRepository: UserBookInfoRepository,
    private val bookRepository: BookRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookDetailFragmentViewModel::class.java)) {
            return BookDetailFragmentViewModel(
                bookId,
                userId,
                userBookInfoRepository,
                bookRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}