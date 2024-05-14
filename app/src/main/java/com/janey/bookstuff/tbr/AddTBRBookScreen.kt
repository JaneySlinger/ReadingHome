package com.janey.bookstuff.tbr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.janey.bookstuff.R
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTBRBookScreen(
    title: String,
    author: String,
    genre: String, // TODO: update to genre enum?
    bookInfo: String,
//    isReleased: Boolean, // TODO: handle in viewModel
    submitText: String,
    onSubmitClicked: () -> Unit
) {
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
                    label = {Text("Title")},
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    label = {Text("Author")},
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        GenreFilterChips()
        TextField(
            label = {Text("Book info")},
            value = "",
            onValueChange = {},
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Is released?")
            Checkbox(checked = isReleased, onCheckedChange = { isReleased = !isReleased })
        }

        AnimatedVisibility(visible = !isReleased) {
            DatePicker(
                showModeToggle = true,
                state = rememberDatePickerState(
                    initialDisplayMode = DisplayMode.Picker,
                    yearRange = IntRange(2024, 2040)
                )
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }) {
                Text("Add to TBR")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreFilterChips() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
        FilterChip(selected = true, onClick = { /*TODO*/ }, label = { Text("Sci-Fi") })
        FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text("Fantasy") })
        FilterChip(selected = true, onClick = { /*TODO*/ }, label = { Text("Romance") })
        FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text("YA") })
        FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text("Contemporary") })
        FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text("Classic") })
        FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text("Non-Fiction") })
    }
}

@PreviewLightDark
@Composable
fun AddTBRBookScreenPreview() {
    BookStuffTheme {
        AddTBRBookScreen(
           "Title",
            "Author",
            "Sci-fi",
            bookInfo = "",
            submitText = "Submit",
            {}
        )
    }
}