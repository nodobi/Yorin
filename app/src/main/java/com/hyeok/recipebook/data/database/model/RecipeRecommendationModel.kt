package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.home.model.RecommendationReason

data class RecipeRecommendationModel(
    val recipe: RecipeDetailsModel,
    val reason: Set<RecommendationReason>
)