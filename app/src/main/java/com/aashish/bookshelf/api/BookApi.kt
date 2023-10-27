package com.aashish.bookshelf.api

import com.aashish.bookshelf.model.BookDto
import retrofit2.Response
import retrofit2.http.GET

interface BookApi {
    @GET("b/CNGI")
    suspend fun getAllBooks(): Response<List<BookDto>>
}