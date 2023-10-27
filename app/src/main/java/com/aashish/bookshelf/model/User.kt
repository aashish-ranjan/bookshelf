package com.aashish.bookshelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    val name: String,
    val email: String,
    val password: String,
    val country: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
)