package com.janey.bookstuff.tbr.bookdetails

import android.text.format.DateFormat
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.LocalSharedTransitionScope
import com.janey.bookstuff.R
import com.janey.bookstuff.WithAnimatedContentScope
import com.janey.bookstuff.tbr.Genre
import com.janey.bookstuff.ui.components.BaseScreen
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookDetailsScreen(
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
                    Image(
                        painterResource(id = R.drawable.vampire),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .padding(2.dp)
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .sharedElement(
                                // TODO janey update to book title
                                sharedTransitionScope.rememberSharedContentState(key = "My Roommate is a Vampire"),
                                animatedVisibilityScope = this@WithAnimatedContentScope,
                            )
                    )
                    Badge {
                        Text("The Locked Tomb #1")
                    }
                    ReleaseDate(Date())
                    Genres(listOf(Genre.SCI_FI, Genre.ROMANCE))
                    Text(text = "Gideon the Ninth", style = Typography.titleLarge)
                    Text(text = "Tamsyn Muir", style = Typography.titleSmall)
                    Text("448 pages")
                }
                Card {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Entertaining and dark. Heard good things about it."
                    )
                }

                Text(
                    text = "The Emperor needs necromancers.\n" +
                            "\n" +
                            "The Ninth Necromancer needs a swordswoman.\n" +
                            "\n" +
                            "Gideon has a sword, some dirty magazines, and no more time for undead bullshit.\n" +
                            "\n" +
                            "Brought up by unfriendly, ossifying nuns, ancient retainers, and countless skeletons, Gideon is ready to abandon a life of servitude and an afterlife as a reanimated corpse. She packs up her sword, her shoes, and her dirty magazines, and prepares to launch her daring escape. But her childhood nemesis won't set her free without a service.\n" +
                            "\n" +
                            "Harrowhark Nonagesimus, Reverend Daughter of the Ninth House and bone witch extraordinaire, has been summoned into action. The Emperor has invited the heirs to each of his loyal Houses to a deadly trial of wits and skill. If Harrowhark succeeds she will become an immortal, all-powerful servant of the Resurrection, but no necromancer can ascend without their cavalier. Without Gideon's sword, Harrow will fail, and the Ninth House will die.\n" +
                            "\n" +
                            "Of course, some things are better left dead.",
                    style = Typography.bodyMedium
                )
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
fun ReleaseDate(releaseDate: Date, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(R.drawable.date_range), null)
        Text(DateFormat.format("dd MMM yyyy", releaseDate).toString())
    }
}

@PreviewLightDark
@Composable
private fun BookDetailsScreenPreview() {
    BookStuffTheme {
        BookDetailsScreen()
    }
}