package com.hyeok.recipebook.presentation.ingredient.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiModel
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ingredientId = savedStateHandle.toRoute<Route.Ingredient.Detail>().ingredientId

    private val ingredient = flow {
        emit(ingredientRepository.getIngredient(ingredientId).getOrDefault(IngredientUiModel.empty()))
    }

    private val recipes = flow {
        emit(recipeRepository.getRecipesByIngredient(ingredientId).map { it.map { it.name } }.getOrDefault(emptyList()))
    }

    val uiState = combine(
        ingredient,
        recipes
    ) { ingredient, recipes ->
        val today = DateTimeUtil.currentLocalDate(TimeZone.currentSystemDefault())
        val remainExpirationDays = (today.day - ingredient.expirationDate.day).let {
            if (it < 0) 0 else it
        }
        val totalDay = ingredient.expirationDate.day - ingredient.purchaseDate.day
        val expirationProgress = if (remainExpirationDays == totalDay) {
            1f
        } else {
            1f - remainExpirationDays.toFloat() / totalDay.toFloat()
        }

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
                    // TODO:: 화면 전환
                }
                .onFailure {  }
        }
    }
}