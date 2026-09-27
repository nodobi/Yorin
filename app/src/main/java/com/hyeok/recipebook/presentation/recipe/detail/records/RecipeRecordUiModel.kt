package com.hyeok.recipebook.presentation.recipe.detail.records

import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import kotlinx.datetime.LocalDate

data class RecipeRecordUiModel(
    val id: Long,
    val cookedAt: LocalDate,
    val title: String,
    val description: String,
    val score: Int
) {

    companion object {
        fun fake() = RecipeRecordUiModel(
            id = 1,
            cookedAt = LocalDate(2026, 9, 21),
            title = "기록 1",
            description = "기록 상세사항 1",
            score = 5
        )

        fun fakes(): List<RecipeRecordUiModel> = listOf(
            RecipeRecordUiModel(
                id = 1,
                cookedAt = LocalDate(2026, 9, 21),
                title = "기록 1",
                description = "기록 상세사항 1",
                score = 5
            ),
            RecipeRecordUiModel(
                id = 2,
                cookedAt = LocalDate(2026, 9, 22),
                title = "기록 2",
                description = "기록 상세사항 2",
                score = 3
            )
        )
    }
}