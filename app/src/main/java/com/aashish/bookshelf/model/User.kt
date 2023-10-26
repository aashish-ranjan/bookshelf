package com.aashish.bookshelf.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    val username: String,
    val password: String,
    val email: String? = null,
    val country: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}