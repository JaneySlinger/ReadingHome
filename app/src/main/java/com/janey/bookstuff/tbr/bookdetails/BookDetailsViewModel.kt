package com.janey.bookstuff.tbr.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.tbr.TBRBook
import com.janey.bookstuff.tbr.data.TBRRepository
import com.janey.bookstuff.tbr.toTBRBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetailsState(
    val isLoading: Boolean = true,
    val book: TBRBook? = null,
)

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tbrRepository: TBRRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val bookId: Long? = savedStateHandle.get<Long>("id")

    val state = MutableStateFlow(BookDetailsState())

    init {
        viewModelScope.launch(dispatcher) {
            bookId?.let {
                tbrRepository.getBookById(bookId).collect {
                    if (it != null) {
                        state.update { state -> state.copy(isLoading = false, book = it.toTBRBook()) }
                    }
                }
            }
        }
    }

    fun updateDescription(description: String) {
        state.update { state -> state.copy(
            book = state.book?.copy(description = description)
        ) }
    }

    fun updateReasonToRead(reason: String) {
        state.update { state -> state.copy(
            book = state.book?.copy(reasonToRead = reason)
        ) }
    }

    fun saveChanges() {
        viewModelScope.launch(dispatcher) {
            state.value.book?.let {
                tbrRepository.updateBook(
                    TBRBookEntity(
                        id = state.value.book!!.id,
                        title = state.value.book!!.title,
                        author = state.value.book!!.author,
                        imageUrl = state.value.book!!.imageUrl,
                        genres = state.value.book!!.genres.map { it.title }, // TODO janey
                        releaseDate = state.value.book!!.releaseDate,
                        isReleased = true, // TODO janey
                        reasonForInterest = state.value.book!!.reasonToRead ?: "",
                        pages = state.value.book!!.pages,
                        dateAdded = state.value.book!!.dateAdded,
                        description = state.value.book!!.description
                    )
                )
            }
        }
    }
}