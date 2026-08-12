package com.hyeok.recipebook.presentation.recipe.detail.records

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinRatingBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format

@Composable
fun RecipeRecordCard(
    recipeRecord: RecipeRecordUiModel,
    modifier: Modifier = Modifier
) {
    val recordDate = remember(recipeRecord.cookedAt) {
        recipeRecord.cookedAt.format(LocalDate.Formats.ISO)
    }

    YorinCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )
                    YorinText(
                        text = recordDate,
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.body4
                    )
                }

                YorinRatingBar(
                    score = recipeRecord.score
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = recipeRecord.title,
                    style = YorinTheme.typography.body4,
                )
                YorinText(
                    text = recipeRecord.description,
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.body5,
                )
            }
        }
    }
}

@Composable
fun EditingRecipeRecordCard(
    recipeRecord: RecipeRecordUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    val recordDate = remember(recipeRecord.cookedAt) {
        recipeRecord.cookedAt.format(LocalDate.Formats.ISO)
    }

    YorinCard(
        modifier = modifier
            .clickable {
                onClick(recipeRecord.id)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )
                    YorinText(
                        text = recordDate,
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.body4
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                YorinRatingBar(
                    score = recipeRecord.score
                )

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
                    contentDescription = null,
                    tint = YorinTheme.colors.black3
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = recipeRecord.title,
                    style = YorinTheme.typography.body4,
                )
                YorinText(
                    text = recipeRecord.description,
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.body5,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeRecordCardPreview() {
    YorinTheme {
        RecipeRecordCard(
            RecipeRecordUiModel(
                id = 1,
                cookedAt = LocalDate(2026, 1, 1),
                title = "요리 기록",
                description = "요리 기록 내부",
                score = 4
            )
        )
    }
}

@Preview
@Composable
private fun EditingRecipeRecordCardPreview() {
    YorinTheme {
        EditingRecipeRecordCard(
            RecipeRecordUiModel(
                id = 1,
                cookedAt = LocalDate(2026, 1, 1),
                title = "요리 기록",
                description = "요리 기록 내부",
                score = 4
            )
        )
    }
}

