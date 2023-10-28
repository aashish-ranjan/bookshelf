package com.aashish.bookshelf.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aashish.bookshelf.R
import com.aashish.bookshelf.databinding.ItemBookBinding
import com.aashish.bookshelf.model.Book
import com.bumptech.glide.Glide

class BookViewHolder(private val binding: ItemBookBinding): ViewHolder(binding.root) {
    fun bind(book: Book, onItemClick: (String) -> Unit) {
        with(binding) {
            tvTitle.text = book.title
            tvRating.text = book.score.toString()
            tvPublicationYear.text = book.publicationYear.toString()
            Glide.with(root).load(book.imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .into(ivThumbnail)
            root.setOnClickListener {
                onItemClick(book.id)
            }
        }
    }
}