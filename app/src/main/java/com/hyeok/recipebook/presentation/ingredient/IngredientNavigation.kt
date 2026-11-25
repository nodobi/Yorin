package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.hyeok.recipebook.presentation.ingredient.detail.IngredientDetailSheet
import com.hyeok.recipebook.presentation.ingredient.detail.IngredientDetailViewModel
import com.hyeok.recipebook.presentation.ingredient.edit.IngredientEditSheet
import com.hyeok.recipebook.presentation.ingredient.edit.IngredientEditViewModel
import com.hyeok.recipebook.presentation.ingredient.list.IngredientRoute
import com.hyeok.recipebook.presentation.ingredient.list.IngredientsViewModel
import com.hyeok.recipebook.presentation.navigation.BottomSheetSceneStrategy
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.util.ext.hideKeyboardOnTapOutside
import kotlinx.serialization.Serializable

@Serializable
data object IngredientListNavKey : NavKey

@Serializable
data class IngredientDetailNavKey(val ingredientId: Long) : NavKey

@Serializable
data class IngredientEditNavKey(val ingredientId: Long?) : NavKey

fun Navigator.navigateToIngredient() {
    navigate(IngredientListNavKey)
}

fun Navigator.navigateToIngredientDetail(ingredientId: Long) {
    navigate(
        IngredientDetailNavKey(
            ingredientId = ingredientId
        )
    )
}

fun Navigator.navigateToIngredientEdit(ingredientId: Long?) {
    navigate(
        IngredientEditNavKey(
            ingredientId = ingredientId
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun EntryProviderScope<NavKey>.ingredientSection(
    navigator: Navigator
) {
    entry<IngredientListNavKey> {
        val viewModel = hiltViewModel<IngredientsViewModel>()
        val ingredientUiState by viewModel.ingredientsUiState.collectAsStateWithLifecycle()

        IngredientRoute(
            ingredientsUiState = ingredientUiState,
            searchQueryState = viewModel.searchQueryState,
            onClickAddIngredient = {
                navigator.navigateToIngredientEdit(ingredientId = null)
            },
            onClickIngredient = { model ->
                navigator.navigateToIngredientDetail(ingredientId = model.id)
            }
        )
    }

    entry<IngredientDetailNavKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet()
    ) { key ->
        val viewModel = hiltViewModel<IngredientDetailViewModel, IngredientDetailViewModel.Factory>(
            creationCallback = { factory -> factory.create(key) }
        )
        val ingredientDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()

        IngredientDetailSheet(
            modifier = Modifier
                .hideKeyboardOnTapOutside(),
            ingredientDetailUiState = ingredientDetailUiState,
            onDismiss = {
                navigator.goBack()
            },
            onClickRecipe = {},
            onEditIngredient = {
                navigator.goBack()
            },
            onDeleteIngredient = {
                viewModel.removeIngredient(ingredientDetailUiState.ingredient.id)
            }
        )
    }

    entry<IngredientEditNavKey>(
        metadata = BottomSheetSceneStrategy.bottomSheet()
    ) { key ->
        val viewModel = hiltViewModel<IngredientEditViewModel, IngredientEditViewModel.Factory>(
            creationCallback = { factory -> factory.create(key) }
        )
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        IngredientEditSheet(
            modifier = Modifier
                .hideKeyboardOnTapOutside(),
            ingredientEditUiState = uiState,
            sheetState = sheetState,
            onDismiss = {
                navigator.goBack()
            },
            onComplete = { ingredientUiModel ->
                navigator.goBack()
                viewModel.addIngredient(ingredientUiModel)
            }
        )
    }
}
