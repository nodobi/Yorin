package com.hyeok.recipebook.presentation.recipe.detail.step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun StepTabContent(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false
) {
    Box(
        modifier = modifier
            .background(YorinTheme.colors.sub3)
    )
}