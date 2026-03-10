package com.hyeok.recipebook.presentation.ingredient.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun ExpirationWarningCard(
    expiredCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
) {
    YorinCard(
        modifier = modifier
            .clickable(
                enabled = enabled
            ) {
                onClick()
            },
        backgroundColor = YorinTheme.colors.sub4,
        shape = RoundedCornerShape(12.dp),
        stroke = BorderStroke(
            width = 1.dp,
            color = YorinTheme.colors.sub3
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_error_circle),
                contentDescription = null,
                tint = YorinTheme.colors.sub1
            )
            Column {
                YorinText(
                    text = stringResource(R.string.ingredient_expiration_warning_title),
                    style = YorinTheme.typography.body2,
                    color = YorinTheme.colors.sub1
                )
                YorinText(
                    text = stringResource(
                        R.string.ingredient_expiration_warning_body,
                        expiredCount
                    ),
                    style = YorinTheme.typography.caption1,
                    color = YorinTheme.colors.sub1
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpirationWarningCardPreview() {
    YorinTheme {
        ExpirationWarningCard(
            modifier = Modifier,
            expiredCount = 10
        )
    }
}