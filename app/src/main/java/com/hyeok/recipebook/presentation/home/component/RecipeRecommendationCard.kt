package com.hyeok.recipebook.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.data.database.model.RecommendationReason
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.home.model.RecipeRecommendationUiModel

@Stable
sealed interface RecipeRecommendationState {
    data class Recommendation(val recipe: RecipeRecommendationUiModel) : RecipeRecommendationState
    data object Empty : RecipeRecommendationState
    data object EmptyIngredient : RecipeRecommendationState
    data object EmptyRecipe : RecipeRecommendationState
    data object NoRecommendation : RecipeRecommendationState
}

@Composable
fun RecipeRecommendationCard(
    state: RecipeRecommendationState,
    onViewRecipeClick: () -> Unit,
    onRerollRecommendation: () -> Unit,
    onAddIngredientClick: () -> Unit,
    onAddRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        is RecipeRecommendationState.Recommendation -> RecommendationCard(
            modifier = modifier,
            recipe = state.recipe,
            onViewRecipeClick = onViewRecipeClick,
            onRerollRecommendation = onRerollRecommendation
        )

        RecipeRecommendationState.Empty -> EmptyRecommendationCard(
            modifier = modifier,
            onAddIngredientClick = onAddIngredientClick,
            onAddRecipeClick = onAddRecipeClick
        )

        RecipeRecommendationState.EmptyIngredient -> EmptyIngredientCard(
            modifier = modifier,
            onAddIngredientClick = onAddIngredientClick
        )

        RecipeRecommendationState.EmptyRecipe -> EmptyRecipeCard(
            modifier = modifier,
            onAddRecipeClick = onAddRecipeClick
        )

        RecipeRecommendationState.NoRecommendation -> NoRecommendationCard(
            modifier = modifier,
            onAddIngredientClick = onAddIngredientClick,
            onAddRecipeClick = onAddRecipeClick
        )
    }
}

@Composable
private fun RecommendationCard(
    recipe: RecipeRecommendationUiModel,
    onViewRecipeClick: () -> Unit,
    onRerollRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val missingIngredients = recipe.reasons.firstNotNullOfOrNull {
        it as? RecommendationReason.MissingIngredients
    }?.missingIngredients ?: emptyList()

    val expiringIngredients = recipe.reasons.firstNotNullOfOrNull {
        it as? RecommendationReason.UseExpiringIngredients
    }?.expiringIngredients ?: emptyList()

    Column(
        modifier = modifier
            .clip(YorinTheme.shapes.medium)
            .background(YorinTheme.colors.black7)
            .border(
                border = BorderStroke(1.dp, YorinTheme.colors.main6),
                shape = YorinTheme.shapes.medium
            )
    ) {

        // TODO:: 음식 이미지
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(YorinTheme.colors.main6)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    recipe.reasons.forEach { reason ->
                        // 반복문 외부에서 선언한다면 성능차이가 있을까?
                        val (background, fontColor) = when (reason) {
                            RecommendationReason.ReadyToCook -> YorinTheme.colors.success3 to YorinTheme.colors.success1
                            is RecommendationReason.MissingIngredients -> YorinTheme.colors.sub4 to YorinTheme.colors.sub1
                            is RecommendationReason.UseExpiringIngredients -> YorinTheme.colors.main8 to YorinTheme.colors.main1
                        }
                        val text = when (reason) {
                            RecommendationReason.ReadyToCook -> stringResource(R.string.recipe_recommendation_reason_ready_to_cook)
                            is RecommendationReason.MissingIngredients -> stringResource(R.string.recipe_recommendation_reason_missing_ingredients, reason.missingIngredients.size)
                            is RecommendationReason.UseExpiringIngredients -> stringResource(R.string.recipe_recommendation_reason_use_expiring_ingredients)
                        }

                        Box(
                            modifier = Modifier
                                .clip(YorinTheme.shapes.extraSmall)
                                .background(background)
                                .padding(4.dp)
                        ) {
                            YorinText(
                                text = text,
                                color = fontColor,
                                style = YorinTheme.typography.caption2
                            )
                        }
                    }
                }

                YorinText(
                    text = recipe.name,
                    style = YorinTheme.typography.body3
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val text = expiringIngredients.joinToString { it.name }

                        YorinText(
                            text = stringResource(R.string.recipe_recommendation_expiring_ingredients_label),
                            color = YorinTheme.colors.black3,
                            style = YorinTheme.typography.caption2
                        )

                        YorinText(
                            text = text,
                            color = YorinTheme.colors.black2,
                            style = YorinTheme.typography.caption2
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val text = missingIngredients.joinToString { it.name }

                        YorinText(
                            text = stringResource(R.string.recipe_recommendation_missing_ingredients_label),
                            color = YorinTheme.colors.black3,
                            style = YorinTheme.typography.caption2
                        )

                        YorinText(
                            text = text,
                            color = YorinTheme.colors.black2,
                            style = YorinTheme.typography.caption2
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_star_filled),
                        contentDescription = null,
                        tint = YorinTheme.colors.main2
                    )

                    YorinText(
                        text = stringResource(R.string.format_score, recipe.averageScore),
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.caption2
                    )

                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )

                    YorinText(
                        text = if (recipe.cookingTime > 0)
                            stringResource(R.string.format_cooking_time_min, recipe.cookingTime)
                        else
                            stringResource(R.string.recipe_detail_cooking_time_empty),
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.caption2
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_view_recipe),
                    shape = ButtonShape.Round,
                    onClick = { onViewRecipeClick() }
                )

                YorinTextButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.btn_do_reroll),
                    shape = ButtonShape.Round,
                    color = ButtonColor.Secondary,
                    onClick = { onRerollRecommendation() }
                )
            }
        }
    }
}

@Composable
private fun EmptyRecommendationCard(
    onAddIngredientClick: () -> Unit,
    onAddRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyRecommendationLayout(
        modifier = modifier,
        title = stringResource(R.string.recipe_recommend_empty_title),
        description = stringResource(R.string.recipe_recommend_empty_title),
        buttons = {
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_recipe_title),
                description = stringResource(R.string.recipe_recommend_empty_add_recipe_description),
                onClick = onAddRecipeClick
            )
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_ingredient_title),
                description = stringResource(R.string.recipe_recommend_empty_add_ingredient_description),
                onClick = onAddIngredientClick
            )
        }
    )
}

@Composable
private fun EmptyIngredientCard(
    onAddIngredientClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyRecommendationLayout(
        modifier = modifier,
        title = stringResource(R.string.recipe_recommend_empty_recipe_title),
        description = stringResource(R.string.recipe_recommend_empty_recipe_description),
        buttons = {
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_ingredient_title),
                description = stringResource(R.string.recipe_recommend_empty_add_ingredient_description),
                onClick = onAddIngredientClick
            )
        }
    )
}

@Composable
private fun EmptyRecipeCard(
    onAddRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyRecommendationLayout(
        modifier = modifier,
        title = stringResource(R.string.recipe_recommend_empty_ingredient_title),
        description = stringResource(R.string.recipe_recommend_empty_ingredient_description),
        buttons = {
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_recipe_title),
                description = stringResource(R.string.recipe_recommend_empty_add_recipe_description),
                onClick = onAddRecipeClick
            )
        }
    )
}

@Composable
private fun NoRecommendationCard(
    onAddIngredientClick: () -> Unit,
    onAddRecipeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyRecommendationLayout(
        modifier = modifier,
        title = stringResource(R.string.recipe_recommend_unable_title),
        description = stringResource(R.string.recipe_recommend_unable_description),
        buttons = {
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_recipe_title),
                description = stringResource(R.string.recipe_recommend_empty_add_recipe_description),
                onClick = onAddRecipeClick
            )
            RecommendationButton(
                title = stringResource(R.string.recipe_recommend_empty_add_ingredient_title),
                description = stringResource(R.string.recipe_recommend_empty_add_ingredient_description),
                onClick = onAddIngredientClick
            )
        }
    )
}

@Composable
private fun EmptyRecommendationLayout(
    title: String,
    description: String,
    buttons: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    YorinCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = YorinTheme.colors.main8
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                YorinText(
                    text = title,
                    style = YorinTheme.typography.body2,
                )
                YorinText(
                    text = description,
                    style = YorinTheme.typography.body5,
                    color = YorinTheme.colors.black2
                )
            }

            buttons()
        }
    }
}


@Composable
private fun RecommendationButton(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(YorinTheme.shapes.medium)
            .background(YorinTheme.colors.black7)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            YorinText(
                text = title,
                style = YorinTheme.typography.body5
            )
            YorinText(
                text = description,
                style = YorinTheme.typography.caption1,
                color = YorinTheme.colors.black2
            )
        }
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
            contentDescription = null
        )
    }
}

@Composable
private fun RecipeRecommendationCardPreviewContent(state: RecipeRecommendationState) {
    YorinTheme {
        RecipeRecommendationCard(
            state = state,
            onViewRecipeClick = {},
            onRerollRecommendation = {},
            onAddIngredientClick = {},
            onAddRecipeClick = {},
        )
    }
}

@Preview
@Composable
private fun RecipeRecommendationCardRecommendationPreview() {
    YorinTheme {
        RecipeRecommendationCardPreviewContent(
            state = RecipeRecommendationState.Recommendation(
                RecipeRecommendationUiModel(
                    id = 0,
                    name = "추천 레시피 이름",
                    cookingTime = 30,
                    averageScore = 4.7f,
                    photoUrl = null,
                    reasons = setOf(RecommendationReason.ReadyToCook)
                )
            )
        )
    }
}

@Preview
@Composable
private fun RecipeRecommendationCardEmptyPreview() {
    YorinTheme {
        RecipeRecommendationCardPreviewContent(state = RecipeRecommendationState.Empty)
    }
}

@Preview
@Composable
private fun RecipeRecommendationCardEmptyIngredientPreview() {
    YorinTheme {
        RecipeRecommendationCardPreviewContent(state = RecipeRecommendationState.EmptyIngredient)
    }
}

@Preview
@Composable
private fun RecipeRecommendationCardEmptyRecipePreview() {
    YorinTheme {
        RecipeRecommendationCardPreviewContent(state = RecipeRecommendationState.EmptyRecipe)
    }
}

@Preview
@Composable
private fun RecipeRecommendationCardNoRecommendationPreview() {
    YorinTheme {
        RecipeRecommendationCardPreviewContent(state = RecipeRecommendationState.NoRecommendation)
    }
}
