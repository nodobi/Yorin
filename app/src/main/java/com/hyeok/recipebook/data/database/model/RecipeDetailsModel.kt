package com.hyeok.recipebook.data.database.model

import com.hyeok.recipebook.presentation.recipe.detail.RecipeUiModel
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import kotlinx.datetime.LocalDate


data class RecipeDetailsModel(
    val id: Long = 0,
    val name: String,
    val registerDate: Long,
    val cookingTime: Int,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientModel>,
    val steps: List<RecipeStepModel>,
    val records: List<RecipeRecordModel>
) {
    val averageScore: Float = records.sumOf { record ->
        record.score
    }.toFloat() / records.size
}

data class RecipeIngredientModel(
    val id: Long,
    val name: String,
    val weightUnit: String,
    val requireQuantity: Int,
)

data class RecipeStepModel(
    val id: Long = 0,
    val order: Int,
    val description: String,
)

data class RecipeRecordModel(
    val id: Long = 0,
    val cookedAt: Long,
    val title: String,
    val description: String,
    val score: Int,
)

fun RecipeIngredientModel.toUiModel(stockQuantity: Int) = RecipeIngredientUiModel(
    id = id,
    name = name,
    unit = weightUnit,
    requireQuantity = requireQuantity,
    stockQuantity = stockQuantity
)

fun RecipeStepModel.toUiModel() = RecipeStepUiModel(
    id = id,
    order = order,
    description = description
)

fun RecipeRecordModel.toUiModel() = RecipeRecordUiModel(
    id = id,
    cookedAt = LocalDate.fromEpochDays(cookedAt),
    title = title,
    description = description,
    score = score
)

fun RecipeDetailsModel.toUiModel(stockQuantity: Map<String, Int>) = RecipeUiModel(
    id = id,
    name = name,
    registerDate = LocalDate.fromEpochDays(registerDate),
    cookingTime = cookingTime,
    averageScore = averageScore,
    photoUrl = photoUrl,
    ingredients = ingredients.map { ingredient -> ingredient.toUiModel(stockQuantity[ingredient.name] ?: 0) },
    steps = steps.map(RecipeStepModel::toUiModel),
    cookingRecords = records.map(RecipeRecordModel::toUiModel)
)