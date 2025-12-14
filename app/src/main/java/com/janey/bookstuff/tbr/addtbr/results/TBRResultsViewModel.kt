package com.janey.bookstuff.tbr.addtbr.results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.network.GoogleBooksRemoteDataSource
import com.janey.bookstuff.network.entities.Book
import com.janey.bookstuff.tbr.data.TBRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class TBRResultsState(
    val isLoading: Boolean = true,
    val results: List<TBRBookSearchResult> = emptyList(),
    val selectedBookId: Long? = null,
    val canNavigateForwards: Boolean = false,
)

data class TBRBookSearchResult(
    val pageCount: Int?,
    val coverUrl: String,
)

@HiltViewModel
class TBRResultsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tbrRepository: TBRRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val googleBooksRemoteDataSource: GoogleBooksRemoteDataSource,
) : ViewModel() {
    val title = savedStateHandle.get<String>("title") ?: ""
    val author = savedStateHandle.get<String>("author") ?: ""

    val state = MutableStateFlow(TBRResultsState())

    private var results: List<Book> = emptyList()

    init {
        viewModelScope.launch(dispatcher) {
            results =
                googleBooksRemoteDataSource.searchBookByTitleAndAuthor(title = title, author = author)
            state.update { state ->
                state.copy(isLoading = false, results = results.map {
                    TBRBookSearchResult(
                        pageCount = it.pageCount,
                        coverUrl = it.imageUrl ?: ""
                    )
                })
            }
        }
    }

    fun saveBook(selectedBook: Int) {
        viewModelScope.launch(dispatcher) {
            // TODO janey handle error case where index isn't in the list
            val book = results[selectedBook]
            val releaseDate = book.releaseDate?.convertBookReleaseDateToDate()
            val isReleased = releaseDate?.before(Date())
            val savedId = tbrRepository.insertBook(
                book = TBRBookEntity(
                    title = book.title,
                    author = book.author,
                    imageUrl = book.imageUrl ?: "",
                    genres = book.genres.map { it.title },
                    releaseDate = book.releaseDate?.convertBookReleaseDateToDate(),
                    isReleased = isReleased,
                    reasonForInterest = book.reasonsToRead ?: "",
                    pages = book.pageCount ?: 0,
                    dateAdded = Date(),
                    description = book.description
                )
            )
            state.update { state -> state.copy(selectedBookId = savedId, canNavigateForwards = true) }
        }
    }

    fun resetNavigation() {
        state.update { state -> state.copy(canNavigateForwards = false) }
    }

    // TODO janey move somewhere else
    /**
     * convert date string in the format yyyy-MM-dd to a Date object
     */
    private fun String.convertBookReleaseDateToDate(): Date? {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        return try {
            dateFormatter.parse(this)
        } catch (e: Exception) {
            null
        }
    }
}