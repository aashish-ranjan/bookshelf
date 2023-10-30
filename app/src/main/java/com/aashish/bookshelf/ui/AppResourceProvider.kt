package com.aashish.bookshelf.ui

import android.content.Context

class AppResourceProvider(private val context: Context): ResourceProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}