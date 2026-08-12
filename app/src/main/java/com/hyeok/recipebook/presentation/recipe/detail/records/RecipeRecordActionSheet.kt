package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.YorinModalBottomSheet
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeRecordActionSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onEditRecord: () -> Unit,
    onRemoveRecord: () -> Unit,
    onCancel: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    YorinModalBottomSheet(
        modifier = modifier
            .fillMaxWidth(),
        sheetState = sheetState,
        content = {
            RecipeRecordActionContent(
                onEditRecord = onEditRecord,
                onRemoveRecord = onRemoveRecord,
                onCancel = onCancel
            )
        },
        onDismiss = onDismiss
    )
}

@Composable
private fun RecipeRecordActionContent(
    modifier: Modifier = Modifier,
    onEditRecord: () -> Unit = {},
    onRemoveRecord: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        YorinTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "수정하기",
            onClick = onEditRecord,
            shape = ButtonShape.Round
        )

        YorinTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "삭제하기",
            onClick = onRemoveRecord,
            shape = ButtonShape.Round,
            color = ButtonColor.Warning
        )

        Column {
            Spacer(modifier = Modifier.height(8.dp))
            YorinTextButton(
                modifier = Modifier.fillMaxWidth(),
                text = "취소",
                onClick = onCancel,
                shape = ButtonShape.Round,
                color = ButtonColor.Secondary
            )
        }
    }
}

@BackgroundPreview
@Composable
private fun RecipeRecordActionSheetPreview() {
    YorinTheme {
        RecipeRecordActionContent()
    }
}