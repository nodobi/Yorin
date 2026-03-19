package com.hyeok.recipebook.presentation.recipe.model

import kotlinx.datetime.LocalDate

data class CookingRecordUiModel(
    val cookedAt: LocalDate,
    val description: String,
    val score: Int
)