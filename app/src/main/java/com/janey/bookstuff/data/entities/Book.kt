package com.janey.bookstuff.data.entities

data class Book(
    val title: String,
    val author: String,
    val imageUrl: String? = null,
    val pageCount: Int?,
)