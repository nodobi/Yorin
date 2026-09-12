package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.AddItemCard
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailUiState
import com.hyeok.recipebook.presentation.recipe.detail.RecipeUiModel

@Composable
fun StepTabContent(
    state: RecipeDetailUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.recipe_detail_step_title),
            style = YorinTheme.typography.body1
        )

        when (state) {
            is RecipeDetailUiState.Success -> {
                state.recipe.steps.forEach { step ->
                    RecipeStepCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        recipeStepUiModel = step
                    )
                }
            }

            is RecipeDetailUiState.Edit -> {
                state.editState.steps.forEach { editState ->
                    EditingRecipeStepCard(
                        modifier = Modifier
                            .fillMaxWidth(),
                        editState = editState,
                        onRemoveStep = { id ->

                        }
                    )
                }

                AddItemCard(
                    modifier = Modifier,
                    onClick = {
                        state.editState.steps.add(
                            RecipeStepEditState(
                                id = -1,
                                initialOrder = state.editState.steps.size,
                                initialDescription = ""
                            )
                        )
                    }
                )
            }

            RecipeDetailUiState.Loading -> {}
        }
    }
}

@Stable
class RecipeStepEditState(
    val id: Long,
    initialOrder: Int,
    initialDescription: String
) {
    val order = TextFieldState(initialOrder.toString())
    val description = TextFieldState(initialDescription)
}

@BackgroundPreview
@Composable
fun StepTabContentPreview() {
    StepTabContent(
        state = RecipeDetailUiState.Success(
            recipe = RecipeUiModel.fake()
        )
    )
}

@BackgroundPreview
@Composable
fun EditingStepTabContentPreview() {
    StepTabContent(
        state = RecipeDetailUiState.Success(
            recipe = RecipeUiModel.fake()
        )
    )
}
