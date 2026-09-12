package com.hyeok.recipebook.presentation.recipe.detail

import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class RecipeUiModel(
    val id: Long,
    val name: String,
    val registerDate: LocalDate,
    val cookingTime: Int,
    val averageScore: Float,
    val photoUrl: String?,
    val ingredients: List<RecipeIngredientUiModel>,
    val steps: List<RecipeStepUiModel>,
    val cookingRecords: List<RecipeRecordUiModel>,
) {
    companion object {
        fun fake(): RecipeUiModel = RecipeUiModel(
            id = 1,
            name = "김치찌개",
            registerDate = LocalDate(2026, 8, 16),
            cookingTime = 30,
            averageScore = 4.4f,
            photoUrl = "",
            ingredients = listOf(
                RecipeIngredientUiModel(
                    id = 1,
                    name = "김치",
                    unit = "g",
                    requireQuantity = 100,
                    stockQuantity = 200
                ),
                RecipeIngredientUiModel(
                    id = 2,
                    name = "두부",
                    unit = "g",
                    requireQuantity = 100,
                    stockQuantity = 50
                )
            ),
            steps = listOf(),
            cookingRecords = listOf(),
        )

        @OptIn(ExperimentalTime::class)
        fun empty() = RecipeUiModel(
            id = 0,
            name = "",
            registerDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            cookingTime = 0,
            averageScore = 0.0f,
            photoUrl = null,
            ingredients = listOf(),
            steps = listOf(),
            cookingRecords = listOf()
        )
    }
}