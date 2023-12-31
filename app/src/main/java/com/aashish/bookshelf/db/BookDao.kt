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

    @Query("SELECT * FROM book_table ORDER BY publicationYear DESC")
    fun getAllBooksSortedByYear(): List<Book>
    
    @Query("SELECT * FROM book_table WHERE title LIKE '%'|| :searchQuery || '%'")
    suspend fun searchBooksByTitle(searchQuery: String): List<Book>

    @Query("SELECT * FROM book_table WHERE id=:bookId")
    suspend fun getBookById(bookId: String): Book?
}