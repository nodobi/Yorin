package com.hyeok.recipebook.presentation.recipe.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.theme.YorinTheme


@Composable
fun AddItemCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    YorinCard(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onClick()
            },
        stroke = BorderStroke(
            width = 1.dp,
            color = YorinTheme.colors.black5
        ),
        backgroundColor = YorinTheme.colors.black7
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                contentDescription = null
            )
        }
    }
}
