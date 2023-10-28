package com.aashish.bookshelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_book_info_table")
data class UserBookInfo(
    val userId: Long,
    val bookId: String,
    val isFavourite: Boolean,
    val notesTagList: List<String>,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
)