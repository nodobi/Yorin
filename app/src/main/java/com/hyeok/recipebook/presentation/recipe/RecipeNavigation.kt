package com.hyeok.recipebook.presentation.recipe

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipeRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipesUiState

fun NavController.navigateToRecipe(navOptions: NavOptions? = null) {
    navigate(Route.Recipe.Recipes, navOptions)
}

fun NavController.navigateToRecipeDetail(navOptions: NavOptions? = null, ingredientId: Int) {
    navigate(
        Route.Recipe.Detail(
            ingredientId
        ), navOptions
    )
}

fun NavGraphBuilder.recipeScreen(
    navController: NavHostController
) {
    navigation<Route.Recipe>(
        startDestination = Route.Recipe.Recipes,
    ) {
        composable<Route.Recipe.Recipes> {
            RecipeRoute(
                recipesUiState = RecipesUiState(recipes = listOf()),
                searchQueryState = rememberTextFieldState(),
                onAddRecipe = {},
                onSelectFilter = {}
            )
        }

        composable<Route.Recipe.Detail> {
            RecipeDetailRoute(

            )
        }
    }
}