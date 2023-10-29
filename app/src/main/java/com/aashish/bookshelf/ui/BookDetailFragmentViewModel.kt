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
import kotlinx.coroutines.GlobalScope
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
    override fun onCleared() {
        GlobalScope.launch(Dispatchers.IO) {
            val isFavourite = markedFavouriteLiveData.value == true
            val notesTagsList  = noteLabelListLiveData.value ?: listOf()

            if (userBookInfo != null) {
                userBookInfo?.let {
                    userBookInfoRepository.updateUserBookInfo(
                        it.copy(isFavourite = isFavourite, notesTagList = notesTagsList)
                    )
                }
            } else if (isFavourite || notesTagsList.isNotEmpty()) {
                userBookInfoRepository.addUserBookInfo(
                    UserBookInfo(userId, bookId, isFavourite, notesTagsList )
                )
            }
        }
        super.onCleared()
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