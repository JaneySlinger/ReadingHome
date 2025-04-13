package com.janey.bookstuff.tbr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.tbr.domain.GetTBRBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBRViewModel @Inject constructor(
    private val getTBRBooksUseCase: GetTBRBooksUseCase,
) : ViewModel() {
    val state = MutableStateFlow(TBRState(previewBooks))
    private var books: List<TBRBook> = emptyList()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            getTBRBooksUseCase().collect {
                books = it
                update(state.value.copy(filteredBooks = books, isLoading = false))
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
}

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