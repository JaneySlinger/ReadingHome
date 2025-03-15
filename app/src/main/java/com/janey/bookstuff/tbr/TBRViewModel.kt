package com.janey.bookstuff.tbr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.tbr.data.TBRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TBRViewModel @Inject constructor(
    private val tbrRepository: TBRRepository,
) : ViewModel() {
    val state = MutableStateFlow(TBRState(previewBooks))

    private var books: List<TBRBook> = emptyList()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO janey remove saving after a few are there
//            tbrRepository.insertBook(
//                TBRBookEntity(
//                    title = "Wind and Truth",
//                    author = "Brandon Sanderson",
//                    imageUrl = "https://books.google.com/books/content?id=OO7pEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
//                    genres = listOf(Genre.SCI_FI.name, Genre.FANTASY.name),
//                    releaseDate = null,
//                    isReleased = true,
//                    reasonForInterest = "5th book in the Stormlight Archive!",
//                    pages = 1304,
//                    dateAdded = Date()
//                )
//            )

            tbrRepository.getBooks().collect { bookResults ->
                val tbrBooks = bookResults.map { it.toTBRBook() }
                books = tbrBooks
                update(state.value.copy(filteredBooks = tbrBooks, isLoading = false))
            }
        }
    }

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

        val filteredBooks = books.filter { book ->
            book.genres.intersect(selectedGenres).isNotEmpty()
        }

        update(state.value.copy(filteredBooks = filteredBooks))
        sortBooks(state.value.sortType)
    }


    private fun TBRBookEntity.toTBRBook(): TBRBook = TBRBook(
        title = title,
        author = author,
        pages = pages,
        genres = genres.toGenreSet(),
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        dateAdded = dateAdded,
    )

    private fun List<String>.toGenreSet(): Set<Genre> = map { Genre.valueOf(it) }.toSet()
}


// TODO should this be shared between different screens?
data class TBRBook(
    val title: String,
    val author: String,
    val pages: Int,
    val genres: Set<Genre>,
    val imageUrl: String,
    val releaseDate: Date? = null,
    val dateAdded: Date = Date(),
)

data class TBRState(
    val filteredBooks: List<TBRBook> = emptyList(),
    val sortType: SortType = SortType.DATE_ADDED,
    val isLoading: Boolean = true,
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