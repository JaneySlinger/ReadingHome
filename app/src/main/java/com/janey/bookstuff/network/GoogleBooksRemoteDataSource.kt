package com.janey.bookstuff.network

import android.util.Log
import com.janey.bookstuff.network.entities.Book
import com.janey.bookstuff.network.mappers.toBooks
import javax.inject.Inject

class GoogleBooksRemoteDataSource @Inject constructor(
    private val googleBooksApi: GoogleBooksApi
) {
    /**
     * Searches for books by title and author.
     * @param title The title of the book.
     * @param author The author of the book.
     * @return A list of books that match the search query.
     */
    suspend fun searchBookByTitleAndAuthor(
        title: String,
        author: String,
    ): List<Book> {
        val query = listOfNotNull(
            if (title.isNotBlank()) "intitle:$title" else null,
            if (author.isNotBlank()) "inauthor:$author" else null
        ).joinToString("+")

        if (query.isBlank()) return emptyList()

        try {
            return googleBooksApi.getBookFromQuery(
                query = query
            ).toBooks()
        } catch (exception: Exception) {
            Log.d("GoogleBooksRepository", "searchBookByTitleAndAuthor: $exception")
            return emptyList()
        }
    }
}