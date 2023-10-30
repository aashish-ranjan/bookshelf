package com.aashish.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aashish.bookshelf.databinding.ActivityBooksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}