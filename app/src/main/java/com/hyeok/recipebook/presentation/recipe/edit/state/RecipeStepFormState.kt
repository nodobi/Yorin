package com.hyeok.recipebook.presentation.recipe.edit.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.presentation.recipe.component.formfield.FormFieldState
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel

@Stable
class RecipeStepItemFormState(
    val id: Long,
    val stepId: Long = 0L,
    val order: Int,
    val description: FormFieldState
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
        description = FormFieldState(initialText = description, FormType.RecipeStepDescription)
    )

    fun isValid() = description.isValid

    fun revealError() {
        description.revealError()
    }

    fun hideError() {
        description.hideError()
    }
}

@Stable
class RecipeStepFormState(
    initial: List<RecipeStepUiModel>,
    private var nextId: Long = 0L
) {
    val items = mutableStateListOf<RecipeStepItemFormState>().apply {
        initial.forEach { uiModel ->
            add(
                RecipeStepItemFormState(
                    id = nextId++,
                    stepId = uiModel.id,
                    order = uiModel.order,
                    description = uiModel.description
                )
            )
        }
    }

    val isRemovable: Boolean get() = items.size > 1

    fun add() {
        items.add(
            RecipeStepItemFormState(
                id = nextId++,
                order = items.size,
            )
        )
    }

    fun remove(id: Long) {
        if (!isRemovable) return
        items.removeIf { it.id == id }
    }

    /**
     * RecipeIngredientFormState 와 마찬가지로 내용이 공백인 항목은 저장하지 않기 때문에 에러만 보여주고 검증 결과에는 포함하지 않는다.
     */
    fun isValid() = items
        .onEach { it.revealError() }
        .filter { it.description.text.isNotBlank() }
        .all { it.isValid() }

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
                            with(FormFieldState.saver(FormType.RecipeStepDescription)) { save(state.description) }
                        )
                    }
                )
            },
            restore = {
                RecipeStepFormState(
                    initial = emptyList(),
                    nextId = it[0] as Long
                ).apply {
                    @Suppress("UNCHECKED_CAST")
                    val rows = it[1] as List<List<Any>>

                    rows.forEach { row ->
                        items += RecipeStepItemFormState(
                            id = row[0] as Long,
                            stepId = row[1] as Long,
                            order = row[2] as Int,
                            description = FormFieldState.saver(FormType.RecipeStepDescription).restore(row[3])!!
                        )
                    }
                }
            }
        )
    }
}

fun RecipeStepFormState.toUiModel(): List<RecipeStepUiModel> =
    items.filter { it.description.text.isNotBlank() }
        .mapIndexed { index, state ->
            RecipeStepUiModel(
                id = state.stepId,
                order = index + 1,
                description = state.description.text
            )
        }

@Composable
fun rememberRecipeStepFormState(
    initial: List<RecipeStepUiModel>
) = rememberSaveable(saver = RecipeStepFormState.saver) {
    RecipeStepFormState(
        initial = initial
    ).apply { if (initial.isEmpty()) add() }
}