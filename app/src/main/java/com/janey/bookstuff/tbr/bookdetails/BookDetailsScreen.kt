package com.janey.bookstuff.tbr.bookdetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.LocalSharedTransitionScope
import com.janey.bookstuff.R
import com.janey.bookstuff.WithAnimatedContentScope
import com.janey.bookstuff.tbr.Genre
import com.janey.bookstuff.tbr.TBRBook
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.components.BookImage
import com.janey.bookstuff.ui.components.LoadingScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.Typography
import java.util.Date

/*
- Update
- Delete
- See release date if not already out
- See genres
- See why I wanted to read it
 */

@Composable
fun BookDetailsScreen(
    viewModel: BookDetailsViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    when {
        state.value.isLoading -> LoadingScreen()
        state.value.book != null -> BookDetailsScreenContent(
            book = state.value.book!!
        )

        else -> Text("Oops something went wrong")
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookDetailsScreenContent(
    book: TBRBook,
    modifier: Modifier = Modifier
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    with(sharedTransitionScope) {
        WithAnimatedContentScope {
            BaseScreen {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BookImage(
                        height = 200.dp,
                        url = book.imageUrl ?: "",
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = book.id),
                                animatedVisibilityScope = this@WithAnimatedContentScope,
                            )
                    )
                    ReleaseDate((book.releaseDate ?: "Unknown").toString())
                    // TODO fix
//                    Genres(book.genres)
                    Text(text = book.title, style = Typography.titleLarge)
                    Text(text = book.author, style = Typography.titleSmall)
                    Text("${book.pages} pages")
                }
                // TODO fix
//                book.reasonsToRead?.let {
//                    Card {
//                        Text(
//                            modifier = Modifier.padding(8.dp),
//                            text = it
//                        )
//                    }
//                }
                // TODO fix
//                book.description?.let {
//                    Text(
//                        text = book.description,
//                        style = Typography.bodyMedium
//                    )
//                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Genres(genres: List<Genre>, modifier: Modifier = Modifier) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        genres.map {
            Badge {
                Text(it.title)
            }
        }
    }
}

@Composable
fun ReleaseDate(releaseDate: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(R.drawable.date_range), null)
        Text(releaseDate)
        // TODO janey format the date from the service somewhere
//        Text(DateFormat.format("dd MMM yyyy", releaseDate).toString())
    }
}

@PreviewLightDark
@Composable
private fun BookDetailsScreenPreview() {
    BookStuffTheme {
        BookDetailsScreenContent(
            TBRBook(
                id = 1,
                title = "Gideon the Ninth",
                author = "Tamsyn Muir",
                imageUrl = "",
                pages = 355,
//                description = previewBookDescription,
//                releaseDate = "2024-03-05",
                releaseDate = Date(),
//                reasonsToRead = "Entertaining and dark. Heard good things about it.",
                genres = setOf(Genre.SCI_FI, Genre.ROMANCE),
            )
        )
    }
}

private const val previewBookDescription = "The Emperor needs necromancers.\n" +
        "\n" +
        "The Ninth Necromancer needs a swordswoman.\n" +
        "\n" +
        "Gideon has a sword, some dirty magazines, and no more time for undead bullshit.\n" +
        "\n" +
        "Brought up by unfriendly, ossifying nuns, ancient retainers, and countless skeletons, Gideon is ready to abandon a life of servitude and an afterlife as a reanimated corpse. She packs up her sword, her shoes, and her dirty magazines, and prepares to launch her daring escape. But her childhood nemesis won't set her free without a service.\n" +
        "\n" +
        "Harrowhark Nonagesimus, Reverend Daughter of the Ninth House and bone witch extraordinaire, has been summoned into action. The Emperor has invited the heirs to each of his loyal Houses to a deadly trial of wits and skill. If Harrowhark succeeds she will become an immortal, all-powerful servant of the Resurrection, but no necromancer can ascend without their cavalier. Without Gideon's sword, Harrow will fail, and the Ninth House will die.\n" +
        "\n" +
        "Of course, some things are better left dead."