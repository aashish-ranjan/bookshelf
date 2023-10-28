package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.UserBookInfo

interface UserBookInfoRepository {
    suspend fun addUserBookInfo(userBookInfo: UserBookInfo)
    suspend fun updateUserBookInfo(userBookInfo: UserBookInfo)
    suspend fun getSavedBookInfo(userId: Long, bookId: String): UserBookInfo?
}