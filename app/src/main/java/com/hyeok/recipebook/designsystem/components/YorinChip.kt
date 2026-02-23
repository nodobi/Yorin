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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.util.ext.applyIfNotNull

enum class ChipShape() {
    Rectangle,
    Round;
}

enum class ChipColor {
    Primary,
    Secondary;
}

@Composable
fun YorinTextChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: ChipShape = ChipShape.Rectangle,
    color: ChipColor = ChipColor.Primary,
) {
    BasicYorinChip(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = color,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        val contentColor = LocalChipContentColor.current

        YorinText(
            text = text,
            textAlign = TextAlign.Center,
            color = contentColor
        )
    }
}

@Composable
fun YorinIconChip(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    shape: ChipShape = ChipShape.Rectangle,
    color: ChipColor = ChipColor.Primary,
) {
    BasicYorinChip(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        color = color,
        enabled = enabled,
        contentPadding = PaddingValues(start = 16.dp, end = 8.dp)
    ) {
        val contentColor = LocalChipContentColor.current

        YorinText(
            text = text,
            textAlign = TextAlign.Center,
            color = contentColor
        )

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = contentColor
        )
    }
}

@Composable
private fun BasicYorinChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: ChipShape = ChipShape.Rectangle,
    color: ChipColor = ChipColor.Primary,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val shape = when (shape) {
        ChipShape.Rectangle -> RoundedCornerShape(8.dp)
        ChipShape.Round -> RoundedCornerShape(14.dp)
    }

    val (containerColor, contentColor) = when (color) {
        ChipColor.Primary -> YorinTheme.colors.main1 to YorinTheme.colors.black6
        ChipColor.Secondary -> YorinTheme.colors.main8 to YorinTheme.colors.black3
    }

    BasicChipBox(
        modifier = modifier
            .height(32.dp),
        onClick = onClick,
        shape = shape,
        containerColor = containerColor,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalChipContentColor provides contentColor) {
            Row(
                modifier = Modifier.padding(contentPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}

/**
 * 각 컴포넌트 배치와 색상, 크기를 결정하는 Basic Component
 *
 * @param onClick
 * @param shape
 * @param modifier
 * @param containerColor
 * @param stroke
 * @param enabled
 * @param interactionSource
 * @param content
 */
@Composable
private fun BasicChipBox(
    onClick: () -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    stroke: BorderStroke? = null,
    enabled: Boolean = true,
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

private val LocalChipContentColor = compositionLocalOf { Color.Unspecified }

@Preview
@Composable
private fun YorinTextChipsPreview() {
    YorinTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                YorinTextChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    shape = ChipShape.Rectangle,
                    color = ChipColor.Primary,
                    onClick = {}
                )
                YorinTextChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    shape = ChipShape.Rectangle,
                    color = ChipColor.Secondary,
                    onClick = {}
                )

                YorinIconChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    icon = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                    shape = ChipShape.Rectangle,
                    color = ChipColor.Primary,
                    onClick = {},
                )
                YorinIconChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    icon = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                    shape = ChipShape.Rectangle,
                    color = ChipColor.Secondary,
                    onClick = {},
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                YorinTextChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    shape = ChipShape.Round,
                    color = ChipColor.Primary,
                    onClick = {}
                )
                YorinTextChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    shape = ChipShape.Round,
                    color = ChipColor.Secondary,
                    onClick = {}
                )

                YorinIconChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    icon = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                    shape = ChipShape.Round,
                    color = ChipColor.Primary,
                    onClick = {},
                )
                YorinIconChip(
                    modifier = Modifier,
                    text = "YorinChip",
                    icon = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                    shape = ChipShape.Round,
                    color = ChipColor.Secondary,
                    onClick = {},
                )
            }
        }
    }
}