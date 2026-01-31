package com.hyeok.recipebook.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.designsystem.theme.RecipeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeBookTheme {
                MainView()
            }
        }
    }

    fun foo() {
        val a =1


        bar {
            this + 123
        }

        baz { it ->
            it + 123
        }
    }

    fun bar(block: Int.() -> Int) {
        val a = block(123)
    }

    fun baz(block: (Int) -> Int) {
        val a = block(123)
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Route.topLevelRoutes.forEachIndexed { idx, route ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                        onClick = {
                            navController.navigate(route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {Icon(route.icon, contentDescription = null)},
                        label = {Text(text = route.name)}
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Route.Home
        ) {
            composable<Route.Home> { backStack ->
                HomeScreen()
            }
            composable<Route.Menu> { backStack ->
                MenuScreen()
            }
            composable<Route.Ingredient> { backStack ->
                IngredientScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RecipeBookTheme {
        MainView()
    }
}