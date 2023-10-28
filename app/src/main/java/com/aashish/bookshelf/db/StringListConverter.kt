package com.aashish.bookshelf.db

import androidx.room.TypeConverter

object StringListConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<String> = value.split(",").map { it.trim() }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<String>): String = list.joinToString(",")

}