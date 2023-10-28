package com.aashish.bookshelf.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.utils.Constants.BOOK_SHELF_DATABASE_NAME

@Database(entities = [User::class, Book::class], version = 1, exportSchema = false)
abstract class BookShelfDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookShelfDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context)
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookShelfDatabase::class.java,
            BOOK_SHELF_DATABASE_NAME
        ).build()
    }
}