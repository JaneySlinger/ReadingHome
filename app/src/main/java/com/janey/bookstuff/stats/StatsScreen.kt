package com.janey.bookstuff.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.R
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme
import com.janey.bookstuff.ui.theme.typography

@Composable
fun StatsScreen() {
    BaseScreen {
        StatsDateRangePicker()
        OverviewStats(modifier = Modifier.padding(bottom = 16.dp))
        PageCounts(
            pageCounts = mapOf("Shortest" to 32, "Median" to 320, "Longest" to 1002),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        GenreStats(
            listOf(
                GenreData(
                    "Sci-fi",
                    55f,
                    MaterialTheme.colorScheme.primaryContainer
                ),
                GenreData(
                    "Fantasy",
                    30f,
                    MaterialTheme.colorScheme.tertiary
                ),
                GenreData(
                    "Classic",
                    5f,
                    MaterialTheme.colorScheme.secondaryContainer
                ),
                GenreData(
                    "Romance",
                    10f,
                    MaterialTheme.colorScheme.secondary
                ),
            )
        )
        MonthStats()
    }
}

@Composable
fun StatsDateRangePicker(modifier: Modifier = Modifier) {
    var filterExpanded by remember { mutableStateOf(false) }
    var dateRangeSelected by remember { mutableStateOf("2024") }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
        Box(modifier = modifier) {
            InputChip(selected = false,
                onClick = { filterExpanded = true },
                label = { Text(dateRangeSelected, style = typography.headlineMedium) },
                trailingIcon = { Icon(painterResource(R.drawable.arrow_drop_down), null) })
            DropdownMenu(expanded = filterExpanded, onDismissRequest = { filterExpanded = false }) {
                listOf("2024", "2023", "2022").forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            dateRangeSelected = it
                            filterExpanded = false
                        },
                        trailingIcon = {
                            if (dateRangeSelected == it) Icon(painterResource(R.drawable.check), null)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OverviewStats(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("24", style = typography.headlineLarge)
            Text("Books")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("24,000", style = typography.headlineLarge)
            Text("Pages")
        }

    }


    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painterResource(R.drawable.star_outlined),
            null,
            tint = Color.Yellow,
            modifier = Modifier.size(50.dp)
        )
        Text("Avg 4.4", style = typography.headlineMedium)
    }
}

@Composable
fun PageCounts(
    pageCounts: Map<String, Int>,
    modifier: Modifier = Modifier,
) {
    Text("Page Counts", style = typography.titleLarge)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pageCounts.onEachIndexed { index, (title, pages) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, style = typography.titleMedium)
                Box(
                    modifier = Modifier
                        .width(10.dp * (index + 1))
                        .height(48.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {}
                Text(text = pages.toString(), style = typography.headlineMedium)
                Text("Pages")
            }
        }
    }
}

@Composable
fun FormatStats(modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreStats(chartPoints: List<GenreData>, modifier: Modifier = Modifier) {
    Text(text = "Genres", style = typography.titleLarge)
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = modifier
                .width(250.dp)
                .height(250.dp)
        ) {
            var startAngle = 270f
            var sweepAngle: Float

            chartPoints.forEach { (title, percent, color) ->
                sweepAngle = (percent / 100) * 360
                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                )
                startAngle += sweepAngle
            }
        }
    }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        chartPoints.forEach {
            Box(
                modifier = Modifier
                    .background(color = it.color, shape = RoundedCornerShape(4.dp))
                    .size(24.dp)
            )
            Text(text = it.genre, style = typography.titleMedium)
        }
    }

}

@Composable
fun MonthStats(modifier: Modifier = Modifier) {

}

@PreviewLightDark
@Composable
private fun StatsScreenPreview() {
    BookStuffTheme {
        StatsScreen()
    }
}

data class GenreData(
    val genre: String,
    val percent: Float,
    val color: Color
)