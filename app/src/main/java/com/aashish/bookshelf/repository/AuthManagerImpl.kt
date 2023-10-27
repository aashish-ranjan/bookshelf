package com.aashish.bookshelf.repository

class AuthManagerImpl(private val preferencesRepository: PreferencesRepository): AuthManager {
    override fun getLastLoginUserId(): Int {
        return preferencesRepository.getLastLoginUserId()
    }

    override fun updateLastLoginUserId(userId: Int) {
        preferencesRepository.updateLastLoginUserId(userId)
    }

    override fun resetLastLoginUserId() {
        preferencesRepository.removeLastLoginUserId()
    }
}