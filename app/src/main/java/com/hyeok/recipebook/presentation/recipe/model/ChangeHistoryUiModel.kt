package com.hyeok.recipebook.presentation.recipe.model

import kotlinx.datetime.LocalDate

data class ChangeHistoryUiModel(
    val changeAt: LocalDate,
    val description: String
)