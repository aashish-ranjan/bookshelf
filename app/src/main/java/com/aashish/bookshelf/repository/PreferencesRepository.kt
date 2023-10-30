package com.aashish.bookshelf.repository

interface PreferencesRepository {
    fun updateLastLoginUserId(userId: Long)
    fun getLastLoginUserId(): Long
    fun removeLastLoginUserId()


}