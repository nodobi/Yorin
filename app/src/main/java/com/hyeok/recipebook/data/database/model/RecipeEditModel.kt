package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeIngredientEditUiModel

data class RecipeEditModel(
    val id: Long = 0,
    val name: String,
    val cookingTime: Int,
    val photoUrl: String? = null,
    val ingredients: List<RecipeIngredientModel>,
    val steps: List<RecipeStepModel>,
    val records: List<RecipeRecordModel>
)

fun RecipeIngredientEditUiModel.toModel() = RecipeIngredientModel(
    id = id,
    name = name,
    weightUnit = unit,
    requireQuantity = requireQuantity,
)

fun RecipeEditUiModel.toModel(
    recipeId: Long = 0,
) = RecipeEditModel(
    id = recipeId,
    name = name,
    cookingTime = cookingTime,
    photoUrl = photoUrl,
    ingredients = ingredients.map { it.toModel() },
    steps = steps.map { it.toModel() },
    records = cookingRecords.map { it.toModel() },
)