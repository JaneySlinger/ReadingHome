package com.janey.bookstuff.tbr

import com.janey.bookstuff.database.entities.TBRBookEntity
import java.util.Date

data class TBRBook(
    val id: Long,
    val title: String,
    val author: String,
    val pages: Int,
    val genres: Set<Genre>,
    val imageUrl: String,
    val releaseDate: Date? = null,
    val dateAdded: Date = Date(),
    val description: String? = null,
)

fun TBRBookEntity.toTBRBook(): TBRBook = TBRBook(
    id = id,
    title = title,
    author = author,
    pages = pages,
    genres = genres.toGenreSet(),
    imageUrl = imageUrl,
    releaseDate = releaseDate,
    dateAdded = dateAdded,
    description = description,
)