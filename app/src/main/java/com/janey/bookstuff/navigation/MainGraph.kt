package com.janey.bookstuff.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.janey.bookstuff.goals.GoalsScreen
import com.janey.bookstuff.home.HomeScreen
import com.janey.bookstuff.library.LibraryScreen
import com.janey.bookstuff.stats.StatsScreen
import com.janey.bookstuff.tbr.tbrNavGraph

enum class Graphs {
    MAIN_GRAPH,
    TBR_GRAPH
}

@Composable
fun MainGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    // TODO janey - handle screen titles and FABs
    NavHost(
        navController = navController,
        route = Graphs.MAIN_GRAPH.name,
        startDestination = Tab.Home.navGraphRoute,
        modifier = modifier,
    ) {
        composable(Tab.Home.navGraphRoute) {
            HomeScreen()
        }
        composable(Tab.Library.navGraphRoute) {
            LibraryScreen(onBookRemoved = {})
        }
        tbrNavGraph(
            navController = navController
        )
        composable(Tab.Stats.navGraphRoute) {
            StatsScreen()
        }
        composable(Tab.Goals.navGraphRoute) {
            GoalsScreen()
        }
    }
}