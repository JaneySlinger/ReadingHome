package com.janey.bookstuff.tbr

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TBRViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow(TBRState(previewBooks))

    private fun update(newState: TBRState) {
        state.value = newState
    }

    private var selectedGenres: MutableSet<Genre> = Genre.entries.toMutableSet()

    fun handleEvent(event: TBREvent) {
        when (event) {
            is TBREvent.SortChanged -> sortBooks(event.sortType)
            is TBREvent.FilterChanged -> filterBooksByGenre(event.selectedGenre, event.isSelected)
        }
    }

    private fun sortBooks(sortType: SortType) {
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

    private fun filterBooksByGenre(genreSelection: Genre, isSelected: Boolean) {
        if (isSelected) {
            selectedGenres.add(genreSelection)
        } else {
            selectedGenres.remove(genreSelection)
        }

        val filteredBooks = state.value.books.filter { book ->
            book.genres.intersect(selectedGenres).isNotEmpty()
        }

        update(state.value.copy(filteredBooks = filteredBooks))
        sortBooks(state.value.sortType)
    }
}


// TODO should this be shared between different screens?
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
)

sealed class TBREvent {
    data class SortChanged(val sortType: SortType) : TBREvent()
    data class FilterChanged(
        val selectedGenre: Genre,
        val isSelected: Boolean
    ) : TBREvent()
}

// TODO janey make this a broader used enum
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