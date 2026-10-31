package com.hyeok.recipebook.presentation.recipe.detail.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel

@Composable
fun IngredientTabContent(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.recipe_detail_ingredient_title),
            style = YorinTheme.typography.body1
        )

        recipe.ingredients.forEach {
            RecipeIngredientCard(
                modifier = modifier.fillMaxWidth(),
                recipeIngredient = it
            )
        }
    }
}

@BackgroundPreview
@Composable
private fun IngredientTabContentPreview() {
    YorinTheme {
        IngredientTabContent(
            recipe = RecipeUiModel.fake(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}