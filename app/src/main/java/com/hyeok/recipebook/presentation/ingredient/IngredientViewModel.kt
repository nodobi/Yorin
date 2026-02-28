package com.hyeok.recipebook.presentation.ingredient

import androidx.lifecycle.ViewModel
import com.hyeok.recipebook.presentation.ingredient.model.IngredientUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(

) : ViewModel() {

    val tempIngredients = listOf(
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1)
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1)
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1)
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            description = "재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1)
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(1),
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(5),
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        ),
        IngredientUiState(
            name = "재료",
            expirationDate = LocalDate.now().plusDays(10),
            description = "긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명긴재료설명"
        )
    )

}