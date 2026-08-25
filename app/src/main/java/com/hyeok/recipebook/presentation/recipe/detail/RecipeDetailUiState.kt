package com.hyeok.recipebook.presentation.recipe.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientEditState
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepEditState
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel

data class RecipeDetailUiState(
    val isEditing: Boolean = false,
    val recipe: RecipeUiModel
) {
    companion object {
        fun fake(): RecipeDetailUiState = RecipeDetailUiState(
            isEditing = false,
            recipe = RecipeUiModel.fake()
        )
    }
}

class RecipeDetailEditState(
    initialName: String,
    initialCookingTime: Int,
    initialPhotoUrl: String? = null,
    initialIngredients: List<RecipeIngredientUiModel>,
    initialStep: List<RecipeStepUiModel>,
    initialRecord: List<RecipeRecordUiModel>
) {
    val name: TextFieldState = TextFieldState(initialName)
    val cookingTime: TextFieldState = TextFieldState("$initialCookingTime")
    var photoUrl by mutableStateOf(initialPhotoUrl)
    val ingredients = mutableStateListOf<RecipeIngredientEditState>().apply {
        addAll(initialIngredients.map { recipeIngredient ->
            RecipeIngredientEditState(
                id = recipeIngredient.id,
                initialName = recipeIngredient.name,
                initialQuantity = recipeIngredient.requireQuantity,
                initialUnit = recipeIngredient.unit
            )
        })
    }
    val steps = mutableStateListOf<RecipeStepEditState>().apply {
        addAll(initialStep.map {
            RecipeStepEditState(
                id = it.id,
                initialOrder = it.order,
                initialDescription = it.description
            )
        })
    }
    val record = mutableStateListOf<RecipeRecordUiModel>().apply {
        addAll(initialRecord)
    }
}