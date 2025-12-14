package com.janey.bookstuff.tbr.addtbr.initialSearch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@Composable
fun InitialAddTBRScreen(
    viewModel: InitialAddTBRViewModel = viewModel(),
    onQuickAddClicked: () -> Unit,
    onContinueClicked: (title: String, author: String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    InitialAddTBRScreenContent(
        title = state.value.title,
        author = state.value.author,
        onTitleChanged = { viewModel.onTitleChanged(it) },
        onAuthorChanged = { viewModel.onAuthorChanged(it) },
        onQuickAddClicked = {
            viewModel.onQuickAddClicked()
            onQuickAddClicked()
        },
        onContinueClicked = onContinueClicked
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun InitialAddTBRScreenContent(
    title: String = "",
    author: String = "",
    onTitleChanged: (String) -> Unit = {},
    onAuthorChanged: (String) -> Unit = {},
    onQuickAddClicked: () -> Unit = {},
    onContinueClicked: (title: String, author: String) -> Unit = { _, _ -> },
) {
    BaseScreen(isScrollable = false) {
        Column {
            TextField(
                label = { Text("Title") },
                value = title,
                onValueChange = { onTitleChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                label = { Text("Author") },
                value = author,
                onValueChange = { onAuthorChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onQuickAddClicked() }
            ) {
                Text("Quick Add")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onContinueClicked(title, author) })
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
            onQuickAddClicked = {},
            onContinueClicked = { _, _ -> }
        )
    }
}