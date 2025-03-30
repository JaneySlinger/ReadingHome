package com.janey.bookstuff.tbr.addtbr.initialSearch

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class InitialAddTBRState(
    val title: String = "",
    val author: String = "",
)

@HiltViewModel
class InitialAddTBRViewModel @Inject constructor() : ViewModel() {
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
}