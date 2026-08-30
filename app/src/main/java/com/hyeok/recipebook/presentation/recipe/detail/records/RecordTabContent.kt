package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.ChipShape
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextChip
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordTabContent(
    modifier: Modifier = Modifier,
    records: List<RecipeRecordUiModel> = listOf(),
    scope: CoroutineScope = rememberCoroutineScope(),
    isEditing: Boolean = true,
    onAddNewRecord: (RecipeRecordUiModel) -> Unit = {},
    onEditedRecord: (RecipeRecordUiModel) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showRecordSheet by remember { mutableStateOf(false) }

    var selectedRecord: RecipeRecordUiModel? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
                    selectedRecord = null
                    showRecordSheet = true
                },
                shape = ChipShape.Round
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(records) { record ->
                if (isEditing) {
                    EditingRecipeRecordCard(
                        modifier = Modifier.fillMaxWidth(),
                        recipeRecord = record,
                        onClick = { target ->
                            selectedRecord = target
                            showRecordSheet = true
                        }
                    )
                } else {
                    RecipeRecordCard(
                        recipeRecord = record,
                    )
                }
            }
        }
    }

    if (showRecordSheet) {
        RecipeRecordEditSheet(
            sheetState = sheetState,
            record = selectedRecord,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    showRecordSheet = false
                }
            },
            onConfirm = { new ->
                val newRecord = new

                // 새로 추가되어 저장되지 않은 상태는 id 가 -1
                if(new.id == -1L) {
                    onAddNewRecord(new)
                } else {
                    onEditedRecord(new)
                }

                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    showRecordSheet = false
                }
            }
        )
    }
}

@BackgroundPreview
@Composable
private fun RecordTabContentPreview() {
    YorinTheme {
        RecordTabContent()
    }
}