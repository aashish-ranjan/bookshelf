package com.aashish.bookshelf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.model.UserBookInfo
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.BookRepository
import com.aashish.bookshelf.repository.UserBookInfoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookDetailFragmentViewModel @AssistedInject constructor(
    private val userBookInfoRepository: UserBookInfoRepository,
    private val bookRepository: BookRepository,
    private val authManager: AuthManager,
    @Assisted private val bookId: String,
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
                userBookInfo = userBookInfoRepository.getSavedBookInfo(authManager.getLastLoginUserId(), bookId)
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
                    UserBookInfo(authManager.getLastLoginUserId(), bookId, isFavourite, notesTagsList )
                )
            }
        }
        super.onCleared()
    }
}

@AssistedFactory
interface BookDetailFragmentViewModelFactory {
    fun create(bookId: String): BookDetailFragmentViewModel
}