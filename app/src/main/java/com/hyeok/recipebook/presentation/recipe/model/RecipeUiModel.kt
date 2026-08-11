package com.hyeok.recipebook.presentation.recipe.model

import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import kotlinx.datetime.LocalDate

data class RecipeUiModel(
    val id: Int,
    val name: String,
    val registerDate: LocalDate,
    val cookingTime: Int,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientUiModel>,
    val steps: List<RecipeStepUiModel>,
    val cookingRecords: List<RecipeRecordUiModel>,
    val changeHistory: List<ChangeHistoryUiModel>
)