package com.aashish.bookshelf.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManagerImpl @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    AuthManager {
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