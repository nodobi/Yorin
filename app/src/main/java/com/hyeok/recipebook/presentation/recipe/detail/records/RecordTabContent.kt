package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyeok.recipebook.designsystem.theme.YorinTheme

// TODO:: 수정 필요
@Composable
fun RecordTabContent(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {
    Box(
        modifier = modifier
            .background(YorinTheme.colors.success3)
    )
}