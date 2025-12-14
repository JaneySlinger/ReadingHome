package com.janey.bookstuff.tbr

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.LocalSharedTransitionScope
import com.janey.bookstuff.R
import com.janey.bookstuff.WithAnimatedContentScope
import com.janey.bookstuff.tbr.addtbr.GenreFilterChips
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.components.BookImage
import com.janey.bookstuff.ui.components.LoadingScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.typography

@Composable
fun TBRScreen(
    viewModel: TBRViewModel = viewModel(),
    onBookClicked: (id: Long) -> Unit,
    onAddBookClicked: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddBookClicked() }
            ) { Icon(painterResource(R.drawable.add), null)}
        }
    ){ padding ->
        TBRScreenContent(
            modifier = Modifier.padding(padding),
            isLoading = state.isLoading,
            books = state.filteredBooks,
            sortType = state.sortType,
            onSortTypeSelected = { viewModel.handleEvent(TBREvent.SortChanged(it)) },
            onGenreSelected = { genre, selected ->
                viewModel.handleEvent(
                    TBREvent.FilterChanged(
                        genre, selected
                    )
                )
            },
            onBookClicked = onBookClicked,
        )
    }

}

@Composable
fun TBRScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    books: List<TBRBook>,
    onGenreSelected: (Genre, Boolean) -> Unit,
    sortType: SortType,
    onSortTypeSelected: (SortType) -> Unit,
    onBookClicked: (id: Long) -> Unit,
) {
    BaseScreen {
        if (isLoading) {
            LoadingScreen()
        } else {
            TBRGrid(
                books = books,
                sortType = sortType,
                onSortTypeSelected = onSortTypeSelected,
                onGenreSelected = onGenreSelected,
                onBookClicked = onBookClicked,
            )
        }
    }
}

@Composable
fun AddFab(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            painterResource(R.drawable.add),
            contentDescription = "Add",
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecentlyReleased() {
    Surface(
        color = MaterialTheme.colorScheme.tertiaryContainer,
        tonalElevation = 150.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Recently Released",
                style = typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "These books you were interested in were released recently, why not check them out?",
                style = typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            FlowRow(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
            ) {
                repeat(3) {
                    Image(
                        painterResource(id = R.drawable.gideon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun SortDropDown(
    modifier: Modifier = Modifier,
    sortType: SortType = SortType.DATE_ADDED,
    onSortTypeSelected: (SortType) -> Unit,
) {
    var sortExpanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        InputChip(selected = false,
            onClick = { sortExpanded = true },
            label = { Text(sortType.title) },
            leadingIcon = { Icon(painterResource(sortType.icon), contentDescription = null) },
            trailingIcon = { Icon(painterResource(R.drawable.arrow_drop_down), null) })
        DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
            SortType.entries.forEach {
                DropdownMenuItem(
                    text = { Text(it.title) },
                    onClick = {
                        onSortTypeSelected(it)
                        sortExpanded = false
                    },
                    leadingIcon = { Icon(painterResource(it.icon), contentDescription = null) },
                    trailingIcon = {
                        if (sortType == it) Icon(painterResource(R.drawable.check), null)
                    }
                )
            }
        }
    }
}

@Composable
fun LayoutOptions(
    modifier: Modifier = Modifier,
    layoutOption: LayoutOptions,
    onLayoutOptionSelected: (LayoutOptions) -> Unit = {}
) {
    LayoutOptions.entries.forEach { option ->
        IconToggleButton(
            checked = layoutOption == option,
            onCheckedChange = { onLayoutOptionSelected(option) },
        ) {
            Icon(painterResource(id = option.icon), contentDescription = option.title)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun TBRGrid(
    books: List<TBRBook>,
    onGenreSelected: (Genre, Boolean) -> Unit,
    sortType: SortType,
    onSortTypeSelected: (SortType) -> Unit,
    onBookClicked: (id: Long) -> Unit,
) {
    var layoutOption by remember { mutableStateOf(LayoutOptions.GRID) }
    val sharedTransitionScope = LocalSharedTransitionScope.current
    with(sharedTransitionScope) {
        WithAnimatedContentScope {
            FlowRow {
                SortDropDown(
                    modifier = Modifier.weight(1f, fill = true),
                    sortType = sortType,
                    onSortTypeSelected = onSortTypeSelected
                )
                LayoutOptions(
                    layoutOption = layoutOption,
                    onLayoutOptionSelected = { layoutOption = it })
            }
            HorizontalDivider()
            GenreFilterChips(onChipSelected = onGenreSelected)
            Column(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(layoutOption, label = "layoutOption") {
                    when (it) {
                        LayoutOptions.GRID -> LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 80.dp),
                            modifier = Modifier.height(600.dp)
                        ) {
                            items(books) { book ->
                                BookImage(
                                    url = book.imageUrl,
                                    height = 150.dp,
                                    modifier = Modifier
                                        .clickable { onBookClicked(book.id) }
                                        .sharedElement(
                                            sharedTransitionScope.rememberSharedContentState(key = book.id),
                                            animatedVisibilityScope = this@WithAnimatedContentScope,
                                        )
                                )
                            }
                        }

                        LayoutOptions.LIST -> TBRListLayout(
                            books = books,
                            onBookClicked = onBookClicked,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TBRListLayout(
    books: List<TBRBook>,
    onBookClicked: (id: Long) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    with(sharedTransitionScope) {
        WithAnimatedContentScope {
            LazyColumn(modifier = Modifier.height(600.dp)) {
                items(books) { book ->
                    Row(modifier = Modifier
                        .clickable { onBookClicked(book.id) }
                        .fillMaxWidth()
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = book.id),
                            animatedVisibilityScope = this@WithAnimatedContentScope,
                        )) {
                        BookImage(
                            url = book.imageUrl,
                            height = 150.dp
                        )
                        Column(modifier = Modifier.padding(start = 4.dp)) {
                            Text(text = book.title, style = typography.headlineSmall)
                            Text(text = book.author, style = typography.bodyMedium)
                            Text(text = "${book.pages} pages", style = typography.bodyMedium)
                            Row() {
                                book.genres.forEach { genre ->
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                                    ) {
                                        Text(
                                            text = genre.title,
                                            style = typography.labelMedium,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TBRScreenPreview() {
    BookStuffTheme {
        TBRScreenContent(
            isLoading = false,
            books = previewBooks,
            sortType = SortType.DATE_ADDED,
            onSortTypeSelected = {},
            onGenreSelected = { _, _ -> },
            onBookClicked = {}
        )
    }
}

@PreviewLightDark
@Composable
fun TBRListPreview() {
    BookStuffTheme {
        BaseScreen {
            TBRListLayout(
                books = previewBooks,
                onBookClicked = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LoadingScreenPreview() {
    BookStuffTheme {
        TBRScreenContent(
            isLoading = true,
            books = previewBooks,
            sortType = SortType.DATE_ADDED,
            onSortTypeSelected = {},
            onGenreSelected = { _, _ -> },
            onBookClicked = {}
        )
    }
}


@PreviewLightDark
@Composable
fun TBRScreenFABPreview() {
    BookStuffTheme {
        AddFab()
    }
}

val previewBooks = listOf(
    TBRBook(
        id = 0,
        title = "Gideon the Ninth",
        author = "Tamsyn Muir",
        pages = 410,
        genres = setOf(Genre.SCI_FI),
        imageUrl = ""
    ),
    TBRBook(
        id = 1,
        title = "My Roommate is a Vampire",
        author = "Jenna Levine",
        pages = 360,
        genres = setOf(Genre.ROMANCE, Genre.FANTASY),
        imageUrl = ""
    ),
    TBRBook(
        id = 2,
        title = "The Two Towers",
        author = "J. R. R. Tolkien",
        pages = 350,
        genres = setOf(Genre.FANTASY),
        imageUrl = ""
    ),
    TBRBook(
        id = 3,
        title = "When Among Crows",
        author = "Veronica Roth",
        pages = 176,
        genres = setOf(Genre.FANTASY),
        imageUrl = ""
    ),
    TBRBook(
        id = 4,
        title = "The Stars Too Fondly",
        author = "Emily Hamilton",
        pages = 336,
        genres = setOf(Genre.SCI_FI, Genre.ROMANCE),
        imageUrl = ""
    ),
)