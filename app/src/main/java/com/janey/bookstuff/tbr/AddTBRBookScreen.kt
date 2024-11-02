package com.janey.bookstuff.tbr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTBRBookScreen(
    viewModel: AddTBRBookViewModel = viewModel(),
    listener: (AddTBRBookEvent) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    var isReleased by remember { mutableStateOf(true) }
    BaseScreen {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .background(Color.Yellow)
            )
            Column {
                TextField(
                    label = { Text("Title") },
                    value = state.value.title,
                    onValueChange = { listener(AddTBRBookEvent.TitleChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    label = { Text("Author") },
                    value = state.value.author,
                    onValueChange = { listener(AddTBRBookEvent.AuthorChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        GenreFilterChips(
            defaultChipSelection = false,
            onChipSelected = { genre, selected ->
                listener(
                    AddTBRBookEvent.GenreChanged(
                        genre,
                        selected
                    )
                )
            }
        )
        TextField(
            label = { Text("Why do you want to read it?") },
            value = state.value.reason,
            onValueChange = { listener(AddTBRBookEvent.ReasonChanged(it)) },
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Is released?")
            Checkbox(checked = isReleased, onCheckedChange = { isReleased = !isReleased })
        }

        AnimatedVisibility(visible = !isReleased) {
            // Todo janey store the date from the picker - or just search it
            DatePicker(
                showModeToggle = true,
                state = rememberDatePickerState(
                    initialDisplayMode = DisplayMode.Picker,
                    yearRange = IntRange(2024, 2040)
                )
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { listener(AddTBRBookEvent.SubmitClicked) }) {
                Text("Add to TBR")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreFilterChips(
    modifier: Modifier = Modifier,
    defaultChipSelection: Boolean = true,
    onChipSelected: (Genre, Boolean) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Genre.entries.forEach {
            var selected by remember { mutableStateOf(defaultChipSelection) }
            FilterChip(
                selected = selected,
                onClick = {
                    selected = !selected
                    onChipSelected(it, selected)
                },
                label = { Text(it.title) })
        }
    }
}

@PreviewLightDark
@Composable
fun AddTBRBookScreenPreview() {
    BookStuffTheme {
        AddTBRBookScreen(
            listener = {}
        )
    }
}