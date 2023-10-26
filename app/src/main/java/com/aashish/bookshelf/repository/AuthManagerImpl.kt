package com.aashish.bookshelf.repository

import android.content.SharedPreferences
import com.aashish.bookshelf.utils.Constants.USER_ID

class AuthManagerImpl(private val sharedPreferences: SharedPreferences): AuthManager {
    override fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, null)
    }

    override fun markUserLoggedIn(userId: String) {
        sharedPreferences.edit().apply {
            putString(USER_ID, userId)
            apply()
        }
    }

    override fun markUserLoggedOut() {
        sharedPreferences.edit().apply {
            remove(USER_ID)
            apply()
        }
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getString(USER_ID, null) != null
    }
}