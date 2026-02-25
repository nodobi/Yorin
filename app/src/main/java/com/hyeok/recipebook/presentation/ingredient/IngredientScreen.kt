package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun IngredientRoute(
    navigateUp: () -> Unit,
    viewModel: IngredientViewModel = hiltViewModel()
) {

    IngredientScreen(
        modifier = Modifier,
        navigateUp = navigateUp
    )
}

@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {

}