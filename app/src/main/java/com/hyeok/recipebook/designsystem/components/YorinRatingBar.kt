package com.hyeok.recipebook.designsystem.components

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun YorinRatingBar(
    modifier: Modifier = Modifier,
    @IntRange(1L, 5L) score: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_star_filled),
                contentDescription = null,
                tint = if (i <= score) YorinTheme.colors.main2 else YorinTheme.colors.main6
            )
        }
    }
}

@Preview
@Composable
private fun YorinRatingBarPreview() {
    YorinTheme {
        YorinRatingBar(
            modifier = Modifier,
            score = 5
        )
    }
}