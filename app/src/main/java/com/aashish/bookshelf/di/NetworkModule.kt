package com.aashish.bookshelf.di

import com.aashish.bookshelf.api.BookApi
import com.aashish.bookshelf.api.CountryApi
import com.aashish.bookshelf.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit.Builder {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesCountryApi(retrofitBuilder: Retrofit.Builder): CountryApi {
        return retrofitBuilder.baseUrl(Constants.DUMMY_BASE_URL)
            .build()
            .create(CountryApi::class.java)
    }

    @Provides
    @Singleton
    fun providesBookApi(retrofitBuilder: Retrofit.Builder): BookApi {
        return retrofitBuilder.baseUrl(Constants.BOOK_BASE_URL)
            .build()
            .create(BookApi::class.java)
    }
}