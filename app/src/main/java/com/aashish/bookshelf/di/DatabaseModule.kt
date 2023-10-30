package com.aashish.bookshelf.di

import android.content.Context
import androidx.room.Room
import com.aashish.bookshelf.db.BookShelfDatabase
import com.aashish.bookshelf.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBookShelfDatabase(@ApplicationContext context: Context): BookShelfDatabase {
        return Room.databaseBuilder(
            context,
            BookShelfDatabase::class.java,
            Constants.BOOK_SHELF_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserDao(bookShelfDatabase: BookShelfDatabase) = bookShelfDatabase.userDao()

    @Provides
    @Singleton
    fun providesBookDao(bookShelfDatabase: BookShelfDatabase) = bookShelfDatabase.bookDao()

    @Provides
    @Singleton
    fun providesUserBookInfoDao(bookShelfDatabase: BookShelfDatabase) = bookShelfDatabase.userBookInfoDao()


}