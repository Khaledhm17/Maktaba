package com.ElOuedUniv.maktaba.data.model

data class Book(
    val isbn: String,
    val title: String,
    val author: String = "Unknown Author",
    val nbPages: Int,
    val currentPage: Int = 0,
    val imageUrl: String? = null,
    val status: ReadingStatus = ReadingStatus.NOT_STARTED
)

enum class ReadingStatus {
    NOT_STARTED,
    READING,
    FINISHED
}
