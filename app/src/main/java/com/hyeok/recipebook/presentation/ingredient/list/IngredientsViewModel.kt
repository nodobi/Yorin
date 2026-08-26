package com.hyeok.recipebook.presentation.ingredient.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {
    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(200L)

    private val _ingredients: Flow<List<IngredientUiModel>> = ingredientRepository.getIngredients()
        .map {
            it.getOrDefault(emptyList())
        }

    val ingredientsUiState: StateFlow<IngredientsUiState> = combine(
        _ingredients, searchQueryFlow
    ) { ingredients, searchQuery ->
        val currentEpochDays = DateTimeUtil.currentLocalDate(timeZone = TimeZone.currentSystemDefault()).toEpochDays()

        IngredientsUiState(
            ingredients = ingredients.filter { it.name.contains(searchQuery) },
            expiredCount = ingredients.count {
                (it.expirationDate.toEpochDays() - currentEpochDays) < 0
            },
            remainExpirationDays = ingredients.map {
                (it.expirationDate.toEpochDays() - currentEpochDays).let { remainExpirationDay ->
                    if (remainExpirationDay < 0) -1 else remainExpirationDay.toInt()
                }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, IngredientsUiState.empty())
}