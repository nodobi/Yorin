package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyeok.recipebook.presentation.navigation.Route


fun NavController.navigateToIngredient(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Ingredients, navOptions)
}

fun NavController.navigateToIngredientDetail(navOptions: NavOptions? = null) {
    navigate(Route.Ingredient.Detail, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.ingredientScreen(
    navController: NavHostController
) {

    navigation<Route.Ingredient>(
        startDestination = Route.Ingredient.Ingredients
    ) {
        composable<Route.Ingredient.Ingredients> {
            IngredientRoute(
                onClickAddIngredient = {},
                onClickEditIngredient = {
                    navController.navigateToIngredientDetail()
                }
            )
        }

        bottomSheet<Route.Ingredient.Detail> {

        }
    }
}