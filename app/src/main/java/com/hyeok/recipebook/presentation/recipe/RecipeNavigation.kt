package com.hyeok.recipebook.presentation.recipe

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavController.navigateToRecipe(navOptions: NavOptions? = null) {
    navigate(Route.Recipe, navOptions)
}

fun NavGraphBuilder.recipeScreen(
    navController: NavHostController
) {
    composable<Route.Recipe> {
        RecipeRoute(
        )
    }
}