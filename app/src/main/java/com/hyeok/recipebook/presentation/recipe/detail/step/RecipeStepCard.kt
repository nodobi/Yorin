package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun RecipeStepCard(
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

@Composable
fun EditingRecipeStepCard(
    editState: RecipeStepEditState,
    modifier: Modifier = Modifier,
    onRemoveStep: (Long) -> Unit = {}
) {
    YorinCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        stroke = BorderStroke(1.dp, YorinTheme.colors.black5)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            YorinTextField(
                modifier = Modifier
                    .weight(1f),
                state = editState.description,
                lineLimits = TextFieldLineLimits.MultiLine()
            )

            RemoveIcon(
                modifier = Modifier
                    .clickable {
                        onRemoveStep(editState.id)
                    }
            )
        }

    }
}

@Composable
private fun RemoveIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = ImageVector.vectorResource(R.drawable.ic_close_circle),
        contentDescription = null,
        tint = YorinTheme.colors.black3
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

@Preview
@Composable
private fun EditingRecipeStepCardPreview() {
    YorinTheme {
        EditingRecipeStepCard(
            editState = RecipeStepEditState(1, 1, "엄청나게 심오하고 특별한 조리법에 대한 글이라 아무래도 길어질 수 밖에 없다 이거지")
        )
    }
}