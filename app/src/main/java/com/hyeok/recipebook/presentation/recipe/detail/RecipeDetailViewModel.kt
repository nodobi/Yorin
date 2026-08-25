package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hyeok.recipebook.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId

    private val _uiState: MutableStateFlow<RecipeDetailUiState> = MutableStateFlow(
        RecipeDetailUiState.fake()
    )
    val uiState = _uiState.asStateFlow()

    fun updateIsEditing(isEditing: Boolean) {
        _uiState.update {
            it.copy(isEditing = isEditing)
        }
    }
}