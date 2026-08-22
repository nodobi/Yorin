package com.hyeok.recipebook.presentation.recipe.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchedQuery: TextFieldState = TextFieldState()

    private val _state: MutableStateFlow<RecipesUiState> = MutableStateFlow(RecipesUiState())
    val recipesUiState: StateFlow<RecipesUiState> = _state.asStateFlow()


}