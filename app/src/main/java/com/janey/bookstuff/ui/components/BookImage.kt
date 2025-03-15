package com.janey.bookstuff.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.janey.bookstuff.R

@Composable
fun BookImage(
    url: String,
    height: Dp = 150.dp,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        placeholder = painterResource(R.drawable.gideon),
        error = painterResource(R.drawable.gideon),
        model = url,
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .padding(2.dp)
            .height(height)
            .clip(shape = RoundedCornerShape(4.dp))
    )
}