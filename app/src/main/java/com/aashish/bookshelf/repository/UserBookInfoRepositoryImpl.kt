package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserBookInfoDao
import com.aashish.bookshelf.model.UserBookInfo
import com.aashish.bookshelf.utils.safeCall

class UserBookInfoRepositoryImpl(private val userBookInfoDao: UserBookInfoDao): UserBookInfoRepository {

    override suspend fun addUserBookInfo(userBookInfo: UserBookInfo) {
        safeCall("addUserBookInfo", TAG) {
            userBookInfoDao.insert(userBookInfo)
        }
    }

    override suspend fun updateUserBookInfo(userBookInfo: UserBookInfo) {
        safeCall("updateUserBookInfo", TAG) {
            userBookInfoDao.update(userBookInfo)
        }
    }

    override suspend fun getSavedBookInfo(userId: Long, bookId: String): UserBookInfo? {
        return safeCall("getSavedBookInfo", TAG) {
            userBookInfoDao.getSavedBookInfo(userId, bookId)
        }
    }
    companion object {
        private const val TAG = "UserBookInfoRepositoryImpl"
    }
}