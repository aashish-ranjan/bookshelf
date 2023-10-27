package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.User

interface UserRepository {
    suspend fun getUserById(id: Int): User?

    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    suspend fun registerNewUser(user: User)

    suspend fun getUserEmailById(id: Int): String?

    suspend fun getUserByEmail(email: String): User?
}