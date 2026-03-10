package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YorinModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    containerColor: Color = YorinTheme.colors.black7,
    dragHandle: @Composable () -> Unit = { YorinModalBottomSheetDefault.DragHandle() },
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth(),
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = dragHandle,
        containerColor = containerColor,
        onDismissRequest = {
            onDismiss()
        },
        content = content
    )
}

object YorinModalBottomSheetDefault {
    @Composable
    fun DragHandle() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(4.dp)
                    .background(YorinTheme.colors.black5)
            )
        }
    }
}