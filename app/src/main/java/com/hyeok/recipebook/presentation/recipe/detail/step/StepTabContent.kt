package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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

@Composable
fun StepTabContent(
    recipeSteps: List<RecipeStepUiModel>,
    editedRecipeSteps: SnapshotStateList<RecipeStepEditState>,
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
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

        if (isEditing) {
            editedRecipeSteps.forEach { editState ->
                EditingRecipeStepCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    editState = editState,
                    onRemoveStep = { id ->

                    }
                )
            }

            YorinCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        editedRecipeSteps.add(
                            RecipeStepEditState(
                                id = -1,
                                initialOrder = editedRecipeSteps.size,
                                initialDescription = ""
                            )
                        )
                    },
                stroke = BorderStroke(
                    width = 1.dp,
                    color = YorinTheme.colors.black5
                ),
                backgroundColor = YorinTheme.colors.black7
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                        contentDescription = null
                    )
                }
            }
        } else {
            recipeSteps.forEach { step ->
                RecipeStepCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    recipeStepUiModel = step
                )
            }
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
        recipeSteps = listOf(
            RecipeStepUiModel(id = 1, order = 1, description = "첫 번째 순서"),
            RecipeStepUiModel(id = 2, order = 2, description = "두 번째 순서"),
            RecipeStepUiModel(id = 3, order = 3, description = "세 번째 순서")

        ),
        editedRecipeSteps = remember { mutableStateListOf(
            RecipeStepEditState(id = 1, initialOrder = 1, initialDescription = "첫 번째 순서"),
            RecipeStepEditState(id = 2, initialOrder = 2, initialDescription = "두 번째 순서"),
            RecipeStepEditState(id = 3, initialOrder = 3, initialDescription = "세 번째 순서")
        ) },
        isEditing = false
    )
}

@BackgroundPreview
@Composable
fun EditingStepTabContentPreview() {
    StepTabContent(
        recipeSteps = listOf(
            RecipeStepUiModel(id = 1, order = 1, description = "첫 번째 순서"),
            RecipeStepUiModel(id = 2, order = 2, description = "두 번째 순서"),
            RecipeStepUiModel(id = 3, order = 3, description = "세 번째 순서")

        ),
        editedRecipeSteps = remember { mutableStateListOf(
            RecipeStepEditState(id = 1, initialOrder = 1, initialDescription = "첫 번째 순서"),
            RecipeStepEditState(id = 2, initialOrder = 2, initialDescription = "두 번째 순서"),
            RecipeStepEditState(id = 3, initialOrder = 3, initialDescription = "세 번째 순서")
        ) },
        isEditing = true
    )
}
