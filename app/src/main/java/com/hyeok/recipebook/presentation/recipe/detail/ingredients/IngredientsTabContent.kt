package com.hyeok.recipebook.presentation.recipe.detail.ingredients

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.DefaultSpinnerDropdown
import com.hyeok.recipebook.designsystem.components.DefaultSpinnerHeader
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinNumberTextField
import com.hyeok.recipebook.designsystem.components.YorinSpinner
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun IngredientsTabContent(
    modifier: Modifier = Modifier,
    ingredients: List<RecipeIngredientUiModel>,
    isEditing: Boolean = false
) {

    val ingredientEditState = remember(ingredients) {
        ingredients.map { recipeIngredient ->
            RecipeIngredientEditState(
                id = recipeIngredient.id,
                initialName = recipeIngredient.name,
                initialQuantity = recipeIngredient.requireQuantity,
                initialUnit = recipeIngredient.unit
            )
        }
    }

    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.recipe_detail_ingredient_title),
            style = YorinTheme.typography.body1
        )

        if (isEditing) {
            ingredientEditState.forEach {
                EditingRecipeIngredientCard(
                    modifier = modifier.fillMaxWidth(),
                    editState = it
                )
            }
        } else {
            ingredients.forEach {
                RecipeIngredientCard(
                    modifier = modifier.fillMaxWidth(),
                    recipeIngredient = it
                )
            }
        }
    }
}

// 수정중인 레시피 데이터를 담는 stateholder
@Stable
class RecipeIngredientEditState(
    val id: Int,
    initialName: String = "",
    initialQuantity: Int = 0,
    initialUnit: String = "g"
) {
    val name = TextFieldState(initialText = initialName)
    val quantity = TextFieldState(initialText = if (initialQuantity == 0) "" else initialQuantity.toString())
    var unit = initialUnit
}

@Composable
private fun EditingRecipeIngredientCard(
    editState: RecipeIngredientEditState,
    modifier: Modifier = Modifier,
) {

    // TODO:: 상수로 분리
    val unitGroup = listOf("g", "kg", "ml", "l")

    YorinCard(
        modifier = modifier,
        stroke = BorderStroke(
            width = 1.dp,
            color = YorinTheme.colors.black5
        ),
        backgroundColor = YorinTheme.colors.black7
    ) {
        Column(
            modifier = modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LabeledTextField(
                modifier = Modifier.fillMaxWidth(),
                state = editState.name,
                label = stringResource(R.string.recipe_detail_ingredient_name_label),
                placeHolder = stringResource(R.string.recipe_detail_ingredient_name_hint)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabeledNumberTextField(
                    modifier = Modifier.weight(1f),
                    state = editState.quantity,
                    label = stringResource(R.string.recipe_detail_ingredient_amount_label),
                    placeHolder = stringResource(R.string.recipe_detail_ingredient_amount_hint)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    YorinText(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.recipe_detail_ingredient_unit_label),
                        textAlign = TextAlign.Start,
                        style = YorinTheme.typography.caption1,
                        color = YorinTheme.colors.black3
                    )

                    YorinSpinner(
                        modifier = Modifier,
                        header = { expended, onClick ->
                            DefaultSpinnerHeader(
                                modifier = Modifier
                                    .height(36.dp)
                                    .background(
                                        color = YorinTheme.colors.black6,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                text = editState.unit,
                                textStyle = YorinTheme.typography.button1,
                                placeHolderColor = YorinTheme.colors.black3,
                                expended = expended,
                                onClick = onClick,
                            )
                        },
                        dropdown = { onDismiss ->
                            DefaultSpinnerDropdown(
                                modifier = Modifier,
                                items = unitGroup,
                                selectedItem = editState.unit,
                                onItemSelected = {
                                    editState.unit = it
                                    onDismiss()
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LabeledTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    placeHolder: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        YorinText(
            modifier = Modifier
                .fillMaxWidth(),
            text = label,
            textAlign = TextAlign.Start,
            style = YorinTheme.typography.caption1,
            color = YorinTheme.colors.black3
        )

        YorinTextField(
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth(),
            state = state,
            textStyle = YorinTheme.typography.button1,
            placeHolderColor = YorinTheme.colors.black3,
            placeHolder = placeHolder
        )
    }
}

@Composable
private fun LabeledNumberTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    placeHolder: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        YorinText(
            modifier = Modifier
                .fillMaxWidth(),
            text = label,
            textAlign = TextAlign.Start,
            style = YorinTheme.typography.caption1,
            color = YorinTheme.colors.black3
        )

        YorinNumberTextField(
            modifier = Modifier
                .height(36.dp)
                .fillMaxWidth(),
            state = state,
            textStyle = YorinTheme.typography.button1,
            placeHolderColor = YorinTheme.colors.black3,
            placeHolder = placeHolder
        )
    }
}

@BackgroundPreview
@Composable
private fun IngredientsTabContentPreview() {
    IngredientsTabContent(
        modifier = Modifier.fillMaxWidth(),
        ingredients = listOf(
            RecipeIngredientUiModel.dummy1,
            RecipeIngredientUiModel.dummy2,
        ),
        isEditing = false
    )
}

@BackgroundPreview
@Composable
private fun EditingIngredientItemPreview() {
    EditingRecipeIngredientCard(
        editState = RecipeIngredientEditState(id = 0)
    )
}
