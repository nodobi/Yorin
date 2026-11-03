package com.hyeok.recipebook.presentation.recipe.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.AddItemCard
import com.hyeok.recipebook.presentation.recipe.component.formfield.BasicFormField
import com.hyeok.recipebook.presentation.recipe.component.formfield.FormFieldState
import com.hyeok.recipebook.presentation.recipe.detail.step.RecipeStepUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeStepFormState

@Composable
fun StepTabEditContent(
    state: RecipeStepFormState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            YorinText(
                text = stringResource(R.string.recipe_detail_step_title),
                style = YorinTheme.typography.body1
            )
        }

        items(
            items = state.items,
            key = { it.id }
        ) { formState ->
            RecipeStepEditCard(
                modifier = Modifier
                    .fillMaxWidth(),
                description = formState.description,
                isShowRemoveIcon = state.isRemovable,
                onRemoveStep = {
                    state.remove(formState.id)
                }
            )
        }

        item {
            AddItemCard(
                modifier = Modifier,
                onClick = {
                    state.add()
                }
            )
        }
    }
}

@Composable
private fun RecipeStepEditCard(
    description: FormFieldState,
    onRemoveStep: () -> Unit,
    modifier: Modifier = Modifier,
    isShowRemoveIcon: Boolean = true
) {
    YorinCard(
        modifier = modifier
            .padding(12.dp),
        stroke = BorderStroke(1.dp, YorinTheme.colors.black5)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicFormField(
                modifier = Modifier
                    .weight(1f),
                state = description,
                lineLimits = TextFieldLineLimits.MultiLine()
            )

            if(isShowRemoveIcon) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onRemoveStep()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close_circle),
                    contentDescription = null,
                    tint = YorinTheme.colors.black3
                )
            }
        }
    }
}

@BackgroundPreview
@Composable
private fun StepTabEditContentPreview() {
    YorinTheme {
        StepTabEditContent(
            state = RecipeStepFormState(
                RecipeStepUiModel.fakes()
            )
        )
    }
}