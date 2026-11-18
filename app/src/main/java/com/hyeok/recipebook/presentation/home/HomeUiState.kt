package com.hyeok.recipebook.presentation.home

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import com.hyeok.recipebook.presentation.home.model.OwnedIngredientUiModel
import com.hyeok.recipebook.presentation.home.model.RecipeRecommendationUiModel

// TODO:: Empty, Success 로 분리
@Stable
data class HomeUiState(
    val recipeRecommendation: RecipeRecommendationUiModel?,
    val expiredIngredients: List<OwnedIngredientUiModel>,
    val expiringIngredients: List<OwnedIngredientUiModel>,
    val readyToCookRecipes: List<RecipeSummaryModel>,
    val recentlyCookedRecipes: List<RecipeSummaryModel>
) {
    companion object {
        fun empty() = HomeUiState(
            recipeRecommendation = null,
            expiredIngredients = listOf(),
            expiringIngredients = listOf(),
            readyToCookRecipes = listOf(),
            recentlyCookedRecipes = listOf()
        )
    }
}