package com.hyeok.recipebook.presentation.ingredient.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun IngredientCard(
    ingredientName: String,
    remainExpirationDate: Long,
    expirationDate: String,
    weight: Int,
    weightUnit: String,
    modifier: Modifier = Modifier,
    description: String = "",
    onClick: () -> Unit = {},
) {

    YorinCard(
        modifier = modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        stroke = BorderStroke(1.dp, YorinTheme.colors.black5)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                YorinText(
                    text = ingredientName,
                    style = YorinTheme.typography.body5
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = YorinTheme.colors.sub4,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp),
                ) {
                    YorinText(
                        text = stringResource(
                            R.string.ingredient_remain_expiration_days,
                            remainExpirationDate
                        ),
                        style = YorinTheme.typography.caption1,
                        color = YorinTheme.colors.sub1
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_package),
                        contentDescription = null
                    )
                    YorinText(
                        modifier = Modifier,
                        text = "$weight$weightUnit",
                        style = YorinTheme.typography.caption1
                    )
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null
                    )
                    YorinText(
                        modifier = Modifier,
                        text = expirationDate,
                        style = YorinTheme.typography.caption1
                    )
                }
            }

            if (!description.isBlank()) {
                CardMemo(
                    memo = description
                )
            }
        }
    }
}

@Composable
private fun CardMemo(
    memo: String,
    modifier: Modifier = Modifier
) {
    YorinCard(
        modifier = modifier,
        backgroundColor = YorinTheme.colors.main8,
        stroke = BorderStroke(width = 1.dp, color = YorinTheme.colors.main5)
    ) {
        YorinText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            text = memo,
            style = YorinTheme.typography.caption1,
            color = YorinTheme.colors.black3
        )
    }
}

@Preview
@Composable
private fun IngredientCardPreview() {
    YorinTheme {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            IngredientCard(
                ingredientName = "양파",
                remainExpirationDate = 10,
                expirationDate = "2025-01-01",
                weight = 1,
                weightUnit = "개",
                description = ""
            )

            IngredientCard(
                ingredientName = "양파",
                remainExpirationDate = 10,
                expirationDate = "2025-01-01",
                weight = 1,
                weightUnit = "개",
                description = "메모에 해당하는 영역"
            )
        }
    }
}