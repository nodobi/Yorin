package com.hyeok.recipebook.presentation.ingredient.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.presentation.ingredient.IngredientEditNavKey
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.ingredient.model.toUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = IngredientEditViewModel.Factory::class)
class IngredientEditViewModel @AssistedInject constructor(
    private val ingredientRepository: IngredientRepository,
    @Assisted navKey: IngredientEditNavKey
) : ViewModel() {
    // 새로운 재료를 추가할 때 null
    private val ingredientId = navKey.ingredientId

    // TODO:: #89 레시피 수정 화면을 참고하여 UiState 부터 개선하기
    private val ingredient = flow {
        val id = ingredientId

        if (id == null) {
            emit(IngredientUiModel.empty())
        } else {
            emitAll(
                ingredientRepository.getIngredient(id).map {
                    it?.toUiModel() ?: IngredientUiModel.empty()
                }
            )
        }
    }

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
                    // TODO::#89 화면 전환 이벤트 컴포저블로 전달
                }
                .onFailure { }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: IngredientEditNavKey): IngredientEditViewModel
    }
}
