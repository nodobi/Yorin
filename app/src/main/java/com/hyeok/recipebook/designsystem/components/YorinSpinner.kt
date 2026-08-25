package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun YorinSpinner(
    modifier: Modifier = Modifier,
    header: @Composable (Boolean, () -> Unit) -> Unit,
    dropdown: @Composable (() -> Unit) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var headerHeightPx by remember { mutableIntStateOf(0) }
    var headerWidthPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    Box(modifier = modifier) {
        Box(
            modifier = Modifier.onSizeChanged {
                headerWidthPx = it.width
                headerHeightPx = it.height
            }
        ) {
            header(expanded) { expanded = !expanded }
        }

        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(
                    x = 0,
                    y = headerHeightPx + with(density) { 4.dp.roundToPx() }
                ),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier.width(
                        with(density) { headerWidthPx.toDp()}
                    )
                ) {
                    dropdown { expanded = false }
                }
            }
        }
    }
}

@Composable
fun DefaultSpinnerHeader(
    expended: Boolean,
    textStyle: TextStyle,
    placeHolderColor: Color,
    modifier: Modifier = Modifier,
    text: String = "",
    placeHolder: String = "",
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YorinText(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp),
            text = text.ifBlank { placeHolder },
            style = textStyle,
            color = if (text.isNotBlank()) {
                textStyle.color
            } else {
                placeHolderColor
            }
        )

        Icon(
            imageVector = if (expended)
                ImageVector.vectorResource(R.drawable.ic_arrow_up_mini)
            else
                ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
            contentDescription = null
        )
    }
}

@Composable
fun DefaultSpinnerDropdown(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = YorinTheme.typography.button1,
    selectedColor: Color = YorinTheme.colors.black5,
    divider: @Composable () -> Unit = {
        HorizontalDivider(color = YorinTheme.colors.black4)
    }
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ))
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = item == selectedItem

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSelected) selectedColor
                        else YorinTheme.colors.black6
                    )
                    .clickable(
                        onClick = { onItemSelected(item) }
                    )
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                YorinText(
                    text = item,
                    style = textStyle
                )
            }

            if (index < items.lastIndex) {
                divider
            }
        }
    }
}