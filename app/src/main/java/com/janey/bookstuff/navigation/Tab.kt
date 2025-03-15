package com.janey.bookstuff.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.janey.bookstuff.R
enum class TabRoute {
    HOME_TAB,
    LIBRARY_TAB,
    STATS_TAB,
    GOALS_TAB
}

sealed class Tab(
    val navGraphRoute: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val hasFAB: Boolean = false,
) {
    data object Home : Tab(
        navGraphRoute = TabRoute.HOME_TAB.name,
        title = R.string.home,
        icon = R.drawable.home,
    )

    data object Library : Tab(
        navGraphRoute = TabRoute.LIBRARY_TAB.name,
        title = R.string.library,
        icon = R.drawable.library_books,
        hasFAB = true,
    )

    data object TBR : Tab(
        navGraphRoute = Graphs.TBR_GRAPH.name,
        title = R.string.tbr,
        icon = R.drawable.bookmark_add,
        hasFAB = true
    )

    data object Stats : Tab(
        navGraphRoute = TabRoute.STATS_TAB.name,
        title = R.string.stats,
        icon = R.drawable.stats
    )

    data object Goals : Tab(
        navGraphRoute = TabRoute.GOALS_TAB.name,
        title = R.string.goal,
        icon = R.drawable.target
    )
}