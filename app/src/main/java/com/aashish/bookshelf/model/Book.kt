package com.aashish.bookshelf.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "book_table")
@Parcelize
data class Book(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrl: String,
    val score: Double,
    val title: String,
    val publicationYear: Int
): Parcelable