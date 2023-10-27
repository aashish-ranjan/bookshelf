package com.aashish.bookshelf.repository

interface AuthManager {
    fun getLastLoginUserId(): Int
    fun updateLastLoginUserId(userId: Int)

    fun resetLastLoginUserId()
}