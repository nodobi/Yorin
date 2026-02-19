package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.state.YorinButtonState
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.util.ext.applyIfNotNull


enum class ButtonShape() {
    Rectangle,
    Round;
}

enum class ButtonSize {
    Large,
    Medium,
}

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
            .height(buttonState.height),
        containerColor = buttonState.containerColor,
        enabled = enabled,
        stroke = buttonState.borderStroke,
        interactionSource = interactionSource
    ) {
        YorinText(
            modifier = Modifier
                .padding(horizontal = buttonState.horizontalPadding),
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
    containerColor: Color = Color.Unspecified,
    enabled: Boolean = true,
    stroke: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .applyIfNotNull(stroke) {
                border(it, shape)
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Preview
@Composable
private fun YorinTextButtonsPreview() {
    YorinTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            YorinTextButton(
                text = "YorinText",
                size = ButtonSize.Medium,
                color = ButtonColor.Primary,
                onClick = {}
            )
            YorinTextButton(
                text = "YorinText",
                size = ButtonSize.Medium,
                color = ButtonColor.Secondary,
                onClick = {}
            )
            YorinTextButton(
                text = "YorinText",
                size = ButtonSize.Medium,
                color = ButtonColor.Warning,
                onClick = {}
            )
            YorinTextButton(
                text = "YorinText",
                size = ButtonSize.Medium,
                color = ButtonColor.Primary,
                onClick = {},
                enabled = false
            )
        }
    }
}