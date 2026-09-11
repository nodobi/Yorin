package com.hyeok.recipebook.presentation.recipe.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(200L)

    private val recipesFlow = recipeRepository.getRecipesSummaries()
        .map {
            it.getOrElse {
                emptyList()
            }
        }

    private val _recipeFilter = MutableStateFlow(RecipeSearchFilter.BY_RECIPE)

    val state: StateFlow<RecipesUiState> = combine(
        recipesFlow,
        searchQueryFlow,
        _recipeFilter,
    ) { recipes, query, recipeFilter ->
        val queriedRecipes = recipes.filter { recipe ->
            when (recipeFilter) {
                RecipeSearchFilter.BY_INGREDIENT -> {
                    recipe.ingredients.find { it.contains(query) } != null
                }

                else -> {
                    recipe.name.contains(query)
                }
            }
        }
        RecipesUiState(
            selectedSearchFilter = recipeFilter,
            recipes = queriedRecipes
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, RecipesUiState(selectedSearchFilter = _recipeFilter.value))

    fun updateFilter(newFilter: RecipeSearchFilter) {
        _recipeFilter.update { newFilter }
    }
}