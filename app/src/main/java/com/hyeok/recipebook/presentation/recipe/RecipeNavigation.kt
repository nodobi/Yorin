package com.hyeok.recipebook.presentation.recipe

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.hyeok.recipebook.R
import com.hyeok.recipebook.presentation.LocalSnackbarHostState
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailRoute
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailUiState
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailViewModel
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditRoute
import com.hyeok.recipebook.presentation.recipe.edit.RecipeEditViewModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import com.hyeok.recipebook.presentation.recipe.list.RecipeRoute
import com.hyeok.recipebook.presentation.recipe.list.RecipesViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RecipeListNavKey : NavKey

@Serializable
data class RecipeDetailNavKey(val recipeId: Long) : NavKey

@Serializable
data class RecipeEditNavKey(val recipeId: Long?) : NavKey

fun Navigator.navigateToRecipe() {
    navigate(RecipeListNavKey)
}

fun Navigator.navigateToRecipeDetail(recipeId: Long) {
    navigate(
        RecipeDetailNavKey(
            recipeId
        )
    )
}

fun Navigator.navigateToRecipeEdit(recipeId: Long?) {
    navigate(
        RecipeEditNavKey(
            recipeId
        )
    )
}

fun EntryProviderScope<NavKey>.recipeSection(
    navigator: Navigator
) {
    entry<RecipeListNavKey> {
        val viewModel = hiltViewModel<RecipesViewModel>()
        val recipesUiState by viewModel.state.collectAsStateWithLifecycle()

        val snackbarHostState = LocalSnackbarHostState.current

        RecipeRoute(
            recipesUiState = recipesUiState,
            searchQueryState = viewModel.searchQueryState,
            onAddRecipe = {
                navigator.navigateToRecipeEdit(recipeId = null)
            },
            onClickRecipe = { recipeId ->
                navigator.navigateToRecipeDetail(recipeId = recipeId)
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

    entry<RecipeDetailNavKey> { key ->
        val viewModel = hiltViewModel<RecipeDetailViewModel, RecipeDetailViewModel.Factory>(
            creationCallback = { factory -> factory.create(key) }
        )
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

                    is RecipeDetailUiState.Success -> navigator.goBack()
                }
            },
            onEditStart = {
                navigator.navigateToRecipeEdit(recipeId = recipeId)
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
                navigator.goBack()
                snackbarHostState.showMessage(R.string.error_recipe_not_found)
            } else if (state is RecipeDetailUiState.FailToLoad) {
                navigator.goBack()
                snackbarHostState.showMessage(R.string.error_fail_to_load_recipe)
            }
        }
    }

    entry<RecipeEditNavKey> { key ->
        val viewModel = hiltViewModel<RecipeEditViewModel, RecipeEditViewModel.Factory>(
            creationCallback = { factory -> factory.create(key) }
        )
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val snackbarHostState = LocalSnackbarHostState.current

        RecipeEditRoute(
            state = uiState,
            navigateUp = {
                navigator.goBack()
            },
            onCompleteEdit = { editUiModel ->
                viewModel.completeEdit(editUiModel)
            }
        )

        LaunchedEffect(uiState) {
            when (uiState) {
                RecipeEditUiState.Loading -> Unit
                RecipeEditUiState.FailToLoad -> {
                    navigator.goBack()
                    snackbarHostState.showMessage(R.string.error_fail_to_load_recipe)
                }

                is RecipeEditUiState.Success -> {
                    val uiState = uiState as RecipeEditUiState.Success

                    if (uiState.isFinishEdit && uiState.updatedRecipeId != null) {
                        navigator.navigateToRecipeDetail(uiState.updatedRecipeId)
                    }
                }
            }
        }
    }
}
