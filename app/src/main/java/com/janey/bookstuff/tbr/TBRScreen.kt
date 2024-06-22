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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.janey.bookstuff.R
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.Typography

@Composable
fun TBRScreen() {
    BaseScreen {
//        RecentlyReleased()
        TBRGrid(
            listOf(
                TBRBook(
                    title = "Gideon the Ninth",
                    author = "Tamsyn Muir",
                    pages = 410,
                    genres = listOf("sci-fi"),
                    image = R.drawable.gideon
                ),
                TBRBook(
                    title = "My Roommate is a Vampire",
                    author = "Jenna Levine",
                    pages = 360,
                    genres = listOf("romance", "fantasy"),
                    image = R.drawable.vampire
                ),
                TBRBook(
                    title = "The Two Towers",
                    author = "J. R. R. Tolkien",
                    pages = 350,
                    genres = listOf("fantasy"),
                    image = R.drawable.two_towers
                ),
                TBRBook(
                    title = "When Among Crows",
                    author = "Veronica Roth",
                    pages = 176,
                    genres = listOf("fantasy"),
                    image = R.drawable.crows
                ),
                TBRBook(
                    title = "The Stars Too Fondly",
                    author = "Emily Hamilton",
                    pages = 336,
                    genres = listOf("sci-fi", "romance"),
                    image = R.drawable.stars_fondly
                ),
            )
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
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
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
fun SortDropDown(modifier: Modifier = Modifier) {
    var sortExpanded by remember { mutableStateOf(false) }
    var sortType by remember { mutableStateOf(SortType.DATE_ADDED) }
    Box(modifier = modifier) {
        InputChip(selected = false,
            onClick = { sortExpanded = true },
            label = { Text(sortType.title) },
            leadingIcon = { Icon(painterResource(sortType.icon), contentDescription = null) },
            trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) })
        DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
            SortType.values().forEach {
                DropdownMenuItem(
                    text = { Text(it.title) },
                    onClick = {
                        sortType = it
                        sortExpanded = false
                    },
                    leadingIcon = { Icon(painterResource(it.icon), contentDescription = null) },
                    trailingIcon = {
                        if (sortType == it) Icon(Icons.Outlined.Check, null)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TBRGrid(books: List<TBRBook>) {
    var layoutOption by remember { mutableStateOf(LayoutOptions.GRID) }
    FlowRow {
        SortDropDown(modifier = Modifier.weight(1f, fill = true))
        LayoutOptions(layoutOption = layoutOption,
            onLayoutOptionSelected = { layoutOption = it })
    }
    HorizontalDivider()
    GenreFilterChips()
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(layoutOption, label = "layoutOption") {
            when (it) {
                LayoutOptions.GRID ->
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 80.dp),
                        modifier = Modifier.height(600.dp)
                    ) {
                        items(books) { book ->
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
                    }

                LayoutOptions.LIST ->
                    LazyColumn(modifier = Modifier.height(600.dp)) {
                        items(books) { book ->
                            Row(modifier = Modifier
                                .clickable { /*TODO*/ }
                                .fillMaxWidth()) {
                                Image(
                                    painterResource(id = book.image),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .height(150.dp)
                                        .clip(shape = RoundedCornerShape(4.dp))
                                )
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
                                                    text = genre, style = Typography.labelMedium,
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

data class TBRBook(
    val title: String,
    val author: String,
    val pages: Int,
    val genres: List<String>,
    val image: Int
)