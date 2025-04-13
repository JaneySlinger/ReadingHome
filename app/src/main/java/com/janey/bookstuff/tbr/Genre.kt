package com.janey.bookstuff.tbr

// TODO janey make this a string so people can enter them?
enum class Genre(
    val title: String,
) {
    SCI_FI("Sci-Fi"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    NON_FICTION("Non-Fiction"),
    CLASSIC("Classic"),
    CONTEMPORARY("Contemporary"),
}

fun List<String>.toGenreSet(): Set<Genre> = map { Genre.valueOf(it) }.toSet()