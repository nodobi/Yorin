package com.hyeok.recipebook.presentation.recipe.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeEditViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = MutableStateFlow(savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId)

    private val _uiState = MutableStateFlow<RecipeEditUiState>(RecipeEditUiState.Loading)
    val uiState = _uiState.asStateFlow()
}