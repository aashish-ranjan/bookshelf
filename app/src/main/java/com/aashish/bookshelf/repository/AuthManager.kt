package com.aashish.bookshelf.repository

interface AuthManager {
    fun getLastLoginUserId(): Long
    fun updateLastLoginUserId(userId: Long)

    fun resetLastLoginUserId()
}