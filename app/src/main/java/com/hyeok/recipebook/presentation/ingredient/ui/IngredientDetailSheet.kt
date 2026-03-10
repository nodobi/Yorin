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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.hyeok.recipebook.designsystem.components.YorinModalBottomSheet
import com.hyeok.recipebook.designsystem.components.YorinProgressBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.ingredient.state.IngredientDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetailSheet(
    ingredientDetailUiState: IngredientDetailUiState,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onClickRecipe: (String) -> Unit = {},
    onEditIngredient: () -> Unit = {},
    onDeleteIngredient: () -> Unit = {}
) {
    YorinModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismiss = onDismiss
    ) {
        IngredientDetailLayout(
            modifier = modifier,
            ingredientDetailUiState = ingredientDetailUiState,
            onDismiss = onDismiss,
            onClickRecipe = onClickRecipe,
            onEditIngredient = onEditIngredient,
            onDeleteIngredient = onDeleteIngredient
        )
    }
}

@Composable
fun IngredientDetailLayout(
    ingredientDetailUiState: IngredientDetailUiState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onClickRecipe: (String) -> Unit = {},
    onEditIngredient: () -> Unit = {},
    onDeleteIngredient: () -> Unit = {}
) {
    Column(
        modifier = modifier,
    ) {
        YorinAppbar(
            title = ingredientDetailUiState.ingredient.name,
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
                modifier = Modifier.fillMaxWidth(),
                progress = ingredientDetailUiState.expirationProgress,
                remainExpirationDate = ingredientDetailUiState.remainExpirationDays,
            )

            IngredientDetails(
                modifier = Modifier.fillMaxWidth(),
                weight = "${ingredientDetailUiState.ingredient.weight}${ingredientDetailUiState.ingredient.weightUnit}",
                purchaseDate = ingredientDetailUiState.ingredient.formatPurchaseDate(),
                expirationDate = ingredientDetailUiState.ingredient.formatExpirationDate(),
            )

            IngredientDescription(
                modifier = Modifier.fillMaxWidth(),
                description = ingredientDetailUiState.ingredient.description
            )

            if (ingredientDetailUiState.relatedRecipes.isNotEmpty()) {
                IngredientRecipes(
                    modifier = Modifier.fillMaxWidth(),
                    recipes = ingredientDetailUiState.relatedRecipes,
                    onClick = onClickRecipe
                )
            }
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

@Composable
private fun ExpirationProgress(
    progress: Float,
    remainExpirationDate: Int,
    modifier: Modifier = Modifier,
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
                    text = stringResource(R.string.ingredient_detail_expiration_date_title),
                    style = YorinTheme.typography.button1
                )
                YorinText(
                    text = stringResource(
                        R.string.ingredient_remain_expiration_days,
                        remainExpirationDate
                    ),
                    style = YorinTheme.typography.button1
                )
            }

            YorinProgressBar(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                progressBarColor = YorinTheme.colors.main2,
                containerColor = YorinTheme.colors.black6
            )
        }
    }
}

@Composable
private fun IngredientDetails(
    modifier: Modifier = Modifier,
    weight: String,
    purchaseDate: String,
    expirationDate: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_amount_title),
            value = weight
        )

        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_purchase_date_title),
            value = purchaseDate
        )

        IngredientDetailRow(
            title = stringResource(R.string.ingredient_detail_expiration_date_title),
            value = expirationDate
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

@Preview
@Composable
private fun IngredientDetailSheetPreview() {
    YorinTheme {
        IngredientDetailLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(YorinTheme.colors.black7),
            ingredientDetailUiState = IngredientDetailUiState.fake()
        )
    }
}