package com.hyeok.recipebook.presentation.ingredient

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavGraphBuilder.ingredientScreen(
    navigateUp: () -> Unit,
) {
    composable<Route.Ingredient> {
        IngredientRoute(
            onClickAddIngredient = {},
            onClickEditIngredient = {}
        )
    }
}