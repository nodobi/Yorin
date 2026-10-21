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
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.presentation.recipe.component.formfield.FormFieldState
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.edit.WeightUnitUiModel

@Stable
class RecipeIngredientItemFormState(
    val id: Long,
    val ingredientId: Long = 0L,
    val name: FormFieldState,
    val quantity: FormFieldState,
    initialUnit: WeightUnitUiModel = WeightUnitUiModel.g
) {
    constructor(
        id: Long,
        ingredientId: Long = 0L,
        name: String = "",
        quantity: String = "",
        unit: WeightUnitUiModel = WeightUnitUiModel.g
    ) : this(
        id = id,
        ingredientId = ingredientId,
        name = FormFieldState(name, type = FormType.IngredientName),
        quantity = FormFieldState(quantity, type = FormType.WeightQuantity),
        initialUnit = unit
    )

    var unit by mutableStateOf<String>(initialUnit.name)
        private set

    fun isValid() = name.isValid && quantity.isValid
}

@Stable
class RecipeIngredientFormState(
    initial: List<RecipeIngredientUiModel>,
    private var nextId: Long = 0L
) {
    val items = mutableStateListOf<RecipeIngredientItemFormState>().apply {
        initial.forEach { uiModel ->
            add(RecipeIngredientItemFormState(
                id = nextId++,
                ingredientId = uiModel.id,
                name = uiModel.name,
                quantity = uiModel.requireQuantity.toString(),
                unit = WeightUnitUiModel.valueOf(uiModel.unit),
            ))
        }
    }

    fun add() {
        items.add(
            RecipeIngredientItemFormState(
                id = nextId++
            )
        )
    }

    fun remove(id: Long) {
        items.removeIf {
            it.id == id
        }
    }

    fun isValid() = items.all { it.isValid() }

    companion object {
        private val nameSaver: Saver<FormFieldState, Any> = FormFieldState.saver(FormType.IngredientName)
        private val quantitySaver: Saver<FormFieldState, Any> = FormFieldState.saver(FormType.WeightQuantity)

        val saver = listSaver(
            save = { state ->
                listOf(
                    state.nextId,
                    state.items.map {
                        listOf(
                            it.id,
                            it.ingredientId,
                            with(nameSaver) { save(it.name) },
                            with(quantitySaver) { save(it.quantity) },
                            it.unit
                        )
                    }
                )
            },
            restore = {
                RecipeIngredientFormState(
                    initial = emptyList(),
                    nextId = it[0] as Long
                ).apply {
                    @Suppress("UNCHECKED_CAST")
                    val rows = it[1] as List<List<Any>>

                    rows.forEach { row ->
                        items += RecipeIngredientItemFormState(
                            id = row[0] as Long,
                            ingredientId = row[1] as Long,
                            name = nameSaver.restore(row[2])!!,
                            quantity = quantitySaver.restore(row[3])!!,
                            initialUnit = WeightUnitUiModel.valueOf(row[4] as String)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun rememberRecipeIngredientFormState(
    initial: List<RecipeIngredientUiModel>
): RecipeIngredientFormState = rememberSaveable(saver = RecipeIngredientFormState.saver) {
    RecipeIngredientFormState(initial = initial)
}