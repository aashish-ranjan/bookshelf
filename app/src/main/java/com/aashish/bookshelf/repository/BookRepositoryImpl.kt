package com.aashish.bookshelf.repository

import com.aashish.bookshelf.api.BookApi
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource
import java.io.IOException

class BookRepositoryImpl(private val bookApi: BookApi) : BookRepository {
    override suspend fun getAllBooks(): Resource<List<Book>> {
        return try {
            val response = bookApi.getAllBooks()
            val books = response.body()?.map { it.toBook() }
            if (response.isSuccessful && books != null) {
                Resource.Success(books)
            } else {
                Resource.Error("Something went wrong")
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Resource.Error("Couldn't reach server, check your internet connection")
                else -> Resource.Error("Something went wrong")
            }
        }
    }

}