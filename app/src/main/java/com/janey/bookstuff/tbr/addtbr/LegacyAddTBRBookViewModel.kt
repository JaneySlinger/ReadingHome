package com.janey.bookstuff.tbr.addtbr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.tbr.Genre
import com.janey.bookstuff.tbr.data.TBRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class AddTBRBookState(
    val title: String = "",
    val author: String = "",
    val reason: String = "",
//    val pages: Int,
    val genres: Set<Genre> = emptySet(),
    val image: Int? = null,
    val releaseDate: Date? = null,
//    val dateAdded: Date = Date(),
)

@HiltViewModel
class AddTBRBookViewModel @Inject constructor(
    private val tbrRepository: TBRRepository,
): ViewModel() {
    val state = MutableStateFlow(AddTBRBookState())
    private fun update(newState: AddTBRBookState) {
        state.value = newState
    }

    fun handleEvent(event: AddTBRBookEvent) {
        when (event) {
            is AddTBRBookEvent.AuthorChanged -> update(state.value.copy(author = event.author))
            is AddTBRBookEvent.GenreChanged -> TODO()
            is AddTBRBookEvent.ReasonChanged -> update(state.value.copy(reason = event.reason))
            is AddTBRBookEvent.SubmitClicked -> saveTBRBook(event.isReleased)
            is AddTBRBookEvent.TitleChanged -> update(state.value.copy(title = event.title))
        }
    }

    private fun saveTBRBook(isReleased: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tbrRepository.insertBook(TBRBookEntity(
                title = state.value.title,
                author = state.value.author,
                reasonForInterest = state.value.reason,
                isReleased = isReleased,
            ))
        }
    }
}

sealed class AddTBRBookEvent {
    data class TitleChanged(val title: String) : AddTBRBookEvent()
    data class AuthorChanged(val author: String) : AddTBRBookEvent()
    data class ReasonChanged(val reason: String) : AddTBRBookEvent()
    data class SubmitClicked(val isReleased: Boolean) : AddTBRBookEvent()
    data class GenreChanged(
        val selectedGenre: Genre,
        val isSelected: Boolean
    ): AddTBRBookEvent()
}
