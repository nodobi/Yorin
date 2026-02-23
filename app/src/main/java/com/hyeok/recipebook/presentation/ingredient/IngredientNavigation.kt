package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavGraphBuilder.ingredientScreen(
    modifier: Modifier = Modifier
) {
    composable<Route.Ingredient> {
        IngredientScreen(
            modifier = modifier
        )
    }
}