package com.hyeok.recipebook.presentation.ingredient.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.IngredientEditNavKey
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = IngredientEditViewModel.Factory::class)
class IngredientEditViewModel @AssistedInject constructor(
    private val ingredientRepository: IngredientRepository,
    @Assisted navKey: IngredientEditNavKey
) : ViewModel() {
    private val ingredientId = navKey.ingredientId

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
                .onFailure { }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: IngredientEditNavKey): IngredientEditViewModel
    }
}
