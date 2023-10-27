package com.aashish.bookshelf.repository

class AuthManagerImpl(private val preferencesRepository: PreferencesRepository): AuthManager {
    override fun getLastLoginUserId(): Long {
        return preferencesRepository.getLastLoginUserId()
    }

    override fun updateLastLoginUserId(userId: Long) {
        preferencesRepository.updateLastLoginUserId(userId)
    }

    override fun resetLastLoginUserId() {
        preferencesRepository.removeLastLoginUserId()
    }
}