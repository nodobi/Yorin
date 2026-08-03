package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun StepTabContent(
    modifier: Modifier = Modifier,
    recipeSteps: List<RecipeStepUiModel> = listOf(
        RecipeStepUiModel(id = 1, order = 1, description = "첫 번째 순서"),
        RecipeStepUiModel(id = 2, order = 2, description = "두 번째 순서"),
        RecipeStepUiModel(id = 3, order = 3, description = "세 번째 순서")
    ),
    isEditing: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinText(
            text = stringResource(R.string.recipe_detail_ingredient_title),
            style = YorinTheme.typography.body1
        )

        recipeSteps.forEach { step ->
            RecipeStepCard(
                modifier = Modifier
                    .fillMaxWidth(),
                recipeStepUiModel = step
            )
        }

    }
}

@Stable
class RecipeStepEditState(
    val id: Int,
    initialOrder: Int,
    initialDescription: String
) {
    val order = TextFieldState(initialOrder.toString())
    val description = TextFieldState(initialDescription)
}

@BackgroundPreview
@Composable
fun StepTabContentPreview() {
    StepTabContent()
}
