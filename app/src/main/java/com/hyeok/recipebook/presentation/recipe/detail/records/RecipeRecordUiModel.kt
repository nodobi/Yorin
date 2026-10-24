package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.runtime.saveable.listSaver
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

data class RecipeRecordUiModel(
    val id: Long,
    val cookedAt: LocalDate,
    val title: String,
    val description: String,
    val score: Int
) {

    companion object {
        val saver = listSaver(
            save = { model ->
                listOf(
                    model.id,
                    Json.encodeToString(LocalDate.serializer(), model.cookedAt),
                    model.title,
                    model.description,
                    model.score
                )
            },
            restore = {
                RecipeRecordUiModel(
                    id = it[0] as Long,
                    cookedAt = Json.decodeFromString(LocalDate.serializer(), it[1] as String),
                    title = it[2] as String,
                    description = it[3] as String,
                    score = it[4] as Int
                )
            }
        )

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