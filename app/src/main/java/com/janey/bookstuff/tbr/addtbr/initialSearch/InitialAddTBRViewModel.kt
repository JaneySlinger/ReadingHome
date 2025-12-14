package com.janey.bookstuff.tbr.addtbr.initialSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.database.entities.TBRBookEntity
import com.janey.bookstuff.tbr.data.TBRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InitialAddTBRState(
    val title: String = "",
    val author: String = "",
)

@HiltViewModel
class InitialAddTBRViewModel @Inject constructor(
    private val tbrRepository: TBRRepository,
) : ViewModel() {
    val state = MutableStateFlow(InitialAddTBRState())

    private fun update(newState: InitialAddTBRState) {
        state.value = newState
    }

    fun onTitleChanged(title: String) {
        update(state.value.copy(title = title))
    }

    fun onAuthorChanged(author: String) {
        update(state.value.copy(author = author))
    }

    fun onQuickAddClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            tbrRepository.insertBook(
                TBRBookEntity(
                    title = state.value.title,
                    author = state.value.author,

                    )
            )
        }
    }
}