package com.janey.bookstuff.tbr

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.janey.bookstuff.ProvideAnimatedContentScope
import com.janey.bookstuff.navigation.Graphs
import com.janey.bookstuff.tbr.addtbr.AddTBRBookResultsScreen
import com.janey.bookstuff.tbr.addtbr.InitialAddTBRScreen
import com.janey.bookstuff.tbr.bookdetails.BookDetailsScreen

fun NavGraphBuilder.tbrNavGraph(navController: NavController) {
    // TODO janey - handle screen titles and FAB for adding book
    navigation(
        route = Graphs.TBR_GRAPH.name,
        startDestination = TBRRoutes.TBR_BOOKS.route,
    ) {
        composable(route = TBRRoutes.TBR_BOOKS.route) {
            ProvideAnimatedContentScope {
                TBRScreen(
                    hiltViewModel(),
                    onBookClicked = { navController.navigate("${TBRRoutes.TBR_DETAIL.name}/${it.title}") }
                )
            }
        }

        composable(
            route = TBRRoutes.TBR_DETAIL.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            ProvideAnimatedContentScope {
                BookDetailsScreen()
            }
        }

        composable(route = TBRRoutes.TBR_ADD.route) {
            InitialAddTBRScreen(
                onContinueClicked = { navController.navigate(TBRRoutes.TBR_ADD_DETAIL.route) }
            )
        }

        composable(route = TBRRoutes.TBR_ADD_DETAIL.route) {
            AddTBRBookResultsScreen(
                onNavigateToConfirmDetails = { navController.navigate(TBRRoutes.TBR_DETAIL.route) }
            )
        }
    }
}

enum class TBRRoutes(val route: String) {
    TBR_BOOKS(
        route = TBR_BOOKS.name,
    ),
    TBR_ADD(
        route = TBR_ADD.name,
    ),
    TBR_DETAIL(
        route = "${TBR_DETAIL.name}/{title}",
    ),
    TBR_ADD_DETAIL(
        route = TBR_ADD_DETAIL.name,
    )
}
