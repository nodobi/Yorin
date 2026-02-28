package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.util.ext.applyIfNotNull

@Composable
fun YorinCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = YorinTheme.colors.black7,
    shape: Shape = RoundedCornerShape(12.dp),
    stroke: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor, shape)
            .applyIfNotNull(stroke) {
                border(it, shape)
            }
            .then(modifier)
    ) {
        content()
    }
}