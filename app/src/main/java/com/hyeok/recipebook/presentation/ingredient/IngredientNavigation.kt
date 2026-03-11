package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.hyeok.recipebook.presentation.ingredient.detail.IngredientDetailSheet
import com.hyeok.recipebook.presentation.ingredient.detail.IngredientDetailViewModel
import com.hyeok.recipebook.presentation.ingredient.edit.IngredientEditSheet
import com.hyeok.recipebook.presentation.ingredient.edit.IngredientEditViewModel
import com.hyeok.recipebook.presentation.ingredient.list.IngredientRoute
import com.hyeok.recipebook.presentation.ingredient.list.IngredientsViewModel
import com.hyeok.recipebook.presentation.navigation.Route


fun NavController.navigateToIngredient(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Ingredients, navOptions)
}

fun NavController.navigateToIngredientDetail(ingredientId: Int, navOptions: NavOptions? = null) {
    navigate(
        Route.Ingredient.Detail(
            ingredientId = ingredientId
        ), navOptions
    )
}

fun NavController.navigateToIngredientEdit(ingredientId: Int?, navOptions: NavOptions? = null) {
    navigate(
        Route.Ingredient.Edit(
            ingredientId = ingredientId
        ), navOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.ingredientScreen(
    navController: NavHostController
) {
    navigation<Route.Ingredient>(
        startDestination = Route.Ingredient.Ingredients
    ) {
        composable<Route.Ingredient.Ingredients> { backStackEntry ->
            val backstackEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Route.Ingredient>()
            }
            val viewModel = hiltViewModel<IngredientsViewModel>(backstackEntry)
            val ingredientUiState by viewModel.ingredientsUiState.collectAsStateWithLifecycle()

            IngredientRoute(
                ingredientsUiState = ingredientUiState,
                searchQueryState = viewModel.searchQueryState,
                onClickAddIngredient = {
                    navController.navigateToIngredientEdit(ingredientId = null)
                },
                onClickIngredient = { model ->
                    navController.navigateToIngredientDetail(ingredientId = model.id)
                }
            )
        }

        bottomSheet<Route.Ingredient.Detail> { backStackEntry ->
            val viewModel = hiltViewModel<IngredientDetailViewModel>()
            val ingredientDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()

            IngredientDetailSheet(
                ingredientDetailUiState = ingredientDetailUiState,
                onDismiss = {
                    navController.popBackStack()
                },
                onClickRecipe = {},
                onEditIngredient = {
                    navController.navigateToIngredientEdit(
                        ingredientId = ingredientDetailUiState.ingredient.id,
                        navOptions = navOptions {
                            popUpTo(Route.Ingredient.Ingredients) { inclusive = false }
                        }
                    )
                },
                onDeleteIngredient = {
                    viewModel.removeIngredient(ingredientDetailUiState.ingredient.id)
                }
            )
        }

        bottomSheet<Route.Ingredient.Edit> { backStackEntry ->
            val viewModel = hiltViewModel<IngredientEditViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            IngredientEditSheet(
                ingredientEditUiState = uiState,
                onDismiss = {
                    navController.popBackStack()
                },
                onComplete = { ingredientUiModel ->
                    navController.popBackStack()
                    // TODO:: 저장 로직 추가
                }
            )
        }
    }
}