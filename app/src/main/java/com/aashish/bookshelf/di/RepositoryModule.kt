package com.aashish.bookshelf.di

import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.AuthManagerImpl
import com.aashish.bookshelf.repository.BookRepository
import com.aashish.bookshelf.repository.BookRepositoryImpl
import com.aashish.bookshelf.repository.CountryRepository
import com.aashish.bookshelf.repository.CountryRepositoryImpl
import com.aashish.bookshelf.repository.PreferenceRepositoryImpl
import com.aashish.bookshelf.repository.PreferencesRepository
import com.aashish.bookshelf.repository.UserBookInfoRepository
import com.aashish.bookshelf.repository.UserBookInfoRepositoryImpl
import com.aashish.bookshelf.repository.UserRepository
import com.aashish.bookshelf.repository.UserRepositoryImpl
import com.aashish.bookshelf.ui.AppResourceProvider
import com.aashish.bookshelf.ui.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsBindsBookRepository(impl: BookRepositoryImpl): BookRepository

    @Binds
    abstract fun bindsCountryRepository(impl: CountryRepositoryImpl): CountryRepository

    @Binds
    abstract fun bindsPreferencesRepository(impl: PreferenceRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun bindsUserBookInfoRepository(impl: UserBookInfoRepositoryImpl): UserBookInfoRepository

    @Binds
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsAuthManager(impl: AuthManagerImpl): AuthManager

    @Binds
    abstract fun bindsResourceProvider(impl: AppResourceProvider): ResourceProvider

}