package com.janey.bookstuff.data.mappers

import com.janey.bookstuff.data.entities.Book
import com.janey.bookstuff.network.entities.GoogleBookResult

fun GoogleBookResult.toBooks(): List<Book> {
    return this.items.map {
        Book(
            title = it.volumeInfo.title,
            author = it.volumeInfo.authors.joinToString(", "),
            imageUrl = it.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
            pageCount = it.volumeInfo.pageCount
        )
    }
}