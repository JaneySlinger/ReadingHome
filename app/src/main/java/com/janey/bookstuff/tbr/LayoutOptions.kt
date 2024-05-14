package com.janey.bookstuff.tbr

import androidx.annotation.DrawableRes
import com.janey.bookstuff.R

enum class LayoutOptions(
    @DrawableRes val icon: Int,
    val title: String,
) {
    GRID(R.drawable.grid, "Grid"),
    LIST(R.drawable.list, "List"),
}