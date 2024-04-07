package com.janey.bookstuff.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface { // the surface is needed to make the theming apply to the background
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            content()
        }
    }
}