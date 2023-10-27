package com.aashish.bookshelf.model

import com.aashish.bookshelf.utils.DateUtils

data class BookDto(
    val id: String,
    val image: String,
    val score: Double,
    val popularity: Int,
    val title: String,
    val publishedChapterDate: Long
) {
    fun toBook() = Book(
        id = id,
        imageUrl = image,
        score = score,
        title = title,
        publicationYear = DateUtils.epochToYear(publishedChapterDate)
    )
}