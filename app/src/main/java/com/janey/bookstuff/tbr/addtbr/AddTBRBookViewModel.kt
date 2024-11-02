package com.janey.bookstuff.tbr.addtbr

import androidx.lifecycle.ViewModel
import com.janey.bookstuff.tbr.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
class AddTBRBookViewModel @Inject constructor(): ViewModel() {
    val state = MutableStateFlow(AddTBRBookState())
    private fun update(newState: AddTBRBookState) {
        state.value = newState
    }

    fun handleEvent(event: AddTBRBookEvent) {
        when (event) {
            is AddTBRBookEvent.AuthorChanged -> TODO()
            is AddTBRBookEvent.GenreChanged -> TODO()
            is AddTBRBookEvent.ReasonChanged -> TODO()
            AddTBRBookEvent.SubmitClicked -> TODO()
            is AddTBRBookEvent.TitleChanged -> TODO()
        }
    }
}

sealed class AddTBRBookEvent {
    data class TitleChanged(val title: String) : AddTBRBookEvent()
    data class AuthorChanged(val author: String) : AddTBRBookEvent()
    data class ReasonChanged(val reason: String) : AddTBRBookEvent()
    data object SubmitClicked : AddTBRBookEvent()
    data class GenreChanged(
        val selectedGenre: Genre,
        val isSelected: Boolean
    ): AddTBRBookEvent()
}
