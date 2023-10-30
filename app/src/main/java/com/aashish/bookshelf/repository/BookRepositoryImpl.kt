package com.aashish.bookshelf.repository

import com.aashish.bookshelf.api.BookApi
import com.aashish.bookshelf.db.BookDao
import com.aashish.bookshelf.model.Book
import com.aashish.bookshelf.utils.Resource
import com.aashish.bookshelf.utils.safeCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val bookDao: BookDao
) : BookRepository {
    override suspend fun getAllBooks(): Resource<List<Book>> {
        /* cache valid network result */
        safeCall("getAllBooks", TAG) {
            val remoteResults = bookApi.getAllBooks()
            val books = remoteResults.body()?.map { it.toBook() }
            if (remoteResults.isSuccessful && books != null) {
                bookDao.insert(books)
            }
        }
        /* return valid result from local db */
        safeCall("getAllBooks", TAG) {
            val booksFromLocalDb = bookDao.getAllBooksSortedByYear()
            if (booksFromLocalDb.isNotEmpty()) {
                return@getAllBooks Resource.Success(booksFromLocalDb)
            }
        }

        return Resource.Error("Something went wrong")
    }

    override suspend fun getBookById(bookId: String): Book? {
        return safeCall("getBooksById", TAG) {
            bookDao.getBookById(bookId)
        }
    }

    companion object {
        private const val TAG = "BookRepositoryImpl"
    }
}