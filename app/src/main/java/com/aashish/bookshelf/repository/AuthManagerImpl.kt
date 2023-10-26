package com.aashish.bookshelf.repository

class AuthManagerImpl(private val preferencesRepository: PreferencesRepository): AuthManager {
    override fun getLastLoginUserId(): String? {
        return preferencesRepository.getLastLoginUserId()
    }

    override fun updateLastLoginUserId(userId: String) {
        preferencesRepository.updateLastLoginUserId(userId)
    }

    override fun resetLastLoginUserId() {
        preferencesRepository.removeLastLoginUserId()
    }
}