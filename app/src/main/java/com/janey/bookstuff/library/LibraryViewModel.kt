package com.janey.bookstuff.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow(LibraryState())

    // TODO replace with database hookup
    val currentCheckouts: Flow<List<String>> = flow {
        emit(
            listOf(
                "Gideon the Ninth",
                "The long way to a small angry planet",
                "The Two Towers",
            )
        )
    }
    val currentHolds: Flow<List<String>> = flow {
        emit(
            listOf(
                "The Martian",
                "Oathbringer",
                "The True Deceiver",
            )
        )
    }

    init {
        viewModelScope.launch {
            currentCheckouts.collect {
                update(state.value.copy(currentCheckouts = it))
            }
            currentHolds.collect {
                update(state.value.copy(holds = it))
            }
        }
    }

    private fun update(newState: LibraryState) {
        state.value = newState
    }

    fun handleEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.CheckoutRemoved -> {
                update(
                    state.value.copy(
                        currentCheckouts = state.value.currentCheckouts.filterNot { it == event.title }
                    )
                )
            }

            is LibraryEvent.BookRenewed -> TODO()
            is LibraryEvent.HoldCheckedOut -> {
                TODO()
                // remove from holds

                // add to current checkouts

            }

            is LibraryEvent.HoldRemoved -> TODO()
        }
    }

}

data class LibraryState(
    val currentCheckouts: List<String> = emptyList(),
    val holds: List<String> = emptyList(),
)

sealed class LibraryEvent {
    data class BookRenewed(val title: String) : LibraryEvent()
    data class CheckoutRemoved(val title: String) : LibraryEvent()
    data class HoldRemoved(val title: String) : LibraryEvent()
    data class HoldCheckedOut(val title: String) : LibraryEvent()
}