package com.hyeok.recipebook.presentation.ingredient.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import com.hyeok.recipebook.presentation.util.ext.toEpochMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import javax.inject.Inject

@HiltViewModel
class IngredientEditViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val ingredientId = savedStateHandle.toRoute<Route.Ingredient.Edit>().ingredientId

    private val ingredient = ingredientRepository.getIngredient(ingredientId ?: -1)
        .map {
            it.getOrNull()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<IngredientEditUiState> = ingredient
        .map { model ->
            IngredientEditUiState(
                ingredient = model,
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, IngredientEditUiState.empty())

    fun addIngredient(ingredient: IngredientUiModel) {
        viewModelScope.launch {
            ingredientRepository.addIngredient(ingredient)
                .onSuccess {
                    // TODO:: ingredient 갱신
                }
                .onFailure {  }
        }
    }
}