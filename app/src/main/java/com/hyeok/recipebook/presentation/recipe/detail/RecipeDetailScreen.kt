package com.hyeok.recipebook.presentation.recipe.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinRatingBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.RecipeDetailLayout
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.IngredientTabContent
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecordTabContent
import com.hyeok.recipebook.presentation.recipe.detail.step.StepTabContent
import com.hyeok.recipebook.presentation.recipe.model.RecipeDetailTab

@Composable
fun RecipeDetailRoute(
    state: RecipeDetailUiState,
    navigateUp: () -> Unit,
    onEditStart: () -> Unit,
    onAddRecord: (RecipeRecordUiModel) -> Unit
) {
    when (state) {
        RecipeDetailUiState.Loading -> Unit
        is RecipeDetailUiState.Edit -> Unit

        is RecipeDetailUiState.Success -> RecipeDetailScreen(
            modifier = Modifier,
            state = state,
            navigateUp = navigateUp,
            onEditStart = onEditStart,
            onAddNewRecord = onAddRecord,
        )
    }
}

@Composable
private fun RecipeDetailScreen(
    state: RecipeDetailUiState.Success,
    navigateUp: () -> Unit,
    onEditStart: () -> Unit,
    onAddNewRecord: (RecipeRecordUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    RecipeDetailLayout(
        modifier = modifier,
        actionText = stringResource(R.string.btn_edit),
        header = {
            RecipeDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                name = state.recipe.name,
                cookingTime = state.recipe.cookingTime,
                averageScore = state.recipe.averageScore,
            )
        },
        pages = { page ->
            when (page) {
                RecipeDetailTab.INGREDIENTS ->
                    IngredientTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe
                    )

                RecipeDetailTab.COOKING_STEPS ->
                    StepTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe
                    )

                RecipeDetailTab.RECORD -> {
                    RecordTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe,
                        onAddNewRecord = onAddNewRecord
                    )
                }
            }
        },
        navigateUp = navigateUp,
        onClickAppbarAction = onEditStart
    )
}

@Composable
private fun RecipeDetailHeader(
    name: String,
    cookingTime: Int,
    averageScore: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // TODO:: 이미지 로드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(YorinTheme.colors.black5)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinText(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                style = YorinTheme.typography.title1
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                    contentDescription = null,
                    tint = YorinTheme.colors.black3
                )

                Spacer(modifier = Modifier.size(4.dp))

                YorinText(
                    text = "${cookingTime}분",
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.body2
                )

                Spacer(modifier = Modifier.size(16.dp))

                YorinRatingBar(
                    score = averageScore.toInt()
                )

                Spacer(modifier = Modifier.size(8.dp))

                YorinText(
                    text = stringResource(R.string.format_score, averageScore),
                    color = YorinTheme.colors.main2,
                    style = YorinTheme.typography.body2
                )
            }
        }
    }
}

