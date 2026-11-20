package com.hyeok.recipebook.presentation.home

import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import com.hyeok.recipebook.presentation.home.component.RecipeRecommendationState
import com.hyeok.recipebook.presentation.home.model.OwnedIngredientUiModel
import com.hyeok.recipebook.presentation.home.model.RecipeRecommendationUiModel

sealed interface HomeUiState {
    data object Empty : HomeUiState

    data class Success(
        val recommendationState: RecipeRecommendationState,
        val recipeRecommendation: RecipeRecommendationUiModel?,
        val expiredIngredients: List<OwnedIngredientUiModel>,
        val expiringIngredients: List<OwnedIngredientUiModel>,
        val readyToCookRecipes: List<RecipeSummaryModel>,
        val recentlyCookedRecipes: List<RecipeSummaryModel>
    ) : HomeUiState

    companion object {
        fun empty() = Success(
            recommendationState = RecipeRecommendationState.NoRecommendation,
            recipeRecommendation = null,
            expiredIngredients = listOf(),
            expiringIngredients = listOf(),
            readyToCookRecipes = listOf(),
            recentlyCookedRecipes = listOf()
        )
    }
}