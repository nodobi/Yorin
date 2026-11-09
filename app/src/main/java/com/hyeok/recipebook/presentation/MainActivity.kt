package com.hyeok.recipebook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hyeok.recipebook.designsystem.components.YorinSnackBarHostState
import com.hyeok.recipebook.designsystem.components.YorinSnackbar
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.home.homeScreen
import com.hyeok.recipebook.presentation.ingredient.ingredientScreen
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.recipeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YorinTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    val snackbarState = remember { SnackbarHostState() }
    val appScope = rememberCoroutineScope()
    val context = LocalContext.current

    val yorinSnackbarHostState = remember {
        YorinSnackBarHostState(
            snackbarState,
            appScope,
            context
        )
    }

    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) },
            snackbarHost = { SnackbarHost(hostState = snackbarState, snackbar = { YorinSnackbar(it) }) },
            contentWindowInsets = WindowInsets(0.dp)
        ) { innerPadding ->
            CompositionLocalProvider(LocalSnackbarHostState provides yorinSnackbarHostState) {
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = Route.Recipe
                ) {
                    homeScreen(
                        modifier = Modifier
                    )
                    recipeScreen(
                        navController = navController,
                    )
                    ingredientScreen(
                        navController = navController
                    )
                }
            }
        }
    }
}

val LocalSnackbarHostState = compositionLocalOf<YorinSnackBarHostState> {
    error("SnackbarHostState state should be initialized at runtime")
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    YorinTheme {
        MainView()
    }
}