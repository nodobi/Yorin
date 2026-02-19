package com.hyeok.recipebook.designsystem.components.state

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.ButtonSize
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Immutable
data class YorinButtonState(
    val containerColor: Color,
    val contentColor: Color,
    val height: Dp,
    val horizontalPadding: Dp,
    val shape: Shape,
    val borderStroke: BorderStroke? = null
) {
    companion object {
        @Composable
        fun from(
            buttonShape: ButtonShape = ButtonShape.Rectangle,
            buttonSize: ButtonSize = ButtonSize.Medium,
            buttonColor: ButtonColor = ButtonColor.Primary,
            enabled: Boolean = false
        ): YorinButtonState {
            val (containerColor, contentColor) = when (buttonColor) {
                ButtonColor.Primary -> YorinTheme.colors.main1 to YorinTheme.colors.black6
                ButtonColor.Warning -> YorinTheme.colors.sub1 to YorinTheme.colors.black6
                ButtonColor.Secondary -> YorinTheme.colors.main8 to YorinTheme.colors.black3
            }.let { if (enabled) YorinTheme.colors.black6 to YorinTheme.colors.black3 else it }

            val shape = when (buttonShape) {
                ButtonShape.Rectangle -> RoundedCornerShape(6.dp)
                ButtonShape.Round -> RoundedCornerShape(10.dp)
            }

            val horizontalPadding = when (buttonSize) {
                ButtonSize.Large -> 24.dp
                ButtonSize.Medium -> 16.dp
            }

            val height = when (buttonSize) {
                ButtonSize.Large -> 42.dp
                ButtonSize.Medium -> 34.dp
            }

            val borderStroke = if (enabled) BorderStroke(
                width = 1.dp,
                color = YorinTheme.colors.black5
            ) else null

            return YorinButtonState(
                containerColor = containerColor,
                contentColor = contentColor,
                height = height,
                horizontalPadding = horizontalPadding,
                shape = shape,
                borderStroke = borderStroke,
            )
        }
    }
}