package com.aashish.bookshelf.ui

interface ResourceProvider {
    fun getString(resId: Int): String
}