package com.janey.bookstuff.tbr.addtbr.results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.bookstuff.data.GoogleBooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TBRResultsState(
    val isLoading: Boolean = true,
    val results: List<TBRBookSearchResult> = emptyList()
)

data class TBRBookSearchResult(
    val pageCount: Int?,
    val coverUrl: String,
)

@HiltViewModel
class TBRResultsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val googleBooksRepository: GoogleBooksRepository,
) : ViewModel() {
    val title = savedStateHandle.get<String>("title") ?: ""
    val author = savedStateHandle.get<String>("author") ?: ""

    val state = MutableStateFlow(TBRResultsState())

    init {
        viewModelScope.launch {
            val results =
                googleBooksRepository.searchBookByTitleAndAuthor(title = title, author = author)
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
}