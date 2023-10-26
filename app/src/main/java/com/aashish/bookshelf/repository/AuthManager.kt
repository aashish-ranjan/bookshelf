package com.aashish.bookshelf.repository

interface AuthManager {
    fun getUserId(): String?
    fun markUserLoggedIn(userId: String)

    fun markUserLoggedOut()

    fun isLoggedIn(): Boolean
}