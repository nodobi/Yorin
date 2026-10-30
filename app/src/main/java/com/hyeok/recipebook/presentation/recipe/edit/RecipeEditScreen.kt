package com.hyeok.recipebook.presentation.recipe.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinNumberTextField
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.RecipeDetailLayout
import com.hyeok.recipebook.presentation.recipe.edit.model.RecipeEditUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeEditUiState
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeIngredientFormState
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeRecordState
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeStepFormState
import com.hyeok.recipebook.presentation.recipe.edit.state.rememberRecipeIngredientFormState
import com.hyeok.recipebook.presentation.recipe.edit.state.rememberRecipeRecordState
import com.hyeok.recipebook.presentation.recipe.edit.state.rememberRecipeStepFormState
import com.hyeok.recipebook.presentation.recipe.edit.state.toUiModel
import com.hyeok.recipebook.presentation.recipe.model.RecipeDetailTab
import com.hyeok.recipebook.presentation.util.ext.hideKeyboardOnTapOutside

@Composable
fun RecipeEditRoute(
    state: RecipeEditUiState,
    navigateUp: () -> Unit,
    onCompleteEdit: (RecipeEditUiModel) -> Unit
) {
    when (state) {
        RecipeEditUiState.Loading -> Unit
        is RecipeEditUiState.Success -> {
            val name = rememberTextFieldState(state.name)
            val cookingTime = rememberTextFieldState(state.cookingTime?.toString() ?: "")
            var photoUrl by remember { mutableStateOf(state.photoUrl) }

            val ingredientFormState = rememberRecipeIngredientFormState(state.ingredients)
            val stepFormState = rememberRecipeStepFormState(state.steps)
            val recordState = rememberRecipeRecordState(state.cookingRecords)

            RecipeEditScreen(
                modifier = Modifier
                    .hideKeyboardOnTapOutside(),
                name = name,
                cookingTime = cookingTime,
                photoUrl = photoUrl,
                ingredientFormState = ingredientFormState,
                stepFormState = stepFormState,
                recordState = recordState,
                navigateUp = navigateUp,
                onCompleteEdit = {
                    val isValid = true
                    // TODO:: 세부 로직 구현

                    if(isValid) {
                        onCompleteEdit(
                            RecipeEditUiModel(
                                name = name.text.toString(),
                                cookingTime = cookingTime.text.toString().toInt(),
                                photoUrl = photoUrl,
                                ingredients = ingredientFormState.toUiModel(),
                                steps = stepFormState.toUiModel(),
                                cookingRecords = recordState.toUiModel(),
                            )
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun RecipeEditScreen(
    name: TextFieldState,
    cookingTime: TextFieldState,
    photoUrl: String?,
    ingredientFormState: RecipeIngredientFormState,
    stepFormState: RecipeStepFormState,
    recordState: RecipeRecordState,
    navigateUp: () -> Unit,
    onCompleteEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RecipeDetailLayout(
        modifier = modifier,
        actionText = stringResource(R.string.btn_complete),
        header = {
            RecipeDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                name = name,
                cookingTime = cookingTime,
                photoUrl = photoUrl,
            )
        },
        pages = { page ->
            when (page) {
                RecipeDetailTab.INGREDIENTS -> IngredientTabEditContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = ingredientFormState
                )

                RecipeDetailTab.COOKING_STEPS -> StepTabEditContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = stepFormState
                )

                RecipeDetailTab.RECORD -> RecordTabEditContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = recordState
                )
            }
        },
        navigateUp = navigateUp,
        onClickAppbarAction = onCompleteEdit,
    )
}

@Composable
private fun RecipeDetailHeader(
    name: TextFieldState,
    cookingTime: TextFieldState,
    photoUrl: String?,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // TODO:: 이미지 로드 photoUrl 사용
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(YorinTheme.colors.black5)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            YorinText(
                text = "레시피 이름",
                style = YorinTheme.typography.body2
            )

            YorinTextField(
                state = name,
                placeHolder = "예: 김치찌개"
            )

            YorinText(
                text = "요리 시간(분)",
                style = YorinTheme.typography.body2
            )

            YorinNumberTextField(
                state = cookingTime,
                placeHolder = "30(분)",
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                        contentDescription = null
                    )
                }
            )
        }
    }
}