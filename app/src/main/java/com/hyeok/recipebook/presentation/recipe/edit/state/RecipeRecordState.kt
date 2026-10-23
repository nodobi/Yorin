package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

    var itemToEdit by mutableStateOf<RecipeRecordUiModel?>(null)
        private set

    var isShowRecordEditSheet by mutableStateOf(false)
        private set

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

    fun showRecordEditSheet(record: RecipeRecordUiModel? = null) {
        itemToEdit = record
        isShowRecordEditSheet = true
    }

    fun closeRecordEditSheet() {
        itemToEdit = null
        isShowRecordEditSheet = false
    }

    companion object {
        val saver = listSaver<RecipeRecordState, Any?>(
            save = { state ->
                listOf(
                    state.nextId,
                    state.items.map {
                        listOf(
                            it.id,
                            with(RecipeRecordUiModel.saver) { save(it.record) }
                        )
                    },
                    with(RecipeRecordUiModel.saver) { state.itemToEdit?.let { save(it) } },
                    state.isShowRecordEditSheet
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
                            record = RecipeRecordUiModel.saver.restore(row[1]) as RecipeRecordUiModel
                        )
                    }

                    itemToEdit = it[2]?.let { RecipeRecordUiModel.saver.restore(it) }
                    isShowRecordEditSheet = it[3] as Boolean
                }
            }
        )
    }
}

@Composable
fun rememberRecipeRecordState(
    initial: List<RecipeRecordUiModel>
) = rememberSaveable(saver = RecipeRecordState.saver) {
    RecipeRecordState(initial = initial)
}