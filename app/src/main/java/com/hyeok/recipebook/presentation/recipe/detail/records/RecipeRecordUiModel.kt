package com.hyeok.recipebook.presentation.recipe.detail.records

import kotlinx.datetime.LocalDate

data class RecipeRecordUiModel(
    val id: Long,
    val cookedAt: LocalDate,
    val title: String,
    val description: String,
    val score: Int
)