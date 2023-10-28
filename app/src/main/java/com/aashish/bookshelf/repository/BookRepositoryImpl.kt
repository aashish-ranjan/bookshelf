package com.aashish.bookshelf.repository

import com.aashish.bookshelf.api.BookApi
import com.aashish.bookshelf.db.BookDao
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource

class BookRepositoryImpl(private val bookApi: BookApi, private val bookDao: BookDao) : BookRepository {
    override suspend fun getAllBooks(): Resource<List<Book>> {
        try {
            val remoteResults = bookApi.getAllBooks()
            val books = remoteResults.body()?.map { it.toBook() }
            if (remoteResults.isSuccessful && books != null) {
                bookDao.insert(books)
            }
        } catch (e: Exception) { }

        try {
            val booksFromLocalDb = bookDao.getAllBooksSortedByYear()
            if (booksFromLocalDb.isNotEmpty()) {
                return Resource.Success(booksFromLocalDb)
            }
        } catch (e: Exception) { }

        return Resource.Error("Something went wrong", null)
    }

    override suspend fun getBookById(bookId: String): Book? {
        return bookDao.getBookById(bookId)
    }
}