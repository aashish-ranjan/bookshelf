package com.aashish.bookshelf.repository

import com.aashish.bookshelf.db.UserDatabase
import com.aashish.bookshelf.model.User

class UserRepositoryImpl(private val userDatabase: UserDatabase) : UserRepository {

    override suspend fun getUserById(id: Long): User? {
        return userDatabase.userDao().getUserById(id)
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDatabase.userDao().getUserByEmailAndPassword(email, password)
    }

    override suspend fun registerNewUser(user: User) : Long {
        return userDatabase.userDao().insert(user)
    }

    override suspend fun getUserEmailById(id: Long): String? {
        return userDatabase.userDao().getUserEmailById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDatabase.userDao().getUserByEmail(email)
    }
}