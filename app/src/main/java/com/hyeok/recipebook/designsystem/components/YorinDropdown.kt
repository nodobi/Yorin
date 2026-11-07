package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun YorinDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    button: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    items: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = modifier) {
        button()

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            offset = DpOffset(x = 0.dp, y = 8.dp),
            shape = YorinTheme.shapes.medium,
            containerColor = YorinTheme.colors.black6,
            shadowElevation = 4.dp,
            border = BorderStroke(1.dp, YorinTheme.colors.black5)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                items()
            }
        }
    }
}

@Composable
fun YorinDropdownItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIconVector: ImageVector? = null,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier
            .clip(YorinTheme.shapes.medium)
            .clickable {
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(8.dp)
    ) {
        if (leadingIconVector != null) {
            Icon(
                imageVector = leadingIconVector,
                contentDescription = contentDescription,
                tint = YorinTheme.colors.black1
            )
        }
        YorinText(
            text = text,
            style = YorinTheme.typography.body2
        )
    }
}

@BackgroundPreview
@Composable
private fun YorinDropdownPreview() {
    var expanded by remember { mutableStateOf(true) }

    YorinTheme {
        YorinDropdown(
            modifier = Modifier
                .width(200.dp)
                .background(YorinTheme.colors.black5),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            button = {
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )
            }
        ) {
            YorinDropdownItem(
                text = "수정하기",
                leadingIconVector = ImageVector.vectorResource(R.drawable.ic_edit),
                onClick = { expanded = false }
            )
            YorinDropdownItem(
                text = "수정하기",
                leadingIconVector = ImageVector.vectorResource(R.drawable.ic_edit),
                onClick = { expanded = false }
            )
            YorinDropdownItem(
                text = "수정하기",
                leadingIconVector = ImageVector.vectorResource(R.drawable.ic_edit),
                onClick = { expanded = false }
            )
        }
    }
}