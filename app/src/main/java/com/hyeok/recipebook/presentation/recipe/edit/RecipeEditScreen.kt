package com.hyeok.recipebook.presentation.recipe.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.byValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.data.validation.FormValidator
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.RecipeDetailLayout
import com.hyeok.recipebook.presentation.recipe.component.formfield.BasicFormField
import com.hyeok.recipebook.presentation.recipe.component.formfield.FormFieldState
import com.hyeok.recipebook.presentation.recipe.component.formfield.rememberFormFieldState
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
            val name = rememberFormFieldState(state.name, FormType.RecipeName)
            val cookingTime = rememberFormFieldState(state.cookingTime?.toString() ?: "", FormType.RecipeCookingTime)
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
    name: FormFieldState,
    cookingTime: FormFieldState,
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
    name: FormFieldState,
    cookingTime: FormFieldState,
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
                text = stringResource(R.string.recipe_detail_recipe_title),
                style = YorinTheme.typography.body2
            )

            BasicFormField(
                state = name,
                placeHolder = stringResource(R.string.recipe_detail_recipe_hint)
            )

            YorinText(
                text = stringResource(R.string.recipe_detail_cooking_time_title),
                style = YorinTheme.typography.body2
            )

            BasicFormField(
                modifier = Modifier
                    .fillMaxWidth(),
                state = cookingTime,
                textStyle = YorinTheme.typography.button1,
                placeHolderColor = YorinTheme.colors.black3,
                placeHolder = stringResource(R.string.recipe_detail_cooking_time_hint),
                keyboardAction = KeyboardOptions(keyboardType = KeyboardType.Number),
                inputTransformation = InputTransformation.byValue { current, proposed ->
                    if (proposed.all { it.isDigit() } && proposed.length <= FormValidator.WEIGHT_QUANTITY_MAX.toString().length) proposed
                    else current
                },
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

@BackgroundPreview
@Composable
private fun RecipEditScreenPreview() {
    YorinTheme {
        RecipeEditScreen(
            name = rememberFormFieldState("", FormType.RecipeName),
            cookingTime = rememberFormFieldState("", FormType.RecipeCookingTime),
            photoUrl = null,
            ingredientFormState = RecipeIngredientFormState(listOf()),
            stepFormState = RecipeStepFormState(listOf()),
            recordState = RecipeRecordState(listOf()),
            navigateUp = {},
            onCompleteEdit = {}
        )
    }
}