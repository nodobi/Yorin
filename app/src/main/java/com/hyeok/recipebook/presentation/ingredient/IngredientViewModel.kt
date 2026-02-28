package com.hyeok.recipebook.presentation.ingredient

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(

) : ViewModel() {

    val searchQueryState: TextFieldState = TextFieldState()

    @OptIn(FlowPreview::class)
    val searchQueryFlow = snapshotFlow { searchQueryState.text }
        .debounce(500L)

    val tempIngredients = listOf(
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개",
            description = "재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            weight = 1,
            weightUnit = "개",
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(5),
            weight = 1,
            weightUnit = "개",
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(10),
            weight = 1,
            weightUnit = "개",
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        )
    )

}