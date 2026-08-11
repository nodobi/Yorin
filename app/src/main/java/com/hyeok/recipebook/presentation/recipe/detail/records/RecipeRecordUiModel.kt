package com.hyeok.recipebook.presentation.recipe.detail.records

import kotlinx.datetime.LocalDate

data class RecipeRecordUiModel(
    val cookedAt: LocalDate,
    val description: String,
    val score: Int
)