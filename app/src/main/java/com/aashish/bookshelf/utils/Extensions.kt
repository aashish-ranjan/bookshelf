package com.aashish.bookshelf.utils

import android.util.Log

inline fun <T> safeCall(operationName: String, tag: String, action: () -> T): T? {
    return try {
        action()
    } catch (e: Exception) {
        Log.e(tag, "Error in $operationName", e)
        null
    }
}