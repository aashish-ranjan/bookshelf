package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource

interface BookRepository {
    suspend fun getAllBooks(): Resource<List<Book>>

    suspend fun getBookById(bookId: String): Book?
    suspend fun searchBooksByTitle(title: String): Resource<List<Book>>
}