package com.janey.bookstuff.tbr

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TBRViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow(TBRState(previewBooks))

    private fun update(newState: TBRState) {
        state.value = newState
    }

    fun handleEvent(event: TBREvent) {
        when (event) {
            is TBREvent.SortChanged -> sortBooks(event.sortType)
            is TBREvent.FilterChanged -> filterBooksByGenre(event.selectedGenre)
        }
    }

    private fun sortBooks(sortType: SortType) {
        if (sortType == state.value.sortType) return // no need to sort
        val sortedBooks = when (sortType) {
            SortType.LENGTH -> state.value.filteredBooks.sortedBy { it.pages }
            SortType.DATE_ADDED -> state.value.filteredBooks.sortedBy { it.dateAdded }
            SortType.DATE_RELEASED -> state.value.filteredBooks.sortedBy { it.releaseDate }
            SortType.SHUFFLE -> state.value.filteredBooks.shuffled()
        }

        update(
            state.value.copy(
                filteredBooks = sortedBooks,
                sortType = sortType
            )
        )
    }

    private fun filterBooksByGenre(genreSelection: GenreSelection) {
        // TODO janey - it isn't recomposing when you select one that doesn't change the books
        // toggle whether the selection is on or not
        // then filter the books with the new set

        val genres = state.value.genres
        genres.removeIf { it.genre == genreSelection.genre }
        Log.d("Janey", "genre removed, now $genres")
        genres.add(genreSelection.copy(selected = !genreSelection.selected))
        Log.d("Janey", "Genre added, now $genres")

        val currentlySelectedGenres = genres.filter { it.selected }.map { it.genre }.toSet()

        val filteredBooks = state.value.books.filter { book ->
            book.genres.intersect(currentlySelectedGenres).isNotEmpty()
        }

        update(state.value.copy(filteredBooks = filteredBooks, genres = genres))
    }
}

data class TBRBook(
    val title: String,
    val author: String,
    val pages: Int,
    val genres: Set<Genre>,
    val image: Int,
    val releaseDate: Date = Date(),
    val dateAdded: Date = Date(),
)

data class TBRState(
    val books: List<TBRBook>,
    val filteredBooks: List<TBRBook> = books,
    val sortType: SortType = SortType.DATE_ADDED,
    val genres: GenreSelections = mutableSetOf(
        GenreSelection(Genre.SCI_FI, true),
        GenreSelection(Genre.FANTASY, true),
        GenreSelection(Genre.ROMANCE, true),
        GenreSelection(Genre.NON_FICTION, true),
        GenreSelection(Genre.CLASSIC, true),
        GenreSelection(Genre.CONTEMPORARY, true),
    )
)

sealed class TBREvent {
    class SortChanged(val sortType: SortType) : TBREvent()
    class FilterChanged(val selectedGenre: GenreSelection) : TBREvent()
}

enum class Genre(
    val title: String,
    val selected: Boolean = true,
) {
    SCI_FI("Sci-Fi"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    NON_FICTION("Non-Fiction"),
    CLASSIC("Classic"),
    CONTEMPORARY("Contemporary"),
}

typealias GenreSelections = MutableSet<GenreSelection>

data class GenreSelection(
    val genre: Genre,
    var selected: Boolean
)