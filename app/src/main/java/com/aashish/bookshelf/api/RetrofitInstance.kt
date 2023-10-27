package com.aashish.bookshelf.api

import com.aashish.bookshelf.utils.Constants.DUMMY_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
        private val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        private val retrofit by lazy {
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(DUMMY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val countryApi by lazy {
            retrofit.create(CountryApi::class.java)
        }
    }
}