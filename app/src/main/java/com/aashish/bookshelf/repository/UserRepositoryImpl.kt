package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserDatabase
import com.aashish.bookshelf.model.User

class UserRepositoryImpl(private val userDatabase: UserDatabase) : UserRepository {

    override suspend fun getUserById(id: String): User? {
        return userDatabase.userDao().getUserById(id)
    }

    override suspend fun getUserByNameAndPassword(username: String, password: String): User? {
        return userDatabase.userDao().getUserByNameAndPassword(username, password)
    }

    override suspend fun registerNewUser(user: User) {
        userDatabase.userDao().register(user)
    }

    override suspend fun getUserEmailById(id: String): String? {
        return userDatabase.userDao().getUserEmailById(id)
    }
}