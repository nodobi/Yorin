package com.hyeok.recipebook.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(Route.Home, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navController: NavHostController
) {
    composable<Route.Home> {
        lateinit var uiState: HomeUiState

        HomeRoute(
            uiState = uiState,
            onExpirationCardClick = {},
            onViewRecommendedRecipeClick = {},
            onRerollRecipeRecommendation = {},
            onViewExpiringIngredientsClick = {},
            onRecipeClick = {},
        )
    }
}