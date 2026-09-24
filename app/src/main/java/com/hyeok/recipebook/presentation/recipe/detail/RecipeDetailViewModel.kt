package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import com.hyeok.recipebook.presentation.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId = savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId

    private val recipeDetailFlow: Flow<RecipeUiModel> = (recipeId?.let {
        recipeRepository.getRecipeDetailsById(it)
            .map { result ->
                result.getOrElse {
                    Timber.d("failed recipeRepository.getRecipeDetailsById| $it")
                    RecipeUiModel.empty()
                }
            }
    } ?: flowOf(RecipeUiModel.empty()))

    private val recipeDetail: StateFlow<RecipeUiModel> = recipeDetailFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeUiModel.empty())

    private val _isEditing = MutableStateFlow(false)

    val uiState = recipeDetail
        .combine(_isEditing) { recipeDetail, isEditing ->
            if(isEditing) {
                RecipeDetailUiState.Edit(
                    initialRecipe = recipeDetail
                )
            } else {
                RecipeDetailUiState.Success(
                    recipe = recipeDetail
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeDetailUiState.Loading)

    fun updateIsEditing(isEditing: Boolean) {
        _isEditing.value = isEditing
    }

    fun updateRecipe(editState: RecipeDetailEditState) {
        val recipeUiState = RecipeUiModel(
            id = recipeDetail.value.id,
            name = editState.name.toString(),
            registerDate = DateTimeUtil.currentLocalDate(),
            cookingTime = editState.cookingTime.toString().toInt(),
            averageScore = 0f,
            photoUrl = editState.photoUrl,
            ingredients = editState.ingredients.map { ingredientEditState ->
                RecipeIngredientUiModel(
                    id = ingredientEditState.id,
                    name = ingredientEditState.name.toString(),
                    unit = ingredientEditState.unit,
                    requireQuantity = ingredientEditState.quantity.toString().toInt(),
                    stockQuantity = 0
                )
            },
            steps = editState.steps.map { stepEditState ->
                RecipeStepUiModel(
                    id = stepEditState.id,
                    order = stepEditState.order.toString().toInt(),
                    description = stepEditState.description.toString()
                )
            },
            cookingRecords = editState.record.toList(),
        )

        viewModelScope.launch {
            recipeRepository.updateRecipe(recipeUiState)
                .onSuccess {
                    // Recipe 상세 데이터를 Flow 로 받고 있기 때문에, 알아서 업데이트가 될 것이라 기대

                }
                .onFailure {
                    // TODO:: 실패 대응

                }
        }
    }

    fun addRecord(newRecord: RecipeRecordUiModel) {
        viewModelScope.launch {
            recipeRepository.addRecipeRecord(recipeDetail.value.id, newRecord)
                .onSuccess {
                    // Recipe 상세 데이터를 Flow 로 받고 있기 때문에, 알아서 업데이트가 될 것이라 기대
                }
                .onFailure {
                    // TODO:: 실패 대응

                }
        }
    }
}