package com.aashish.bookshelf

import android.app.Application
import com.aashish.bookshelf.api.RetrofitInstance
import com.aashish.bookshelf.db.BookShelfDatabase
import com.aashish.bookshelf.repository.AuthManagerImpl
import com.aashish.bookshelf.repository.BookRepositoryImpl
import com.aashish.bookshelf.repository.CountryRepositoryImpl
import com.aashish.bookshelf.repository.PreferencesRepository
import com.aashish.bookshelf.repository.UserBookInfoRepositoryImpl
import com.aashish.bookshelf.repository.UserRepositoryImpl

class BookShelfApplication: Application() {

    private val preferencesRepository by lazy {
        PreferencesRepository.getInstance(this)
    }
    val authManager by lazy {
        AuthManagerImpl(preferencesRepository)
    }
    private val bookShelfDatabase by lazy {
        BookShelfDatabase.getInstance(this)
    }
    val userRepository by lazy {
        UserRepositoryImpl(bookShelfDatabase.userDao())
    }

    val countryRepository by lazy {
        CountryRepositoryImpl(RetrofitInstance.countryApi)
    }
    val bookRepository by lazy {
        BookRepositoryImpl(RetrofitInstance.bookApi, bookShelfDatabase.bookDao())
    }

    val userBookInfoRepository by lazy {
        UserBookInfoRepositoryImpl(bookShelfDatabase.userBookInfoDao())
    }
}