package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = MutableStateFlow(savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId)
    val recipeId = _recipeId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = _recipeId.flatMapLatest { recipeId ->
        recipeRepository.getRecipeDetailsById(recipeId)
    }.map { result ->
        result.fold(
            onSuccess = {recipe ->
                if(recipe == null) return@fold RecipeDetailUiState.RecipeNotFound
                RecipeDetailUiState.Success(recipe = recipe)
            },
            onFailure = {
                Timber.d("Fail to load recipe. exception: $it")
                RecipeDetailUiState.FailToLoad
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeDetailUiState.Loading)

    fun removeRecipe() {
        viewModelScope.launch {
            recipeRepository.removeRecipe(_recipeId.value)
                .onFailure {
                    Timber.d("Fail to remove recipe. exception: $it")
                }
        }
    }

    fun addRecord(newRecord: RecipeRecordUiModel) {
        viewModelScope.launch {
            recipeRepository.addRecipeRecord(_recipeId.value, newRecord)
                .onFailure {
                    Timber.d("Fail to save recipe. exception: $it")
                }
        }
    }
}