package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserBookInfoDao
import com.aashish.bookshelf.model.UserBookInfo

class UserBookInfoRepositoryImpl(private val userBookInfoDao: UserBookInfoDao): UserBookInfoRepository {

    override suspend fun addUserBookInfo(userBookInfo: UserBookInfo) {
        userBookInfoDao.insert(userBookInfo)
    }

    override suspend fun updateUserBookInfo(userBookInfo: UserBookInfo) {
        userBookInfoDao.update(userBookInfo)
    }

    override suspend fun getSavedBookInfo(userId: Long, bookId: String): UserBookInfo? {
        return userBookInfoDao.getSavedBookInfo(userId, bookId)
    }
}