package com.aashish.bookshelf.repository

import android.content.Context
import com.aashish.bookshelf.utils.Constants
import com.aashish.bookshelf.utils.Constants.AUTH_PREF_FILE_NAME

class PreferencesRepository private constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(AUTH_PREF_FILE_NAME, Context.MODE_PRIVATE)
    fun updateLastLoginUserId(userId: String) {
        sharedPreferences.edit().apply {
            putString(Constants.LAST_LOGIN_USER_ID, userId)
            apply()
        }
    }

    fun getLastLoginUserId(): String? {
        return sharedPreferences.getString(Constants.LAST_LOGIN_USER_ID, null)
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