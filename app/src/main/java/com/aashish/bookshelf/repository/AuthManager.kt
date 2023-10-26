package com.aashish.bookshelf.repository

interface AuthManager {
    fun getLastLoginUserId(): String?
    fun updateLastLoginUserId(userId: String)

    fun resetLastLoginUserId()
}