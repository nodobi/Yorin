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
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(200L.milliseconds)

    private val recipesFlow = recipeRepository.getRecipesSummaries()
        .map {
            it.getOrElse {
                Timber.d("Fail to load recipeSummaries. exception: $it")

                null
            }
        }

    private val _recipeFilter = MutableStateFlow(RecipeSearchFilter.BY_RECIPE)

    val state: StateFlow<RecipesUiState> = combine(
        recipesFlow,
        searchQueryFlow,
        _recipeFilter,
    ) { recipes, query, recipeFilter ->
        val queriedRecipes = recipes?.filter { recipe ->
            when (recipeFilter) {
                RecipeSearchFilter.BY_INGREDIENT -> {
                    recipe.ingredients.find { it.contains(query) } != null
                }

                else -> {
                    recipe.name.contains(query)
                }
            }
        } ?: emptyList()

        RecipesUiState(
            selectedSearchFilter = recipeFilter,
            recipes = queriedRecipes,
            isFailToLoad = recipes == null
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, RecipesUiState(selectedSearchFilter = _recipeFilter.value))

    fun updateFilter(newFilter: RecipeSearchFilter) {
        _recipeFilter.update { newFilter }
    }
}