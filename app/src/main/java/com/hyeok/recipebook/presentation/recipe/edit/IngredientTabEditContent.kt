package com.hyeok.recipebook.presentation.recipe.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.byValue
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.data.validation.FormValidator
import com.hyeok.recipebook.designsystem.components.DefaultSpinnerDropdown
import com.hyeok.recipebook.designsystem.components.DefaultSpinnerHeader
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinSpinner
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.AddItemCard
import com.hyeok.recipebook.presentation.recipe.component.formfield.BasicFormField
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeIngredientEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.WeightUnitUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeIngredientFormState
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeIngredientItemFormState

@Composable
fun IngredientTabEditContent(
    state: RecipeIngredientFormState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                ),
            verticalArrangement = spacedBy(12.dp)
        ) {
            item {
                YorinText(
                    text = stringResource(R.string.recipe_detail_ingredient_title),
                    style = YorinTheme.typography.body1
                )
            }

            items(
                state.items,
                key = { it.id }
            ) { formState ->
                RecipeIngredientEditCard(
                    modifier = Modifier,
                    state = formState,
                    onRemoveIngredient = {
                        state.remove(formState.id)
                    }
                )
            }

            item {
                AddItemCard(
                    modifier = Modifier,
                    onClick = {
                        state.add()
                    }
                )
            }
        }
    }
}

@Composable
private fun RecipeIngredientEditCard(
    state: RecipeIngredientItemFormState,
    modifier: Modifier = Modifier,
    onRemoveIngredient: () -> Unit
) {
    val unitGroup = remember { WeightUnitUiModel.entries.map { it.name } }

    YorinCard(
        modifier = modifier,
        stroke = BorderStroke(
            width = 1.dp,
            color = YorinTheme.colors.black5
        ),
        backgroundColor = YorinTheme.colors.black7
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YorinText(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.recipe_detail_ingredient_name_label),
                        textAlign = TextAlign.Start,
                        style = YorinTheme.typography.caption1,
                        color = YorinTheme.colors.black3
                    )

                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onRemoveIngredient()
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close_circle),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )
                }

                BasicFormField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = state.name,
                    textStyle = YorinTheme.typography.button1,
                    placeHolderColor = YorinTheme.colors.black3,
                    placeHolder = stringResource(R.string.recipe_detail_ingredient_name_hint)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = spacedBy(8.dp)
                ) {
                    YorinText(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.recipe_detail_ingredient_amount_label),
                        textAlign = TextAlign.Start,
                        style = YorinTheme.typography.caption1,
                        color = YorinTheme.colors.black3
                    )

                    BasicFormField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = state.quantity,
                        textStyle = YorinTheme.typography.button1,
                        placeHolderColor = YorinTheme.colors.black3,
                        placeHolder = stringResource(R.string.recipe_detail_ingredient_amount_hint),
                        keyboardAction = KeyboardOptions(keyboardType = KeyboardType.Number),
                        inputTransformation = InputTransformation.byValue { current, proposed ->
                            if (proposed.all { it.isDigit() } && proposed.length <= FormValidator.WEIGHT_QUANTITY_MAX.toString().length) proposed
                            else current
                        },
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = spacedBy(8.dp)
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
                        header = { expended, onClick ->
                            DefaultSpinnerHeader(
                                modifier = Modifier
                                    .height(44.dp)
                                    .background(
                                        color = YorinTheme.colors.black6,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                text = state.unit.name,
                                textStyle = YorinTheme.typography.button1,
                                placeHolderColor = YorinTheme.colors.black3,
                                expended = expended,
                                onClick = onClick,
                            )
                        },
                        dropdown = { onDismiss ->
                            DefaultSpinnerDropdown(
                                items = unitGroup,
                                selectedItem = state.unit.name,
                                onItemSelected = { unit ->
                                    state.updateUnit(unit)
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

@BackgroundPreview
@Composable
private fun IngredientTabEditContentPreview() {
    YorinTheme {
        IngredientTabEditContent(
            state = RecipeIngredientFormState(
                listOf(
                    RecipeIngredientEditUiModel(1, "재료1", WeightUnitUiModel.g.name, 111),
                    RecipeIngredientEditUiModel(2, "재료2", WeightUnitUiModel.kg.name, 222),
                    RecipeIngredientEditUiModel(3, "재료3", WeightUnitUiModel.ml.name, 333)
                )
            )
        )
    }
}