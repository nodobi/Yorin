package com.hyeok.recipebook.presentation.recipe.model

import kotlinx.datetime.LocalDate

data class RecipeUiModel(
    val id: Int,
    val name: String,
    val registerDate: LocalDate,
    val cookingTime: Int,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientUiModel>,
    val steps: List<CookingStepUiModel>,
    val cookingRecords: List<CookingRecordUiModel>,
    val changeHistory: List<ChangeHistoryUiModel>
)