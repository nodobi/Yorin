package com.hyeok.recipebook.presentation.recipe.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ChipShape
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinRatingBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextChip
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.component.RecipeRecordEditSheet
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.edit.state.RecipeRecordState
import com.hyeok.recipebook.presentation.util.ext.hideKeyboardOnTapOutside
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordTabEditContent(
    state: RecipeRecordState,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LazyColumn(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                YorinText(
                    text = stringResource(R.string.recipe_detail_record_title),
                    style = YorinTheme.typography.body1
                )

                YorinTextChip(
                    text = stringResource(R.string.recipe_detail_record_add_label),
                    onClick = {
                        state.showRecordEditSheet()
                    },
                    shape = ChipShape.Round
                )
            }
        }

        items(
            items = state.items,
            key = { it.id }
        ) { recordState ->
            RecipeRecordEditCard(
                modifier = Modifier.fillMaxWidth(),
                recipeRecord = recordState.record,
                onClick = { target ->
                    state.showRecordEditSheet(target)
                }
            )
        }
    }

    if (state.isShowRecordEditSheet) {
        RecipeRecordEditSheet(
            modifier = Modifier
                .hideKeyboardOnTapOutside(),
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    state.closeRecordEditSheet()
                }
            },
            onConfirm = { new ->
                state.add(new)

                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    state.closeRecordEditSheet()
                }
            }
        )
    }
}

@Composable
private fun RecipeRecordEditCard(
    recipeRecord: RecipeRecordUiModel,
    modifier: Modifier = Modifier,
    onClick: (RecipeRecordUiModel) -> Unit = {},
) {
    val recordDate = remember(recipeRecord.cookedAt) {
        recipeRecord.cookedAt.format(LocalDate.Formats.ISO)
    }

    YorinCard(
        modifier = modifier
            .clickable {
                onClick(recipeRecord)
            },
        stroke = BorderStroke(1.dp, YorinTheme.colors.black5)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )
                    YorinText(
                        text = recordDate,
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.body4
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                YorinRatingBar(
                    score = recipeRecord.score
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
                    contentDescription = null,
                    tint = YorinTheme.colors.black3
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = recipeRecord.title,
                    style = YorinTheme.typography.body4,
                )
                YorinText(
                    text = recipeRecord.description,
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.body5,
                )
            }
        }
    }
}

@BackgroundPreview
@Composable
private fun RecordTabEditContentPreview() {
    RecordTabEditContent(
        state = RecipeRecordState(RecipeRecordUiModel.fakes())
    )
}