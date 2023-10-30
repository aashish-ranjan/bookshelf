package com.aashish.bookshelf.repository

import android.content.Context
import com.aashish.bookshelf.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(@ApplicationContext context: Context): PreferencesRepository {
    private val sharedPreferences = context.getSharedPreferences(Constants.AUTH_PREF_FILE_NAME, Context.MODE_PRIVATE)
    override fun updateLastLoginUserId(userId: Long) {
        sharedPreferences.edit().apply {
            putLong(Constants.LAST_LOGIN_USER_ID, userId)
            apply()
        }
    }

    override fun getLastLoginUserId(): Long {
        return sharedPreferences.getLong(Constants.LAST_LOGIN_USER_ID, Constants.INVALID_USER_ID)
    }

    override fun removeLastLoginUserId() {
        sharedPreferences.edit().apply {
            remove(Constants.LAST_LOGIN_USER_ID)
            apply()
        }
    }
}