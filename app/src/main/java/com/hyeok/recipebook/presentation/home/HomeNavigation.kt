package com.hyeok.recipebook.presentation.home

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.hyeok.recipebook.presentation.ingredient.navigateToIngredient
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.navigateToRecipe
import com.hyeok.recipebook.presentation.recipe.navigateToRecipeDetail

fun Navigator.navigateToHome() {
    navigate(Route.Home)
}

fun NavGraphBuilder.homeScreen(
    navigator: Navigator
) {
    composable<Route.Home> {
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        HomeRoute(
            uiState = uiState,
            onExpirationCardClick = {
                navigator.navigateToIngredient()
            },
            onViewRecommendedRecipeClick = {
                navigator.navigateToRecipeDetail(it)
            },
            onRerollRecipeRecommendation = {
                viewModel.onRerollRecommendation()
            },
            onAddIngredientClick = {
                navigator.navigateToIngredient()
            },
            onAddRecipeClick = {
                navigator.navigateToRecipe()
            },
            onViewExpiringIngredientsClick = {
                navigator.navigateToIngredient()
            },
            onRecipeClick = {
                navigator.navigateToRecipeDetail(it)
            },
        )
    }
}

fun EntryProviderScope<NavKey>.homeEntry(
    navigator: Navigator
) {
    entry<Route.Home> {
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        HomeRoute(
            uiState = uiState,
            onExpirationCardClick = {
                navigator.navigateToIngredient()
            },
            onViewRecommendedRecipeClick = {
                navigator.navigateToRecipeDetail(it)
            },
            onRerollRecipeRecommendation = {
                viewModel.onRerollRecommendation()
            },
            onAddIngredientClick = {
                navigator.navigateToIngredient()
            },
            onAddRecipeClick = {
                navigator.navigateToRecipe()
            },
            onViewExpiringIngredientsClick = {
                navigator.navigateToIngredient()
            },
            onRecipeClick = {
                navigator.navigateToRecipeDetail(it)
            },
        )
    }
}