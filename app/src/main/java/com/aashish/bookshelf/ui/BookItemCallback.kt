package com.aashish.bookshelf.ui

import androidx.recyclerview.widget.DiffUtil
import com.aashish.bookshelf.model.Book

object BookItemCallback: DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}