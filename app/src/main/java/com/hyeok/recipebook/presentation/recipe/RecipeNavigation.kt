package com.hyeok.recipebook.presentation.recipe

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavGraphBuilder.recipeScreen(
    modifier: Modifier = Modifier
) {
    composable<Route.Recipe> {
        RecipeScreen(
            modifier = modifier
        )
    }
}