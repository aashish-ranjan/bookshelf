package com.aashish.bookshelf.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Constants

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context)
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,
            Constants.BOOK_DATABASE_NAME
        ).build()
    }
}
