package com.hyeok.recipebook.presentation.recipe.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.edit.model.toEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeEditViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = MutableStateFlow(savedStateHandle.toRoute<Route.Recipe.Edit>().recipeId)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val recipe: Flow<RecipeUiModel?> = _recipeId.flatMapLatest { recipeId ->
        recipeId?.let {
            recipeRepository.getRecipeDetailsById(it)
                .map { result ->
                    result.onFailure { reason ->
                        Timber.d("Fail to load recipe, id: $recipeId, reason: $reason")
                    }.getOrNull()
                }
        } ?: flowOf(null)
    }

    val uiState = recipe.map { recipeUiModel ->
        if (recipeUiModel == null) RecipeEditUiState.Loading
        else RecipeEditUiState.Success(
            name = recipeUiModel.name,
            cookingTime = recipeUiModel.cookingTime.let { if (it == 0) null else it },
            photoUrl = recipeUiModel.photoUrl,
            ingredients = recipeUiModel.ingredients.map { it.toEditUiModel() },
            steps = recipeUiModel.steps,
            cookingRecords = recipeUiModel.cookingRecords,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeEditUiState.Loading)
}