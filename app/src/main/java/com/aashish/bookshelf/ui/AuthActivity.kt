package com.aashish.bookshelf.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aashish.bookshelf.BookShelfApplication
import com.aashish.bookshelf.databinding.ActivityAuthBinding
import com.aashish.bookshelf.repository.AuthManager
import com.aashish.bookshelf.repository.CountryRepository
import com.aashish.bookshelf.repository.UserRepository

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    lateinit var authManager: AuthManager
    lateinit var userRepository: UserRepository
    lateinit var countryRepository: CountryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authManager = (application as BookShelfApplication).authManager
        userRepository = (application as BookShelfApplication).userRepository
        countryRepository = (application as BookShelfApplication).countryRepository
    }
}