package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.util.ext.applyIfNotNull


/**
 * 버튼의 형태를 결정
 *
 */
enum class ButtonShape() {
    Rectangle,
    Round;
}

/**
 * 버튼의 높이, 좌우 패딩을 결정
 *
 */
enum class ButtonSize {
    Large,
    Medium,
}

/**
 * 버튼의 색상
 *
 */
enum class ButtonColor {
    Primary,
    Warning,
    Secondary,
}

@Composable
fun YorinTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: ButtonShape = ButtonShape.Rectangle,
    size: ButtonSize = ButtonSize.Medium,
    color: ButtonColor = ButtonColor.Primary,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val buttonState = YorinButtonState.from(
        buttonShape = shape,
        buttonSize = size,
        buttonColor = color,
        enabled = enabled,
    )
    // onPressed 상태일 때 색상 변경할지 고민

    BasicButtonBox(
        onClick = onClick,
        shape = buttonState.shape,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonState.height)
            .padding(horizontal = buttonState.horizontalPadding)
            .background(buttonState.containerColor),
        enabled = enabled,
        stroke = buttonState.borderStroke,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            color = buttonState.contentColor,
            style = YorinTheme.typography.button1
        )
    }
}

/**
 * 각 컴포넌트 배치와 색상, 크기를 결정하는 Basic Component
 *
 * @param onClick
 * @param shape
 * @param modifier
 * @param enabled
 * @param stroke
 * @param interactionSource
 * @param content
 */
@Composable
private fun BasicButtonBox(
    onClick: () -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    stroke: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .applyIfNotNull(stroke) {
                border(it, shape)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        content = content
    )
}

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