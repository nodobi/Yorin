package com.hyeok.recipebook.presentation.recipe.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.model.toEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecipeEditViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _recipeId = MutableStateFlow(savedStateHandle.toRoute<Route.Recipe.Edit>().recipeId)
    private val _isSaving = MutableStateFlow(false)
    private val _isFinishEdit = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _recipe = _recipeId.flatMapLatest { recipeId ->
        if (recipeId == null) {
            return@flatMapLatest flowOf(RecipeEditUiModel.empty())
        }
        recipeRepository.getRecipeDetailsById(recipeId)
            .map { result ->
                result.fold(
                    onSuccess = { recipeUiModel ->
                        if (recipeUiModel == null) return@fold null
                        RecipeEditUiModel(
                            name = recipeUiModel.name,
                            cookingTime = recipeUiModel.cookingTime,
                            photoUrl = recipeUiModel.photoUrl,
                            ingredients = recipeUiModel.ingredients.map { it.toEditUiModel() },
                            steps = recipeUiModel.steps,
                            cookingRecords = recipeUiModel.cookingRecords,
                        )
                    },
                    onFailure = { reason ->
                        Timber.d("Fail to load recipe, id: $recipeId, reason: $reason")
                        null
                    }
                )
            }
    }

    val uiState = combine(_isFinishEdit, _recipe, _recipeId) { isFinishEdit, recipeEditUiModel, recipeId ->
        if (recipeEditUiModel == null) {
            RecipeEditUiState.FailToLoad
        } else {
            RecipeEditUiState.Success(
                name = recipeEditUiModel.name,
                cookingTime = recipeEditUiModel.cookingTime.let { if (it == 0) null else it },
                photoUrl = recipeEditUiModel.photoUrl,
                ingredients = recipeEditUiModel.ingredients,
                steps = recipeEditUiModel.steps,
                cookingRecords = recipeEditUiModel.cookingRecords,
                isFinishEdit = isFinishEdit,
                updatedRecipeId = recipeId
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeEditUiState.Loading)

    fun completeEdit(editUiModel: RecipeEditUiModel) {
        if (_isSaving.value || _isFinishEdit.value) return
        _isSaving.value = true

        viewModelScope.launch {
            val recipeId = _recipeId.value

            if (recipeId == null) {
                recipeRepository.addRecipe(editUiModel)
                    .onSuccess {
                        _recipeId.value = it
                    }
            } else {
                recipeRepository.updateRecipe(
                    recipeId = recipeId,
                    recipe = editUiModel
                )
            }.onFailure { reason ->
                _isSaving.value = false
                Timber.d("Fail to edit recipe, id: $recipeId, reason: $reason")
            }.onSuccess {
                _isSaving.value = false
                _isFinishEdit.value = true
            }
        }
    }
}