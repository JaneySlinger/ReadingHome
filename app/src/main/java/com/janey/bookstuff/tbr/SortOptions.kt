package com.janey.bookstuff.tbr

import androidx.annotation.DrawableRes
import com.janey.bookstuff.R

enum class SortType(
    @DrawableRes val icon: Int,
    val title: String,
) {
    LENGTH(R.drawable.width, "Length"),
    DATE_ADDED(R.drawable.date_range, "Date Added"),
    DATE_RELEASED(R.drawable.date_range, "Date Released"),
    SHUFFLE(R.drawable.shuffle, "Shuffle"),
}