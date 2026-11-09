package com.hyeok.recipebook.presentation.recipe

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    navController: NavHostController,
    appScope: CoroutineScope,
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
                if (state is RecipeDetailUiState.RecipeNotFound) navController.navigateUp()
                else if (state is RecipeDetailUiState.FailToLoad) navController.navigateUp()
            }
        }

        composable<Route.Recipe.Edit> {
            val viewModel = hiltViewModel<RecipeEditViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val snackbarHostState = LocalSnackbarHostState.current
            val context = LocalContext.current

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
                    appScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.error_fail_to_load_recipe))
                    }
                }
            }
        }
    }
}