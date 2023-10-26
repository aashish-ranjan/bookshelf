package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.User

interface UserRepository {
    suspend fun getUserById(id: String): User?

    suspend fun getUserByNameAndPassword(username: String, password: String): User?

    suspend fun registerNewUser(user: User)
}