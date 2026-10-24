package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.model.RecipeUiModel

@Composable
fun StepTabContent(
    recipe: RecipeUiModel,
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

        recipe.steps.forEach { step ->
            RecipeStepCard(
                modifier = Modifier
                    .fillMaxWidth(),
                recipeStepUiModel = step
            )
        }
    }
}

@Composable
private fun RecipeStepCard(
    modifier: Modifier = Modifier,
    recipeStepUiModel: RecipeStepUiModel
) {
    YorinCard(
        modifier = modifier
            .fillMaxWidth(),
        stroke = BorderStroke(1.dp, YorinTheme.colors.black5)
    ) {
        Row(
            modifier = modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StepIcon(
                modifier = Modifier.size(32.dp),
                order = recipeStepUiModel.order
            )

            YorinText(
                modifier = Modifier.fillMaxWidth(),
                text = recipeStepUiModel.description,
                style = YorinTheme.typography.body2,
            )
        }
    }
}

@Composable
private fun StepIcon(
    modifier: Modifier,
    order: Int
) {
    Box(
        modifier = modifier
            .background(
                color = YorinTheme.colors.black6,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        YorinText(
            text = "$order",
            style = YorinTheme.typography.body4,
            color = YorinTheme.colors.black3
        )
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
private fun StepTabContentPreview() {
    StepTabContent(
        recipe = RecipeUiModel.fake()
    )
}

@Preview
@Composable
private fun RecipeStepCardPreview() {
    YorinTheme {
        RecipeStepCard(
            modifier = Modifier.wrapContentSize(),
            recipeStepUiModel = RecipeStepUiModel(1, 1, "엄청나게 심오하고 특별한 조리법에 대한 글이라 아무래도 길어질 수 밖에 없다 이거지")
        )
    }
}