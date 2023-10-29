package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserDao
import com.aashish.bookshelf.model.User
import com.aashish.bookshelf.utils.safeCall

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUserById(id: Long): User? {
        return safeCall("getUserById", TAG) {
            userDao.getUserById(id)
        }
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return safeCall("getUserByEmailAndPassword", TAG) {
            userDao.getUserByEmailAndPassword(email, password)
        }
    }

    override suspend fun registerNewUser(user: User) : Long {
        return userDao.insert(user)
    }

    override suspend fun getUserEmailById(id: Long): String? {
        return safeCall("getUserEmailById", TAG) {
            userDao.getUserEmailById(id)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return safeCall("getUserEmailById", TAG) {
            userDao.getUserByEmail(email)
        }
    }

    companion object {
        private const val TAG = "UserRepositoryImpl"
    }
}