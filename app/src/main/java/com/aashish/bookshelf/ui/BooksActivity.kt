package com.aashish.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aashish.bookshelf.BookShelfApplication
import com.aashish.bookshelf.databinding.ActivityBooksBinding
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.BookRepository
import com.aashish.bookshelf.repository.UserBookInfoRepository

class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding
    lateinit var authManager: AuthManager
    lateinit var bookRepository: BookRepository
    lateinit var userBookInfoRepository: UserBookInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userBookInfoRepository = (application as BookShelfApplication).userBookInfoRepository
        authManager = (application as BookShelfApplication).authManager
        bookRepository = (application as BookShelfApplication).bookRepository
    }
}