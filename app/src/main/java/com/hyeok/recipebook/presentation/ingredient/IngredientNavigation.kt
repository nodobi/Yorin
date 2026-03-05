package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiState
import com.hyeok.recipebook.presentation.ingredient.ui.IngredientDetailSheet
import com.hyeok.recipebook.presentation.ingredient.ui.IngredientEditSheet
import com.hyeok.recipebook.presentation.navigation.Route


fun NavController.navigateToIngredient(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Ingredients, navOptions)
}

fun NavController.navigateToIngredientDetail(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Detail, navOptions)
}

fun NavController.navigateToIngredientEdit(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Edit, navOptions)
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
            val viewModel = hiltViewModel<IngredientViewModel>(backstackEntry)

            IngredientRoute(
                onClickAddIngredient = {
                    navController.navigateToIngredientEdit()
                },
                onClickIngredient = {
                    navController.navigateToIngredientDetail()
                }
            )
        }

        bottomSheet<Route.Ingredient.Detail> { backStackEntry ->
            val backstackEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Route.Ingredient>()
            }
            val viewModel = hiltViewModel<IngredientViewModel>(backstackEntry)

            IngredientDetailSheet(
                ingredientUiState = IngredientUiState.mockState(),
                onDismiss = {
                    navController.popBackStack()
                },
                onClickRecipe = {},
                onEditIngredient = {
                    navController.navigateToIngredientEdit(
                        navOptions {
                            popUpTo(Route.Ingredient.Ingredients) { inclusive = false }
                        }
                    )
                },
                onDeleteIngredient = {}
            )
        }

        bottomSheet<Route.Ingredient.Edit> { backStackEntry ->
            val backstackEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Route.Ingredient>()
            }
            val viewModel = hiltViewModel<IngredientViewModel>(backstackEntry)

            IngredientEditSheet(
                ingredientUiState = null,
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
    }
}