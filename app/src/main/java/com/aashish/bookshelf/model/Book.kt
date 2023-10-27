package com.aashish.bookshelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrl: String,
    val score: Double,
    val title: String,
    val publicationYear: Int
)