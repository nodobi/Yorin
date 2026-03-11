package com.hyeok.recipebook.presentation.ingredient

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.state.IngredientEditUiState
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import com.hyeok.recipebook.presentation.util.ext.toEpochMilliseconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import javax.inject.Inject

@HiltViewModel
class IngredientEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val ingredientId = savedStateHandle.toRoute<Route.Ingredient.Detail>().ingredientId
    private val ingredient = flow {
//        emit(repository.getIngredient(ingredientId))
        emit(IngredientUiModel.fake())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<IngredientEditUiState> = ingredient
        .mapLatest { model ->
            val today = DateTimeUtil.currentLocalDate(TimeZone.UTC)

            IngredientEditUiState(
                ingredient = model,
                currentUtcMills = today.toEpochMilliseconds(),
                purchaseUtcMills = model.purchaseDate.toEpochMilliseconds(),
                expirationUtcMills = model.expirationDate.toEpochMilliseconds(),
            )

            IngredientEditUiState.empty()
        }.stateIn(viewModelScope, SharingStarted.Lazily, IngredientEditUiState.empty())
}