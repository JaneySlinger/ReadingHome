package com.janey.bookstuff.tbr.addtbr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@Composable
fun InitialAddTBRScreenContent(
    title: String = "",
    author: String = "",
    listener: (AddTBRBookEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BaseScreen(isScrollable = false) {
        Column {
            TextField(
                label = { Text("Title") },
                value = title,
                onValueChange = { listener(AddTBRBookEvent.TitleChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                label = { Text("Author") },
                value = author,
                onValueChange = { listener(AddTBRBookEvent.AuthorChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    listener(
                        AddTBRBookEvent.SubmitClicked(
                            isReleased = false
                        )
                    )
                }) {
                Text("Quick Add")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { TODO() })
            {
                Text("Continue")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun InitialAddTBRScreenPreview() {
    BookStuffTheme {
        InitialAddTBRScreenContent(
            title = "",
            author = "",
            listener = {}
        )
    }
}