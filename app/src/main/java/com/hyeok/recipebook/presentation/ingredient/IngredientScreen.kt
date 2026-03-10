package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinSearchbar
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.ingredient.component.ExpirationWarningCard
import com.hyeok.recipebook.presentation.ingredient.component.IngredientCard
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.state.IngredientsUiState

@Composable
fun IngredientRoute(
    ingredientsUiState: IngredientsUiState,
    searchQueryState: TextFieldState = rememberTextFieldState(),
    onClickIngredient: (IngredientUiModel) -> Unit = {},
    onClickAddIngredient: () -> Unit = {}
) {
    IngredientScreen(
        modifier = Modifier
            .fillMaxSize(),
        searchQueryState = searchQueryState,
        ingredientsUiState = ingredientsUiState,
        onClickIngredient = { ingredient ->
            onClickIngredient(ingredient)
        },
        onClickAddIngredient = {
            onClickAddIngredient()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientScreen(
    ingredientsUiState: IngredientsUiState,
    modifier: Modifier = Modifier,
    searchQueryState: TextFieldState = rememberTextFieldState(),
    onClickIngredient: (IngredientUiModel) -> Unit = {},
    onClickAddIngredient: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        YorinAppbar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.ingredient_title),
            titleAlignment = Alignment.CenterStart,
            action = {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onClickAddIngredient()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                    contentDescription = null,
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            YorinSearchbar(
                modifier = Modifier.fillMaxWidth(),
                state = searchQueryState,
                onSearch = {}
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = YorinTheme.colors.black6
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (ingredientsUiState.expiredCount > 0) {
                    item {
                        ExpirationWarningCard(
                            modifier = Modifier.fillMaxWidth(),
                            expiredCount = ingredientsUiState.expiredCount,
                            onClick = {}
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                itemsIndexed(ingredientsUiState.ingredients) { idx, ingredientItem ->
                    IngredientCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        ingredientName = ingredientItem.name,
                        remainExpirationDate = ingredientsUiState.remainExpirationDays[idx],
                        expirationDate = ingredientItem.formatExpirationDate(),
                        weight = ingredientItem.weight,
                        weightUnit = ingredientItem.weightUnit,
                        description = ingredientItem.description,
                        onClick = {
                            onClickIngredient(ingredientItem)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun IngredientScreenPreview() {
    YorinTheme {
        IngredientScreen(
            ingredientsUiState = IngredientsUiState.fake()
        )
    }
}