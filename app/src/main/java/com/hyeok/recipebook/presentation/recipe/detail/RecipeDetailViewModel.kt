package com.hyeok.recipebook.presentation.recipe.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.hyeok.recipebook.data.repository.RecipeRepository
import com.hyeok.recipebook.presentation.navigation.Route
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _recipeId = MutableStateFlow(savedStateHandle.toRoute<Route.Recipe.Detail>().recipeId)
    val recipeId = _recipeId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val recipeFlow: Flow<RecipeUiModel> = _recipeId.flatMapLatest { recipeId ->
        recipeRepository.getRecipeDetailsById(recipeId)
            .map { result ->
                result.getOrElse {
                    Timber.d("failed recipeRepository.getRecipeDetailsById| $it")
                    RecipeUiModel.empty()
                }
            }
    }

    val uiState = recipeFlow
        .map { recipe ->
            RecipeDetailUiState.Success(
                recipe = recipe
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RecipeDetailUiState.Loading)

    fun addRecord(newRecord: RecipeRecordUiModel) {
        viewModelScope.launch {
            recipeRepository.addRecipeRecord(recipeId.value, newRecord)
                .onSuccess {
                    // Recipe 상세 데이터를 Flow 로 받고 있기 때문에, 알아서 업데이트가 될 것이라 기대
                }
                .onFailure {
                    // TODO:: 실패 대응

                }
        }
    }
}