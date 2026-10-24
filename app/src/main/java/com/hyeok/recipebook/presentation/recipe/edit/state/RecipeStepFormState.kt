package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextFieldState.Saver.restore
import androidx.compose.foundation.text.input.TextFieldState.Saver.save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel

@Stable
class RecipeStepItemFormState(
    val id: Long,
    val stepId: Long = 0L,
    val order: Int,
    val description: TextFieldState
) {
    constructor(
        id: Long,
        stepId: Long = 0L,
        order: Int,
        description: String = ""
    ) : this(
        id = id,
        stepId = stepId,
        order = order,
        description = TextFieldState(initialText = description)
    )

    fun isValid() = description.text.isNotBlank()
}

@Stable
class RecipeStepFormState(
    initial: List<RecipeStepUiModel>,
    private var nextId: Long = 0L
) {
    val items = mutableStateListOf<RecipeStepItemFormState>().apply {
        initial.forEach { uiModel ->
            add(RecipeStepItemFormState(
                id = nextId++,
                stepId = uiModel.id,
                order = uiModel.order,
                description = uiModel.description
            ))
        }
    }

    fun add() {
        items.add(
            RecipeStepItemFormState(
                id = nextId++,
                order = items.size,
            )
        )
    }

    fun remove(id: Long) {
        items.removeIf { it.id == id }
    }

    fun isValid() = items.all { it.isValid() }

    companion object {
        val saver = listSaver(
            save = { state ->
                listOf(
                    state.nextId,
                    state.items.mapIndexed { index, state ->
                        listOf(
                            state.id,
                            state.stepId,
                            index + 1,
                            save(state.description)
                        )
                    }
                )
            },
            restore = {
                RecipeStepFormState(
                    initial = emptyList()
                ).apply {
                    @Suppress("UNCHECKED_CAST")
                    val rows = it[1] as List<List<Any>>

                    rows.forEach { row ->
                        items += RecipeStepItemFormState(
                            id = row[0] as Long,
                            stepId = row[1] as Long,
                            order = row[2] as Int,
                            description = restore(row[3])!!
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun rememberRecipeStepFormState(
    initial: List<RecipeStepUiModel>
) = rememberSaveable(saver = RecipeStepFormState.saver) {
    RecipeStepFormState(initial = initial)
}