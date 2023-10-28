package com.aashish.bookshelf.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aashish.bookshelf.model.Book

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(books: List<Book>)

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): List<Book>

    @Query("SELECT * FROM book_table WHERE title LIKE '%'|| :searchQuery || '%'")
    suspend fun searchBooks(searchQuery: String): List<Book>
}