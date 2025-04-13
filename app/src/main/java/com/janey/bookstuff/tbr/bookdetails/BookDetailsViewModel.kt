package com.janey.bookstuff.tbr.bookdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    tbrRepository: TBRRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
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
}