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
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeIngredientEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.WeightUnitUiModel

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

    var unit by mutableStateOf<WeightUnitUiModel>(initialUnit)
        private set

    fun updateUnit(unit: WeightUnitUiModel) {
        this.unit = unit
    }

    fun updateUnit(unit: String) {
        this.unit = WeightUnitUiModel.valueOf(unit)
    }

    fun revealError() {
        name.revealError()
        quantity.revealError()
    }

    fun hideError() {
        name.hideError()
        quantity.hideError()
    }

    fun isValid() = name.isValid && quantity.isValid
}

@Stable
class RecipeIngredientFormState(
    initial: List<RecipeIngredientEditUiModel>,
    private var nextId: Long = 0L
) {
    val items = mutableStateListOf<RecipeIngredientItemFormState>().apply {
        initial.forEach { uiModel ->
            add(
                RecipeIngredientItemFormState(
                    id = nextId++,
                    ingredientId = uiModel.id,
                    name = uiModel.name,
                    quantity = uiModel.requireQuantity.takeIf { it != 0 }?.toString() ?: "",
                    unit = WeightUnitUiModel.valueOf(uiModel.unit),
                )
            )
        }
    }

    val isRemovable: Boolean get() = items.size > 1

    fun add() {
        items.add(
            RecipeIngredientItemFormState(
                id = nextId++
            )
        )
    }

    fun remove(id: Long) {
        if (!isRemovable) return

        items.removeIf {
            it.id == id
        }
    }

    /**
     * 재료의 이름, 수량이 입력되지 않은 항목에 대해서는 저장이 되지 않게 할 예정이기 때문에 에러만 보여주고 검증 결과에는 포함하지 않는다.
     */
    fun isValid() = items
        .onEach { it.revealError() }
        .filter { it.name.text.isNotBlank() || it.quantity.text.isNotBlank() }
        .all { it.isValid() }

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
                            it.unit.name
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

fun RecipeIngredientFormState.toUiModel(): List<RecipeIngredientEditUiModel> =
    items.filter { it.name.text.isNotBlank() || it.quantity.text.isNotBlank() }
        .map { item ->
            RecipeIngredientEditUiModel(
                id = item.ingredientId,
                name = item.name.text,
                unit = item.unit.name,
                requireQuantity = item.quantity.text.toIntOrNull() ?: throw IllegalStateException("requireQuantity must be an int, ${item.quantity.text}")
            )
        }

@Composable
fun rememberRecipeIngredientFormState(
    initial: List<RecipeIngredientEditUiModel>
): RecipeIngredientFormState = rememberSaveable(saver = RecipeIngredientFormState.saver) {
    RecipeIngredientFormState(
        initial = initial
    ).apply { if (initial.isEmpty()) add() }
}