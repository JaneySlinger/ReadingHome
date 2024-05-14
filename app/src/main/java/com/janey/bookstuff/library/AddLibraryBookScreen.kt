package com.janey.bookstuff.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLibraryBookScreen(modifier: Modifier = Modifier) {
    BaseScreen(modifier = modifier) {
        var type by remember { mutableStateOf(Type.CHECKOUT) }
        var format by remember { mutableStateOf(Format.PHYSICAL) }
        var title by remember { mutableStateOf("") }
        var author by remember { mutableStateOf("") }

        Column(modifier = Modifier.selectableGroup()) {
            Text(text = "Type")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = type == Type.CHECKOUT,
                    onClick = { type = Type.CHECKOUT }
                )
                Text("Checkout")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = type == Type.HOLD,
                    onClick = { type = Type.HOLD }
                )
                Text("Hold")
            }
        }
        Column(modifier = Modifier.selectableGroup()) {
            Text("Format")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = format == Format.PHYSICAL,
                    onClick = { format = Format.PHYSICAL }
                )
                Text("Physical")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = format == Format.DIGITAL,
                    onClick = { format = Format.DIGITAL }
                )
                Text("Digital")
            }
        }
        TextField(value = title, onValueChange = { title = it },
            label = { Text("Title") })
        TextField(value = author, onValueChange = { author = it },
            label = { Text("Author") })
        Text(if (type == Type.CHECKOUT) "Return Date" else "Expected Hold Date")
        DatePicker(
            showModeToggle = true,
            state = rememberDatePickerState(
                initialDisplayMode = DisplayMode.Input,
                yearRange = IntRange(2024, 2040)
            )
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ }) {
                Text("Add")
            }
        }
    }
}

enum class Type {
    HOLD,
    CHECKOUT
}

enum class Format {
    PHYSICAL,
    DIGITAL
}

@PreviewLightDark
@Composable
private fun AddLibraryBookScreenPreview() {
    BookStuffTheme {
        AddLibraryBookScreen()
    }
}