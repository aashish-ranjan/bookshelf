package com.aashish.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aashish.bookshelf.BookShelfApplication
import com.aashish.bookshelf.databinding.ActivityBooksBinding
import com.aashish.bookshelf.repository.BookRepository

class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding
    lateinit var bookRepository: BookRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookRepository = (application as BookShelfApplication).bookRepository
    }
}