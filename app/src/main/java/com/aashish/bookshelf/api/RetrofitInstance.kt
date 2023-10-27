package com.aashish.bookshelf.api

import com.aashish.bookshelf.utils.Constants.BOOK_BASE_URL
import com.aashish.bookshelf.utils.Constants.DUMMY_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        private val retrofitBuilder by lazy {
            Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
        }
        val countryApi by lazy {
            retrofitBuilder
                .baseUrl(DUMMY_BASE_URL)
                .build()
                .create(CountryApi::class.java)
        }
        val bookApi by lazy {
            retrofitBuilder
                .baseUrl(BOOK_BASE_URL)
                .build()
                .create(BookApi::class.java)
        }
    }
}