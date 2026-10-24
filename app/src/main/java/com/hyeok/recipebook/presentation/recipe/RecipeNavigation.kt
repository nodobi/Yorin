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
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailUiState
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailViewModel
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditRoute
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditViewModel
import com.hyeok.recipebook.presentation.recipe.list.RecipeRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipesViewModel

fun NavController.navigateToRecipe(navOptions: NavOptions? = null) {
    navigate(Route.Recipe.Recipes, navOptions)
}

fun NavController.navigateToRecipeDetail(navOptions: NavOptions? = null, recipeId: Long) {
    navigate(
        Route.Recipe.Detail(
            recipeId
        ), navOptions
    )
}

fun NavController.navigateToRecipeEdit(navOptions: NavOptions? = null, recipeId: Long?) {
    navigate(
        Route.Recipe.Edit(
            recipeId
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
            val recipesUiState by viewModel.state.collectAsStateWithLifecycle()

            // TODO:: 레시피 삭제 모달 작성 #49
            RecipeRoute(
                recipesUiState = recipesUiState,
                searchQueryState = viewModel.searchQueryState,
                onAddRecipe = {
                    navController.navigateToRecipeEdit(recipeId = null)
                },
                onClickRecipe = { recipeId ->
                    navController.navigateToRecipeDetail(recipeId = recipeId)
                },
                onSelectFilter = { newFilter ->
                    viewModel.updateFilter(newFilter)
                }
            )
        }

        composable<Route.Recipe.Detail> { backStackEntry ->
            val viewModel = hiltViewModel<RecipeDetailViewModel>()
            val recipeId by viewModel.recipeId.collectAsStateWithLifecycle()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            RecipeDetailRoute(
                state = state,
                navigateUp = {
                    when (state) {
                        is RecipeDetailUiState.Edit, RecipeDetailUiState.Loading -> Unit

                        is RecipeDetailUiState.Success -> navController.navigateUp()
                    }
                },
                onEditStart = {
                    navController.navigateToRecipeEdit(recipeId = recipeId)
                },
                onAddRecord = { newRecord ->
                    viewModel.addRecord(newRecord)
                }
            )
        }

        composable<Route.Recipe.Edit> {
            val viewModel = hiltViewModel<RecipeEditViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            RecipeEditRoute(
                state = uiState,
                navigateUp = {
                    navController.navigateUp()
                },
                onCompleteEdit = { }
            )
        }
    }
}