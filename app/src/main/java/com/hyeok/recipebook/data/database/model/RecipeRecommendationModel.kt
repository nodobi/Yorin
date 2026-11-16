package com.hyeok.recipebook.data.database.model

data class RecipeRecommendationModel(
    val recipe: RecipeDetailsModel,
    val reason: Set<RecommendationReason>
)