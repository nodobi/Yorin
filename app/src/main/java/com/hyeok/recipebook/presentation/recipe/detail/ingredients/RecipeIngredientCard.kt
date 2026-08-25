package com.hyeok.recipebook.presentation.recipe.detail.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun RecipeIngredientCard(
    recipeIngredient: RecipeIngredientUiModel,
    modifier: Modifier = Modifier
) {
    YorinCard(
        modifier = modifier
            .height(64.dp),
        backgroundColor = if (recipeIngredient.isInStock) {
            YorinTheme.colors.success3
        } else {
            YorinTheme.colors.black6
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            YorinText(
                text = recipeIngredient.name,
                style = YorinTheme.typography.body3
            )
            YorinText(
                text = "${recipeIngredient.requireQuantity}${recipeIngredient.unit}",
                style = YorinTheme.typography.body3
            )
        }
    }
}

@Preview
@Composable
private fun RecipeIngredientCardPreview() {
    RecipeIngredientCard(
        RecipeIngredientUiModel.Companion.dummy1
    )
}