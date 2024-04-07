package com.janey.bookstuff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.janey.bookstuff.goals.GoalsScreen
import com.janey.bookstuff.home.HomeScreen
import com.janey.bookstuff.library.LibraryScreen
import com.janey.bookstuff.read.ReadScreen
import com.janey.bookstuff.tbr.AddTBRBookScreen
import com.janey.bookstuff.tbr.TBRScreen
import com.janey.bookstuff.tbr.TBRScreenFAB
import com.janey.bookstuff.ui.theme.BookStuffTheme


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

    data object Read : Screen(
        route = Routes.READ.name,
        screenName = R.string.read,
        icon = R.drawable.book
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
        READ,
        GOALS,
        ADD_TBR,
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookStuffTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookStuffTheme {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val items = listOf(
            Screen.Home,
            Screen.Library,
            Screen.TBR,
            Screen.Read,
            Screen.Goals,
        )
        val navController = rememberNavController()
        var screenTitle by remember { mutableIntStateOf(Screen.Home.screenName) }
        var hasFAB by remember { mutableStateOf(false) }
        var floatingActionButton: @Composable () -> Unit = {}
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.icon!!),
                                    contentDescription = null
                                )
                            },
                            label = { Text(stringResource(id = screen.screenName)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    screenTitle = screen.screenName
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                if (hasFAB) {
                    floatingActionButton()
                }
            },
            topBar = { TopAppBar(title = { Text(text = stringResource(id = screenTitle)) }) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Routes.HOME.name,
                Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    screenTitle = Screen.Home.screenName
                    hasFAB = Screen.Home.hasFAB
                    HomeScreen()
                }
                composable(Screen.Library.route) {
                    screenTitle = Screen.Library.screenName
                    hasFAB = Screen.Library.hasFAB
                    LibraryScreen()
                }
                composable(Screen.TBR.route) {
                    screenTitle = Screen.TBR.screenName
                    hasFAB = Screen.TBR.hasFAB
                    floatingActionButton =
                        { TBRScreenFAB { navController.navigate(Screen.AddTBRBook.route) } }
                    TBRScreen()
                }
                composable(Screen.Read.route) {
                    screenTitle = Screen.Read.screenName
                    hasFAB = Screen.Read.hasFAB
                    ReadScreen()
                }
                composable(Screen.Goals.route) {
                    screenTitle = Screen.Goals.screenName
                    hasFAB = Screen.Goals.hasFAB
                    GoalsScreen()
                }
                composable(Screen.AddTBRBook.route) {
                    screenTitle = Screen.AddTBRBook.screenName
                    hasFAB = Screen.AddTBRBook.hasFAB
                    AddTBRBookScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    BookStuffTheme {
        MainScreen()
    }
}