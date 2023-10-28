package com.aashish.bookshelf.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aashish.bookshelf.databinding.ItemBookBinding
import com.aashish.bookshelf.model.Book

class BookRecyclerViewAdapter(private val onItemClick: (book: Book) -> Unit): ListAdapter<Book, BookViewHolder>(BookItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookItem = getItem(position)
        bookItem?.let {
            holder.bind(it, onItemClick)
        }
    }

    fun getBookAtPosition(position: Int): Book {
        return currentList[position]
    }
}