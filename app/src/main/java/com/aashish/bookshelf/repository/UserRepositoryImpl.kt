package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserDao
import com.aashish.bookshelf.model.User

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override suspend fun registerNewUser(user: User) : Long {
        return userDao.insert(user)
    }

    override suspend fun getUserEmailById(id: Long): String? {
        return userDao.getUserEmailById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}