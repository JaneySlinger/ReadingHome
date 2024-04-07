package com.janey.bookstuff.tbr

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.janey.bookstuff.library.LibraryScreen
import com.janey.bookstuff.ui.components.BaseScreen
import com.janey.bookstuff.ui.theme.BookStuffTheme

@Composable
fun TBRScreen() {
    BaseScreen {
        Text("Books to read will appear here")
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