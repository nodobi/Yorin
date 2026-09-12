package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId = savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId

    private val recipeDetailFlow: Flow<RecipeUiModel> = recipeId?.let {
        recipeRepository.getRecipeDetailsById(it)
            .map { result ->
                result.getOrElse {
                    Timber.d("failed recipeRepository.getRecipeDetailsById| $it")
                    RecipeUiModel.empty()
                }
            }
    } ?: flowOf(RecipeUiModel.empty())

    private val _uiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun updateIsEditing(isEditing: Boolean) {

    }
}