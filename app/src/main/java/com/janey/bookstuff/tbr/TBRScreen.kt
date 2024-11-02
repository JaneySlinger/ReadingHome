package com.janey.bookstuff.tbr

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.R
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.Typography

@Composable
fun TBRScreen(
    viewModel: TBRViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BaseScreen {
        TBRGrid(
            books = state.filteredBooks,
            sortType = state.sortType,
            onSortTypeSelected = { viewModel.handleEvent(TBREvent.SortChanged(it)) },
            onGenreSelected = { genre, selected ->
                viewModel.handleEvent(
                    TBREvent.FilterChanged(
                        genre,
                        selected
                    )
                )
            }
        )
    }
}

@Composable
fun TBRScreenFAB(onClick: () -> Unit = {}) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            Icons.Filled.Add,
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
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "These books you were interested in were released recently, why not check them out?",
                style = Typography.bodyMedium,
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
            trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) })
        DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
            SortType.entries.forEach {
                DropdownMenuItem(text = { Text(it.title) },
                    onClick = {
                        onSortTypeSelected(it)
                        sortExpanded = false
                    },
                    leadingIcon = { Icon(painterResource(it.icon), contentDescription = null) },
                    trailingIcon = {
                        if (sortType == it) Icon(Icons.Outlined.Check, null)
                    })
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TBRGrid(
    books: List<TBRBook>,
    onGenreSelected: (Genre, Boolean) -> Unit,
    sortType: SortType,
    onSortTypeSelected: (SortType) -> Unit,
) {
    var layoutOption by remember { mutableStateOf(LayoutOptions.GRID) }
    FlowRow {
        SortDropDown(
            modifier = Modifier.weight(1f, fill = true),
            sortType = sortType,
            onSortTypeSelected = onSortTypeSelected
        )
        LayoutOptions(layoutOption = layoutOption, onLayoutOptionSelected = { layoutOption = it })
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
                        BookImage(book)
                    }
                }

                LayoutOptions.LIST -> TBRListLayout(books)
            }
        }
    }
}

@Composable
private fun TBRListLayout(books: List<TBRBook>) {
    LazyColumn(modifier = Modifier.height(600.dp)) {
        items(books) { book ->
            Row(modifier = Modifier
                .clickable { /*TODO*/ }
                .fillMaxWidth()) {
                BookImage(book)
                Column(modifier = Modifier.padding(start = 4.dp)) {
                    Text(text = book.title, style = Typography.headlineSmall)
                    Text(text = book.author, style = Typography.bodyMedium)
                    Text(text = "${book.pages} pages", style = Typography.bodyMedium)
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
                                    style = Typography.labelMedium,
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

@Composable
private fun BookImage(book: TBRBook) {
    Image(
        painterResource(id = book.image),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .padding(2.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(4.dp))
    )
}


@PreviewLightDark
@Composable
fun TBRScreenPreview() {
    BookStuffTheme {
        TBRScreen()
    }
}

@PreviewLightDark
@Composable
fun TBRScreenFABPreview() {
    BookStuffTheme {
        TBRScreenFAB()
    }
}

@PreviewLightDark
@Composable
fun TBRListPreview() {
    BookStuffTheme {
        TBRListLayout(books = previewBooks)
    }
}


val previewBooks = listOf(
    TBRBook(
        title = "Gideon the Ninth",
        author = "Tamsyn Muir",
        pages = 410,
        genres = setOf(Genre.SCI_FI),
        image = R.drawable.gideon
    ),
    TBRBook(
        title = "My Roommate is a Vampire",
        author = "Jenna Levine",
        pages = 360,
        genres = setOf(Genre.ROMANCE, Genre.FANTASY),
        image = R.drawable.vampire
    ),
    TBRBook(
        title = "The Two Towers",
        author = "J. R. R. Tolkien",
        pages = 350,
        genres = setOf(Genre.FANTASY),
        image = R.drawable.two_towers
    ),
    TBRBook(
        title = "When Among Crows",
        author = "Veronica Roth",
        pages = 176,
        genres = setOf(Genre.FANTASY),
        image = R.drawable.crows
    ),
    TBRBook(
        title = "The Stars Too Fondly",
        author = "Emily Hamilton",
        pages = 336,
        genres = setOf(Genre.SCI_FI, Genre.ROMANCE),
        image = R.drawable.stars_fondly
    ),
)