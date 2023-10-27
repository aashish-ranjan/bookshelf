package com.aashish.bookshelf.model

data class Book(
    val id: String,
    val imageUrl: String,
    val score: Double,
    val title: String,
    val publicationYear: Int
)