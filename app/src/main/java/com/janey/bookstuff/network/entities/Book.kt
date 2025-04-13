package com.janey.bookstuff.network.entities

import com.janey.bookstuff.tbr.Genre

data class Book(
    val title: String,
    val author: String,
    val imageUrl: String? = null,
    val pageCount: Int?,
    val description: String? = null,
    val releaseDate: String? = null,
    val reasonsToRead: String? = null,
    val genres: List<Genre> = emptyList()
)