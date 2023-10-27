package com.aashish.bookshelf

import android.app.Application
import com.aashish.bookshelf.api.RetrofitInstance
import com.aashish.bookshelf.db.UserDatabase
import com.aashish.bookshelf.repository.AuthManagerImpl
import com.aashish.bookshelf.repository.CountryRepositoryImpl
import com.aashish.bookshelf.repository.PreferencesRepository
import com.aashish.bookshelf.repository.UserRepositoryImpl

class BookShelfApplication: Application() {

    private val preferencesRepository by lazy {
        PreferencesRepository.getInstance(this)
    }
    val authManager by lazy {
        AuthManagerImpl(preferencesRepository)
    }
    private val userDatabase by lazy {
        UserDatabase.getInstance(this)
    }
    val userRepository by lazy {
        UserRepositoryImpl(userDatabase)
    }

    val countryRepository by lazy {
        CountryRepositoryImpl(RetrofitInstance.countryApi)
    }
}