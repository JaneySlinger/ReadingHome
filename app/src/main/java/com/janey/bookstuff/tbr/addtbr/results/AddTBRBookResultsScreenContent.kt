package com.janey.bookstuff.tbr.addtbr.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.components.BookImage
import com.janey.bookstuff.ui.components.HorizontalPagerIndicator
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.typography

@Composable
fun AddTBRBookResultsScreen(
    viewModel: TBRResultsViewModel = viewModel(),
    onNavigateToConfirmDetails: (selectedBookId: Long?) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.value.canNavigateForwards) {
        if(state.value.canNavigateForwards && state.value.selectedBookId != null) {
            onNavigateToConfirmDetails(viewModel.state.value.selectedBookId)
            viewModel.resetNavigation()
        }
    }
    if (state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        AddTBRBookResultsScreenContent(
            results = state.value.results,
            onSelectClicked = { bookSelectedIndex ->
                viewModel.saveBook(bookSelectedIndex)
            },
            onEnterManuallyClicked = { TODO() },
        )
    }
}

@Composable
fun AddTBRBookResultsScreenContent(
    results: List<TBRBookSearchResult>,
    onEnterManuallyClicked: () -> Unit = {},
    onSelectClicked: (Int) -> Unit = {},
) {
    BaseScreen(isScrollable = false, modifier = Modifier.padding(horizontal = 8.dp)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (results.size) {
                0 -> Text(
                    "Hmmm there aren't any results for that. You can enter it manually.",
                    style = typography.headlineMedium
                )

                1 -> BookResultComponent(
                    coverUrl = results[0].coverUrl,
                    pageCount = results[0].pageCount,
                    onSelectClicked = { onSelectClicked(0) },
                    modifier = Modifier.width(250.dp)
                )

                else -> MultipleResults(
                    results = results,
                    onSelectClicked = onSelectClicked
                )
            }
        }
        Button(
            onClick = onEnterManuallyClicked,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Enter Manually")
        }
    }
}

@Composable
fun MultipleResults(
    results: List<TBRBookSearchResult>,
    onSelectClicked: (Int) -> Unit = {},
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { results.size }
    )
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Choose the cover and page count to add", style = typography.headlineMedium)
        Text("You can edit this later", style = typography.titleSmall)
        HorizontalPager(
            verticalAlignment = Alignment.Top,
            state = pagerState,
            pageSize = PageSize.Fixed(250.dp)
        ) { page ->
            BookResultComponent(
                coverUrl = results[page].coverUrl,
                pageCount = results[page].pageCount,
                onSelectClicked = { onSelectClicked(page) },
            )
        }
        HorizontalPagerIndicator(pagerState)
    }
}

@Composable
fun BookResultComponent(
    coverUrl: String,
    pageCount: Int?,
    onSelectClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            BookImage(
                url = coverUrl,
                height = 300.dp
            )
            Text("${pageCount ?: "Unknown"} pages")
            Button(
                onClick = onSelectClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AddTBRBookResultsScreenOneResultPreview() {
    BookStuffTheme {
        AddTBRBookResultsScreenContent(
            listOf(
                TBRBookSearchResult(
                    pageCount = 1045,
                    coverUrl = "https://books.google.com/books/content?id=OO7pEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                )
            )
        )
    }
}

@Preview
@Composable
private fun AddTBRBookResultsScreenManyResultPreview() {
    BookStuffTheme {
        AddTBRBookResultsScreenContent(
            listOf(
                TBRBookSearchResult(
                    pageCount = 300,
                    coverUrl = "https://books.google.com/books/content?id=OO7pEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                ),
                TBRBookSearchResult(
                    pageCount = 450,
                    coverUrl = "https://books.google.com/books/content?id=OO7pEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                ),
                TBRBookSearchResult(
                    pageCount = 1000,
                    coverUrl = "https://books.google.com/books/content?id=OO7pEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                )
            )
        )
    }
}

@Preview
@Composable
private fun AddTbrBookNoResultsScreenPreview() {
    BookStuffTheme {
        AddTBRBookResultsScreenContent(
            emptyList()
        )
    }
}