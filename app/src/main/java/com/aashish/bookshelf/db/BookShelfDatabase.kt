package com.aashish.bookshelf.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.model.UserBookInfo

@Database(entities = [User::class, Book::class, UserBookInfo::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class BookShelfDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao

    abstract fun userBookInfoDao(): UserBookInfoDao
}