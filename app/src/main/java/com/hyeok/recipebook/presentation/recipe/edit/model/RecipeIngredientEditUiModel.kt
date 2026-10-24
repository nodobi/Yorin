package com.hyeok.recipebook.presentation.recipe.edit.model

import androidx.compose.runtime.Stable
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel

@Stable
data class RecipeIngredientEditUiModel(
    val id: Long,
    val name: String,
    val unit: String,
    val requireQuantity: Int,
)

fun RecipeIngredientUiModel.toEditUiModel() = RecipeIngredientEditUiModel(
    id = id,
    name = name,
    unit = unit,
    requireQuantity = requireQuantity
)