package com.janey.bookstuff.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.janey.bookstuff.R

sealed class Screen(
    val route: String,
    @StringRes val screenName: Int,
    @DrawableRes val icon: Int? = null,
    val hasFAB: Boolean = false,
) {
    data object Home : Screen(
        route = Routes.HOME.name,
        screenName = R.string.home,
        icon = R.drawable.home,
    )

    data object Library : Screen(
        route = Routes.LIBRARY.name,
        screenName = R.string.library,
        icon = R.drawable.library_books
    )

    data object TBR : Screen(
        route = Routes.TBR.name,
        screenName = R.string.tbr,
        icon = R.drawable.bookmark_add,
        hasFAB = true
    )

    data object Stats : Screen(
        route = Routes.STATS.name,
        screenName = R.string.stats,
        icon = R.drawable.stats
    )

    data object Goals : Screen(
        route = Routes.GOALS.name,
        screenName = R.string.goal,
        icon = R.drawable.target
    )

    data object AddTBRBook : Screen(
        route = Routes.ADD_TBR.name,
        screenName = R.string.add_tbr,

        )

    enum class Routes {
        HOME,
        LIBRARY,
        TBR,
        STATS,
        GOALS,
        ADD_TBR,
    }
}