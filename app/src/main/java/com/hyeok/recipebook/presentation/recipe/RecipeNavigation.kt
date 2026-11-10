package com.hyeok.recipebook.presentation.recipe

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyeok.recipebook.R
import com.hyeok.recipebook.presentation.LocalSnackbarHostState
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailRoute
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailUiState
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailViewModel
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditRoute
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditViewModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import com.hyeok.recipebook.presentation.recipe.list.RecipeRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipesViewModel

fun NavController.navigateToRecipe(navOptions: NavOptions? = null) {
    navigate(Route.Recipe.Recipes, navOptions)
}

fun NavController.navigateToRecipeDetail(recipeId: Long, navOptions: NavOptions? = null) {
    navigate(
        Route.Recipe.Detail(
            recipeId
        ), navOptions
    )
}

fun NavController.navigateToRecipeEdit(recipeId: Long?, navOptions: NavOptions? = null) {
    navigate(
        Route.Recipe.Edit(
            recipeId
        ), navOptions
    )
}

fun NavGraphBuilder.recipeScreen(
    navController: NavHostController,
) {
    navigation<Route.Recipe>(
        startDestination = Route.Recipe.Recipes,
    ) {
        composable<Route.Recipe.Recipes> {
            val viewModel = hiltViewModel<RecipesViewModel>()
            val recipesUiState by viewModel.state.collectAsStateWithLifecycle()

            val snackbarHostState = LocalSnackbarHostState.current

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

            LaunchedEffect(recipesUiState) {
                if (recipesUiState.isFailToLoad) {
                    // TODO:: 오류 상황일 때 레시피 목록을 재시도 버튼이 있는 실패 화면으로 변경하기
                    snackbarHostState.showMessage(R.string.error_fail_to_load_recipe)
                }
            }
        }

        composable<Route.Recipe.Detail> { backStackEntry ->
            val viewModel = hiltViewModel<RecipeDetailViewModel>()
            val recipeId by viewModel.recipeId.collectAsStateWithLifecycle()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            val snackbarHostState = LocalSnackbarHostState.current

            RecipeDetailRoute(
                state = state,
                navigateUp = {
                    when (state) {
                        RecipeDetailUiState.Loading -> Unit
                        RecipeDetailUiState.RecipeNotFound -> Unit
                        RecipeDetailUiState.FailToLoad -> Unit

                        is RecipeDetailUiState.Success -> navController.navigateUp()
                    }
                },
                onEditStart = {
                    navController.navigateToRecipeEdit(recipeId = recipeId)
                },
                onRemoveRecipe = {
                    viewModel.removeRecipe()
                },
                onAddRecord = { newRecord ->
                    viewModel.addRecord(newRecord)
                }
            )

            LaunchedEffect(state) {
                if (state is RecipeDetailUiState.RecipeNotFound) {
                    navController.navigateUp()
                    snackbarHostState.showMessage(R.string.error_recipe_not_found)
                } else if (state is RecipeDetailUiState.FailToLoad) {
                    navController.navigateUp()
                    snackbarHostState.showMessage(R.string.error_fail_to_load_recipe)
                }
            }
        }

        composable<Route.Recipe.Edit> {
            val viewModel = hiltViewModel<RecipeEditViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val snackbarHostState = LocalSnackbarHostState.current

            RecipeEditRoute(
                state = uiState,
                navigateUp = {
                    navController.navigateUp()
                },
                onCompleteEdit = { editUiModel ->
                    viewModel.completeEdit(editUiModel)
                }
            )

            LaunchedEffect(uiState) {
                if ((uiState as? RecipeEditUiState.Success)?.isFinishEdit == true) {
                    navController.navigateUp()
                } else if (uiState is RecipeEditUiState.FailToLoad) {
                    navController.navigateUp()
                    snackbarHostState.showMessage(R.string.error_fail_to_load_recipe)
                }
            }
        }
    }
}