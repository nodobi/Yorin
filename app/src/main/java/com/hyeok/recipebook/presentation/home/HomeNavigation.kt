package com.hyeok.recipebook.presentation.home

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.hyeok.recipebook.presentation.ingredient.navigateToIngredient
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.recipe.navigateToRecipe
import com.hyeok.recipebook.presentation.recipe.navigateToRecipeDetail
import kotlinx.serialization.Serializable


@Serializable
data object HomeNavKey : NavKey

fun Navigator.navigateToHome() {
    navigate(HomeNavKey)
}

fun EntryProviderScope<NavKey>.homeEntry(
    navigator: Navigator
) {
    entry<HomeNavKey> {
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
