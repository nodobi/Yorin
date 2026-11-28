package com.hyeok.recipebook.presentation.ingredient.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.ingredient.IngredientDetailNavKey
import com.hyeok.recipebook.presentation.ingredient.model.toUiModel
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.until

@HiltViewModel(assistedFactory = IngredientDetailViewModel.Factory::class)
class IngredientDetailViewModel @AssistedInject constructor(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    @Assisted navKey: IngredientDetailNavKey
) : ViewModel() {

    private val ingredientId = navKey.ingredientId

    private val ingredient = ingredientRepository.getIngredient(ingredientId)
        .mapNotNull {
            it!!.toUiModel()
//            TODO::#89 유효하지 않은 Id 로 조회한 경우 뒤로가기?
        }

    private val recipes = flow {
        emit(recipeRepository.getRecipesByIngredient(ingredientId).map { it.map { it.name } }.getOrDefault(emptyList()))
    }

    val uiState = combine(
        ingredient,
        recipes
    ) { ingredient, recipes ->
        val today = DateTimeUtil.currentLocalDate()

        val remainExpirationDays = ingredient.expirationDate?.let {
            val day = today.until(it, DateTimeUnit.DAY).toInt()

            if (day < 0) day * -1 else day
        }

        val expirationProgress = ingredient.expirationDate?.let {
            val totalDay = it.until(ingredient.purchaseDate, DateTimeUnit.DAY).toInt().let { num ->
                if (num < 0) num * -1 else num
            }

            if (totalDay == remainExpirationDays) {
                1f
            } else if (remainExpirationDays == null) {
                0.5f
            } else {
                1f - remainExpirationDays.toFloat() / totalDay.toFloat()
            }
        } ?: 0.5f

        IngredientDetailUiState(
            ingredient = ingredient,
            remainExpirationDays = remainExpirationDays,
            expirationProgress = expirationProgress,
            relatedRecipes = recipes
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, IngredientDetailUiState.empty())

    fun removeIngredient(ingredientId: Long) {
        viewModelScope.launch {
            ingredientRepository.removeIngredient(ingredientId)
                .onSuccess {
                    // TODO::#89 화면 전환
                }
                .onFailure { }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: IngredientDetailNavKey): IngredientDetailViewModel
    }
}
