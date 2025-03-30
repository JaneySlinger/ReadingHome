package com.janey.bookstuff.tbr.addtbr.results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TBRResultsState(
    val isLoading: Boolean = true,
    val results: List<TBRBookSearchResult> = emptyList()
)

data class TBRBookSearchResult(
    val pageCount: Int,
    val coverUrl: String,
)

@HiltViewModel
class TBRResultsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val title = savedStateHandle.get<String>("title") ?: ""
    val author = savedStateHandle.get<String>("author") ?: ""

    val state = MutableStateFlow(TBRResultsState())

    init {
        viewModelScope.launch {
            // TODO janey get data from endpoint
            delay(2000)
            state.update { it.copy(isLoading = false) }
        }
    }
}