package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.hyeok.recipebook.presentation.ingredient.model.IngredientModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(

) : ViewModel() {

    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(500L)


    val tempIngredients = listOf(
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState(),
        IngredientModel.mockState()
    )

}