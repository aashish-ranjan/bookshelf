package com.aashish.bookshelf.repository

import com.aashish.bookshelf.model.User

interface UserRepository {
    suspend fun getUserById(id: Long): User?

    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    suspend fun registerNewUser(user: User): Long

    suspend fun getUserEmailById(id: Long): String?

    suspend fun getUserByEmail(email: String): User?
}