package com.hyeok.recipebook.presentation.home

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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