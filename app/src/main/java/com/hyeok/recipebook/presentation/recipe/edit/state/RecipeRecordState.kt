package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json

@Stable
data class RecipeRecordItemState(
    val id: Long,
    val record: RecipeRecordUiModel
)

@Stable
class RecipeRecordState(
    initial: List<RecipeRecordUiModel>,
    private var nextId: Long = 0L
) {
    val items = mutableStateListOf<RecipeRecordItemState>().apply {
        initial.forEach { uiModel ->
            add(
                RecipeRecordItemState(
                    id = nextId++,
                    record = uiModel
                )
            )
        }
    }

    fun add(record: RecipeRecordUiModel) {
        items.add(
            RecipeRecordItemState(
                id = nextId++,
                record = record
            )
        )
    }

    fun remove(id: Long) {
        items.removeIf { it.id == id }
    }

    companion object {
        fun saver() = listSaver(
            save = { state ->
                listOf(
                    state.nextId,
                    state.items.map {
                        listOf(
                            it.id,
                            it.record.id,
                            Json.encodeToString(LocalDate.serializer(), it.record.cookedAt),
                            it.record.title,
                            it.record.description,
                            it.record.score
                        )
                    }
                )
            },
            restore = {
                RecipeRecordState(
                    initial = emptyList(),
                    nextId = it[0] as Long,
                ).apply {
                    @Suppress("UNCHECKED_CAST")
                    val rows = it[1] as List<List<Any>>

                    rows.forEach { row ->
                        items += RecipeRecordItemState(
                            id = row[0] as Long,
                            record = RecipeRecordUiModel(
                                id = row[1] as Long,
                                cookedAt = Json.decodeFromString(LocalDate.serializer(), row[2] as String),
                                title = row[3] as String,
                                description = row[4] as String,
                                score = row[5] as Int
                            )
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun rememberRecipeRecordState(
    initial: List<RecipeRecordUiModel>
) = rememberSaveable(saver = RecipeRecordState.saver()) {
    RecipeRecordState(initial = initial)
}