package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.recipe.list.RecipeItemUiModel

data class RecipeSummaryModel(
    val id: Long,
    val name: String,
    val registerDate: Long,
    val cookingTime: Int,
    val photoUrl: String?,
    val rawIngredients: String,
    val averageScore: Float,
)

internal fun RecipeSummaryModel.toUiModel(): RecipeItemUiModel =
    RecipeItemUiModel(
        id = id,
        name = name,
        photoUri = photoUrl,
        ingredients = rawIngredients.split(","),
        averageScore = averageScore,
        cookingTime = cookingTime
    )
