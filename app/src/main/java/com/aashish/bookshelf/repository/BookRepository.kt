package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource

interface BookRepository {
    suspend fun getAllBooks(): Resource<List<Book>>
}