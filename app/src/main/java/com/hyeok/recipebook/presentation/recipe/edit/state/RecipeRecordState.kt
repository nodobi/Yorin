package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel

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

    var itemToEdit by mutableStateOf<RecipeRecordItemState?>(null)
        private set

    var isShowRecordEditSheet by mutableStateOf(false)
        private set

    var isShowRecordActionSheet by mutableStateOf(false)
        private set

    fun add(record: RecipeRecordUiModel) {
        val isNewRecord = itemToEdit == null

        if (isNewRecord) {
            items.add(
                RecipeRecordItemState(
                    id = nextId++,
                    record = record
                )
            )
        } else {
            val idx = items.indexOfFirst { it.id == itemToEdit!!.id }

            check(idx != -1) { "Fail to find itemToEdit idx, id: ${itemToEdit!!.id}" }

            items[idx] = items[idx].copy(
                record = record
            )
        }
    }

    fun remove() {
        itemToEdit?.let { itemToEdit ->
            items.removeIf { it.id == itemToEdit.id }
        } ?: return
    }

    fun showRecordEditSheet() {
        if (isShowRecordActionSheet) isShowRecordActionSheet = false
        isShowRecordEditSheet = true
    }

    fun closeRecordEditSheet() {
        itemToEdit = null
        isShowRecordEditSheet = false
    }

    fun showRecordActionSheet(item: RecipeRecordItemState?) {
        itemToEdit = item
        isShowRecordActionSheet = true
    }

    fun closeRecordActionSheet() {
        itemToEdit = null
        isShowRecordActionSheet = false
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
                    state.itemToEdit?.let { itemToEdit ->
                        listOf(
                            itemToEdit.id,
                            with(RecipeRecordUiModel.saver) { save(itemToEdit.record) },
                        )
                    },
                    state.isShowRecordEditSheet
                )
            },
            restore = {
                RecipeRecordState(
                    initial = emptyList(),
                    nextId = it[0] as Long,
                ).apply {
                    @Suppress("UNCHECKED_CAST")
                    val itemRows = it[1] as List<List<Any>>

                    @Suppress("UNCHECKED_CAST")
                    val itemToEditRows = it[2] as List<Any>?

                    itemRows.forEach { row ->
                        items += RecipeRecordItemState(
                            id = row[0] as Long,
                            record = RecipeRecordUiModel.saver.restore(row[1]) as RecipeRecordUiModel
                        )
                    }
                    itemToEdit = itemToEditRows?.let { row ->
                        RecipeRecordItemState(
                            id = row[0] as Long,
                            record = RecipeRecordUiModel.saver.restore(row[1]) as RecipeRecordUiModel
                        )
                    }
                    isShowRecordEditSheet = it[3] as Boolean
                }
            }
        )
    }
}

fun RecipeRecordState.toUiModel(): List<RecipeRecordUiModel> =
    items.map { it.record }

@Composable
fun rememberRecipeRecordState(
    initial: List<RecipeRecordUiModel>
) = rememberSaveable(saver = RecipeRecordState.saver) {
    RecipeRecordState(initial = initial)
}