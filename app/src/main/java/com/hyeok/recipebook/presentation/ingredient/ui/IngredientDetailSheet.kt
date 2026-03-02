package com.hyeok.recipebook.presentation.ingredient.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.ButtonSize
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinProgressBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetailSheet(
    modifier: Modifier = Modifier,
    ingredientUiState: IngredientUiState,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onClickRecipe: (String) -> Unit = {},
    onEditIngredient: () -> Unit = {},
    onDeleteIngredient: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth(),
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .height(4.dp)
                        .background(YorinTheme.colors.black5)
                )
            }
        },
        containerColor = YorinTheme.colors.black7,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = modifier,
        ) {
            YorinAppbar(
                title = ingredientUiState.name,
                titleAlignment = Alignment.CenterStart,
                action = {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onDismiss()
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ExpirationProgress(
                    modifier = Modifier.fillMaxWidth()
                )

                IngredientDetails(
                    modifier = Modifier.fillMaxWidth(),
                    ingredientUiState = ingredientUiState
                )

                IngredientDescription(
                    modifier = Modifier.fillMaxWidth(),
                    description = ingredientUiState.description
                )

                IngredientRecipes(
                    modifier = Modifier.fillMaxWidth(),
                    recipes = listOf("레시피1", "레시피2", "레시피3"),
                    onClick = onClickRecipe
                )
            }
            HorizontalDivider(color = YorinTheme.colors.black5)
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_do_edit),
                    onClick = onEditIngredient,
                    shape = ButtonShape.Round,
                    size = ButtonSize.Large,
                    color = ButtonColor.Secondary
                )
                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_do_delete),
                    onClick = onDeleteIngredient,
                    shape = ButtonShape.Round,
                    size = ButtonSize.Large,
                    color = ButtonColor.Warning
                )

            }
        }
    }
}

@Composable
private fun ExpirationProgress(
    modifier: Modifier = Modifier
) {
    YorinCard(
        modifier = modifier,
        backgroundColor = YorinTheme.colors.main8,
        shape = RoundedCornerShape(14.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                YorinText(
                    text = "유통기한",
                    style = YorinTheme.typography.button1
                )
                YorinText(
                    text = "4일",
                    style = YorinTheme.typography.button1
                )
            }

            YorinProgressBar(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Composable
private fun IngredientDetails(
    modifier: Modifier = Modifier,
    ingredientUiState: IngredientUiState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_amount_title),
            value = "${ingredientUiState.weight}${ingredientUiState.weightUnit}"
        )

        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_purchase_date_title),
            value = ingredientUiState.toPurchaseDateFormat()
        )

        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_expiration_date_title),
            value = ingredientUiState.toExpirationDateFormat()
        )
    }
}

@Composable
private fun IngredientDetailRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            YorinText(
                text = title,
                style = YorinTheme.typography.body2
            )
            YorinText(
                text = value,
                style = YorinTheme.typography.body2
            )
        }
        HorizontalDivider(color = YorinTheme.colors.black5)
    }
}

@Composable
private fun IngredientDescription(
    modifier: Modifier = Modifier,
    description: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.ingredient_detail_description_title),
            style = YorinTheme.typography.body2
        )
        YorinCard(
            modifier = Modifier,
            backgroundColor = YorinTheme.colors.main8
        ) {
            YorinText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = description,
                style = YorinTheme.typography.body5
            )
        }
    }
}

@Composable
private fun IngredientRecipes(
    modifier: Modifier = Modifier,
    recipes: List<String> = emptyList(),
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.ingredient_detail_related_recipes_title),
            style = YorinTheme.typography.body2
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            recipes.forEach { name ->
                IngredientRecipeRow(
                    name = name,
                    onClick = { onClick(name) },
                )
            }
        }
    }
}

@Composable
private fun IngredientRecipeRow(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    YorinCard(
        modifier = modifier
            .clickable {
                onClick()
            }
            .defaultMinSize(minHeight = 48.dp),
        backgroundColor = YorinTheme.colors.main8
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            YorinText(
                text = name,
                style = YorinTheme.typography.body4
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
                contentDescription = null,
                tint = YorinTheme.colors.black2
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun IngredientDetailSheetPreview() {
    YorinTheme {
        IngredientDetailSheet(
            onDismiss = {},
            ingredientUiState = IngredientUiState.mockState()
        )
    }
}