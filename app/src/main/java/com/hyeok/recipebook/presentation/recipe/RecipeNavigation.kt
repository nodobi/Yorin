package com.hyeok.recipebook.presentation.recipe

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailRoute
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailViewModel
import com.hyeok.recipebook.presentation.recipe.list.RecipeRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipesViewModel

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
            val viewModel = hiltViewModel<RecipesViewModel>()
            val recipesUiState by viewModel.recipesUiState.collectAsStateWithLifecycle()

            RecipeRoute(
                recipesUiState = recipesUiState,
                searchQueryState = viewModel.searchedQuery,
                onAddRecipe = {},
                onSelectFilter = {}
            )
        }

        composable<Route.Recipe.Detail> { backStackEntry ->
            val viewModel = hiltViewModel<RecipeDetailViewModel>()
            val recipeDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()

            RecipeDetailRoute(
                state = recipeDetailUiState,
                onEditRecipe = {},
                onConfirmRecipe = {},
            )
        }
    }
}