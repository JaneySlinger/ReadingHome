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
import com.janey.bookstuff.tbr.addtbr.initialSearch.InitialAddTBRScreen
import com.janey.bookstuff.tbr.addtbr.results.AddTBRBookResultsScreen
import com.janey.bookstuff.tbr.bookdetails.BookDetailsScreen

fun NavGraphBuilder.tbrNavGraph(navController: NavController) {
    navigation(
        route = Graphs.TBR_GRAPH.name,
        startDestination = TBRRoutes.TBR_BOOKS.route,
    ) {
        composable(route = TBRRoutes.TBR_BOOKS.route) {
            ProvideAnimatedContentScope {
                TBRScreen(
                    hiltViewModel(),
                    onAddBookClicked = { navController.navigate(TBRRoutes.TBR_ADD.name) },
                    onBookClicked = { navController.navigate("${TBRRoutes.TBR_DETAIL.name}/${it.title}") }
                )
            }
        }

        composable(
            route = TBRRoutes.TBR_DETAIL.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            ProvideAnimatedContentScope {
                BookDetailsScreen(
                    viewModel = hiltViewModel()
                )
            }
        }

        composable(route = TBRRoutes.TBR_ADD.route) {
            InitialAddTBRScreen(
                viewModel = hiltViewModel(),
                onQuickAddClicked = { /* TODO Janey */ },
                onContinueClicked = { title, author ->
                    navController.navigate(
                        route = "${TBRRoutes.TBR_ADD_DETAIL.name}/${title}&${author}"
                    )
                }
            )
        }

        composable(
            route = TBRRoutes.TBR_ADD_DETAIL.route,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("author") { type = NavType.StringType }
            )
        ) {
            AddTBRBookResultsScreen(
                viewModel = hiltViewModel(),
                onNavigateToConfirmDetails = { selectedBookId ->
                    navController.navigate(
                        route = "${TBRRoutes.TBR_DETAIL.name}/${selectedBookId}"
                    )
                }
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
        route = "${TBR_DETAIL.name}/{id}",
    ),
    TBR_ADD_DETAIL(
        route = "${TBR_ADD_DETAIL.name}/{title}&{author}",
    )
}
