package com.hyeok.recipebook.presentation.ingredient.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.model.toUiModel
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class IngredientsViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {
    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    private val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(200L.milliseconds)

    private val _ingredients: Flow<List<IngredientUiModel>> = ingredientRepository.getIngredients()
        .map { models ->
            models.map { it.toUiModel() }
        }
        .catch { emit(emptyList()) }

    val ingredientsUiState: StateFlow<IngredientsUiState> = combine(
        _ingredients, searchQueryFlow
    ) { ingredients, searchQuery ->
        val currentEpochDays = DateTimeUtil.currentLocalDate().toEpochDays()

        IngredientsUiState(
            ingredients = ingredients.filter { it.name.contains(searchQuery) },
            expiredCount = ingredients.count {
                if (it.expirationDate == null) return@count false

                (it.expirationDate.toEpochDays() - currentEpochDays) < 0
            },
            remainExpirationDays = ingredients.map {
                if (it.expirationDate == null) return@map null

                (it.expirationDate.toEpochDays() - currentEpochDays).let { remainExpirationDay ->
                    if (remainExpirationDay < 0) -1 else remainExpirationDay.toInt()
                }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, IngredientsUiState.empty())
}