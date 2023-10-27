package com.aashish.bookshelf.repository

import android.content.Context
import com.aashish.bookshelf.utils.Constants
import com.aashish.bookshelf.utils.Constants.AUTH_PREF_FILE_NAME
import com.aashish.bookshelf.utils.Constants.INVALID_USER_ID

class PreferencesRepository private constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(AUTH_PREF_FILE_NAME, Context.MODE_PRIVATE)
    fun updateLastLoginUserId(userId: Long) {
        sharedPreferences.edit().apply {
            putLong(Constants.LAST_LOGIN_USER_ID, userId)
            apply()
        }
    }

    fun getLastLoginUserId(): Long {
        return sharedPreferences.getLong(Constants.LAST_LOGIN_USER_ID, INVALID_USER_ID)
    }

    fun removeLastLoginUserId() {
        sharedPreferences.edit().apply {
            remove(Constants.LAST_LOGIN_USER_ID)
            apply()
        }
    }

    companion object {
        @Volatile
        private var instance: PreferencesRepository? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: PreferencesRepository(context.applicationContext)
        }
    }
}