package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.hyeok.recipebook.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId

    private val _uiState: MutableStateFlow<RecipeDetailUiState> = MutableStateFlow<RecipeDetailUiState>(
        RecipeDetailUiState(
            detail = RecipeUiModel(
                id = -1,
                name = "테스트",
                registerDate = LocalDate(2026, 8, 17),
                cookingTime = 30,
                photoUrl = "",
                ingredients = listOf(),
                steps = listOf(),
                cookingRecords = listOf(),
                changeHistory = listOf(),
            )
        )
    )
    val uiState = _uiState.asStateFlow()

}