package com.hyeok.recipebook.presentation.ingredient.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.IngredientRepository
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ingredientId = savedStateHandle.toRoute<Route.Ingredient.Detail>().ingredientId

    private val ingredient = ingredientRepository.getIngredient(ingredientId)
        .mapNotNull {
            it.fold(
                onSuccess = { ingredientUiModel ->
                    ingredientUiModel ?: return@mapNotNull null
                },
                onFailure = {
                    // 에러 핸들링
                    return@mapNotNull null
                }
            )
        }

    private val recipes = flow {
        emit(recipeRepository.getRecipesByIngredient(ingredientId).map { it.map { it.name } }.getOrDefault(emptyList()))
    }

    val uiState = combine(
        ingredient,
        recipes
    ) { ingredient, recipes ->
        val today = DateTimeUtil.currentLocalDate(TimeZone.currentSystemDefault())

        val remainExpirationDays = ingredient.expirationDate?.let {
            val day = today.until(it, DateTimeUnit.DAY).toInt()

            if(day < 0) day * -1 else day
        }

        val expirationProgress = ingredient.expirationDate?.let {
            val totalDay = it.until(ingredient.purchaseDate, DateTimeUnit.DAY).toInt().let { num ->
                if(num < 0) num * -1 else num
            }

            if(totalDay == remainExpirationDays) {
                1f
            } else if(remainExpirationDays == null) {
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
                    // TODO:: 화면 전환
                }
                .onFailure {  }
        }
    }
}