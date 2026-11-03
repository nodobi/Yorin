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

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _recipe = _recipeId.flatMapLatest { recipeId ->
        if (recipeId == null) {
            return@flatMapLatest flowOf(RecipeEditUiModel.empty())
        }
        recipeRepository.getRecipeDetailsById(recipeId)
            .map { result ->
                result.fold(
                    onSuccess = { recipeUiModel ->
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
                        RecipeEditUiModel.empty()
                    }
                )
            }
    }

    private val _isFinishEdit = savedStateHandle.getStateFlow(
        key = KEY_FINISH_EDIT,
        initialValue = false
    )

    val uiState = combine(_isFinishEdit, _recipe) { isFinishEdit, recipeEditUiModel ->
        RecipeEditUiState.Success(
            name = recipeEditUiModel.name,
            cookingTime = recipeEditUiModel.cookingTime.let { if (it == 0) null else it },
            photoUrl = recipeEditUiModel.photoUrl,
            ingredients = recipeEditUiModel.ingredients,
            steps = recipeEditUiModel.steps,
            cookingRecords = recipeEditUiModel.cookingRecords,
            isFinishEdit = isFinishEdit
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeEditUiState.Loading)

    fun completeEdit(editUiModel: RecipeEditUiModel) {
        viewModelScope.launch {
            val recipeId = _recipeId.value

            if (recipeId == null) {
                // RecipeId 가 없는 경우는 신규 등록
                recipeRepository.addRecipe(editUiModel)
            } else {
                recipeRepository.updateRecipe(
                    recipeId = recipeId,
                    recipe = editUiModel
                )
            }.onFailure { reason ->
                Timber.d("Fail to edit recipe, id: $recipeId, reason: $reason")
            }.onSuccess {
                savedStateHandle[KEY_FINISH_EDIT] = true
            }
        }
    }

    companion object {
        private const val KEY_FINISH_EDIT = "key_finish_edit"
    }
}