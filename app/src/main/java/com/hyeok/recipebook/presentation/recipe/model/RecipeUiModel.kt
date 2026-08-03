package com.hyeok.recipebook.presentation.recipe.model

import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import kotlinx.datetime.LocalDate

data class RecipeUiModel(
    val id: Int,
    val name: String,
    val registerDate: LocalDate,
    val cookingTime: Int,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientUiModel>,
    val steps: List<RecipeStepUiModel>,
    val cookingRecords: List<CookingRecordUiModel>,
    val changeHistory: List<ChangeHistoryUiModel>
)