package com.hyeok.recipebook.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.hyeok.recipebook.designsystem.components.YorinSnackbar
import com.hyeok.recipebook.designsystem.components.YorinSnackbarHostState
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.home.HomeNavKey
import com.hyeok.recipebook.presentation.home.homeEntry
import com.hyeok.recipebook.presentation.ingredient.ingredientSection
import com.hyeok.recipebook.presentation.navigation.BottomSheetSceneStrategy
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.navigation.rememberNavigationState
import com.hyeok.recipebook.presentation.navigation.toEntries
import com.hyeok.recipebook.presentation.recipe.recipeSection
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
    val snackbarState = remember { SnackbarHostState() }
    val appScope = rememberCoroutineScope()
    val context = LocalContext.current

    val yorinSnackbarHostState = remember {
        YorinSnackbarHostState(
            snackbarState,
            appScope,
            context
        )
    }

    val navigationState = rememberNavigationState(
        startRoute = HomeNavKey,
        topLevelRoutes = TopLevelRoutes
    )
    val navigator = remember { Navigator(navigationState) }
    val entryProvider = entryProvider {
        homeEntry(navigator)
        recipeSection(navigator)
        ingredientSection(navigator)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navigator) },
        snackbarHost = { SnackbarHost(hostState = snackbarState, snackbar = { YorinSnackbar(it) }) },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        CompositionLocalProvider(LocalSnackbarHostState provides yorinSnackbarHostState) {
            NavDisplay(
                modifier = Modifier.padding(innerPadding),
                entries = navigationState.toEntries(entryProvider),
                onBack = { navigator.goBack() },
                sceneStrategies = remember {
                    // metadata 가 붙은 entry 가 없으면 동작하지 않는다. -> DialogSceneStrategy 를 넣어도 동작에는 문제없다
                    listOf(BottomSheetSceneStrategy())
                }
            )
        }
    }
}

val LocalSnackbarHostState = compositionLocalOf<YorinSnackbarHostState> {
    error("SnackbarHostState state should be initialized at runtime")
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    YorinTheme {
        MainView()
    }
}