package com.hyeok.recipebook.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.data.database.model.IngredientModel
import com.hyeok.recipebook.data.database.model.RecipeIngredientModel
import com.hyeok.recipebook.data.database.model.RecipeSummaryModel
import com.hyeok.recipebook.data.database.model.RecommendationReason
import com.hyeok.recipebook.designsystem.components.ButtonColor
import com.hyeok.recipebook.designsystem.components.ButtonShape
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextButton
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.home.model.OwnedIngredientUiModel
import com.hyeok.recipebook.presentation.home.model.RecipeRecommendationUiModel
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    onExpirationCardClick: () -> Unit,
    onViewRecommendedRecipeClick: () -> Unit,
    onRerollRecipeRecommendation: () -> Unit,
    onViewExpiringIngredientsClick: () -> Unit,
    onRecipeClick: (Long) -> Unit
) {
    val scrollState = rememberScrollState()

    HomeScreen(
        modifier = Modifier
            .fillMaxWidth()
            .background(YorinTheme.colors.black7)
            .verticalScroll(scrollState),
        expiredIngredients = uiState.expiredIngredients,
        expiringIngredients = uiState.expiringIngredients,
        recipeRecommendation = uiState.recipeRecommendation,
        readyToCookRecipes = uiState.readyToCookRecipes,
        recentlyCookedRecipes = uiState.recentlyCookedRecipes,
        onExpirationCardClick = onExpirationCardClick,
        onViewRecommendedRecipeClick = onViewRecommendedRecipeClick,
        onRerollRecipeRecommendation = onRerollRecipeRecommendation,
        onViewExpiringIngredientsClick = onViewExpiringIngredientsClick,
        onRecipeClick = onRecipeClick
    )
}

@Composable
private fun HomeScreen(
    expiredIngredients: List<OwnedIngredientUiModel>,
    expiringIngredients: List<OwnedIngredientUiModel>,
    recipeRecommendation: RecipeRecommendationUiModel,
    readyToCookRecipes: List<RecipeSummaryModel>,
    recentlyCookedRecipes: List<RecipeSummaryModel>,
    onExpirationCardClick: () -> Unit,
    onViewRecommendedRecipeClick: () -> Unit,
    onRerollRecipeRecommendation: () -> Unit,
    onViewExpiringIngredientsClick: () -> Unit,
    onRecipeClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YorinAppbar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.menu_home),
            titleAlignment = Alignment.CenterStart
        )

        if (expiredIngredients.isNotEmpty()) {
            ExpirationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                expirationCount = expiredIngredients.size,
                onClick = onExpirationCardClick,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinText(
                text = stringResource(R.string.home_recipe_recommendation_title),
                style = YorinTheme.typography.body2
            )

            RecipeRecommendationCard(
                modifier = Modifier.fillMaxWidth(),
                recipe = recipeRecommendation,
                onViewRecipeClick = onViewRecommendedRecipeClick,
                onRerollRecommendation = onRerollRecipeRecommendation,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                YorinText(
                    text = stringResource(R.string.home_expiring_ingredient_title),
                    style = YorinTheme.typography.body2
                )

                Row(
                    modifier = Modifier
                        .clickable {
                            onViewExpiringIngredientsClick()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YorinText(
                        text = stringResource(R.string.btn_view_all),
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.body4
                    )

                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
                        contentDescription = null,
                        tint = YorinTheme.colors.black3
                    )
                }
            }

            Column(
                modifier = Modifier
                    .clip(YorinTheme.shapes.medium)
                    .background(YorinTheme.colors.main8),
            ) {
                val filteredExpiringIngredients = remember(expiringIngredients) {
                    expiringIngredients
                        .asSequence()
                        .filter { it.expirationDate != null && it.daysToExpiration != null }
                        .take(4)
                        .toList()
                }

                for (expiringIngredient in filteredExpiringIngredients) {
                    ExpiringIngredientCard(
                        modifier = Modifier
                            .padding(16.dp),
                        ownIngredient = expiringIngredient
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.home_ready_to_cook_recipes_title),
                style = YorinTheme.typography.body2
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.width(12.dp))

                for (readyToCook in readyToCookRecipes) {
                    SimpleRecipeCard(
                        name = readyToCook.name,
                        photoUrl = readyToCook.photoUrl,
                        averageScore = readyToCook.averageScore,
                        cookingTime = readyToCook.cookingTime,
                        onClickRecipe = {
                            onRecipeClick(readyToCook.id)
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinText(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = stringResource(R.string.home_recently_cooked_recipes_title),
                style = YorinTheme.typography.body2
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.width(12.dp))

                for (recentlyCooked in recentlyCookedRecipes) {
                    SimpleRecipeCard(
                        name = recentlyCooked.name,
                        photoUrl = recentlyCooked.photoUrl,
                        averageScore = recentlyCooked.averageScore,
                        cookingTime = recentlyCooked.cookingTime,
                        onClickRecipe = {
                            onRecipeClick(recentlyCooked.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpirationCard(
    expirationCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(YorinTheme.colors.sub4)
            .clip(YorinTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YorinText(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onClick()
                },
            text = stringResource(R.string.home_expired_ingredient_title, expirationCount),
            color = YorinTheme.colors.sub1,
            style = YorinTheme.typography.body5
        )

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_front_small),
            contentDescription = null,
            tint = YorinTheme.colors.sub1
        )
    }
}

@Composable
private fun RecipeRecommendationCard(
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
fun ExpiringIngredientCard(
    ownIngredient: OwnedIngredientUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        requireNotNull(ownIngredient.daysToExpiration) // expiredIngredients 는 daysToExpiration 가 null 이 아닌 경우만 포함

        // TODO:: 만료 상태 별도 데이터 클래스로 모델링
        val (badgeContainerColor, badgeTextColor) = if (ownIngredient.daysToExpiration <= 1) {
            YorinTheme.colors.sub4 to YorinTheme.colors.sub1
        } else if (ownIngredient.daysToExpiration <= 5) {
            YorinTheme.colors.main6 to YorinTheme.colors.main1
        } else run {
            YorinTheme.colors.success3 to YorinTheme.colors.success1
        }

        Row(
            modifier = Modifier
                .weight(1f, fill = false),
            verticalAlignment = Alignment.CenterVertically
        ) {
            YorinText(
                modifier = Modifier.weight(1f, fill = false),
                text = ownIngredient.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = YorinTheme.typography.body5
            )

            Spacer(modifier = Modifier.width(4.dp))

            YorinText(
                text = "${ownIngredient.weight}${ownIngredient.weightUnit}",
                color = YorinTheme.colors.black2,
                style = YorinTheme.typography.caption2,
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            modifier = Modifier
                .fillMaxHeight()
                .clip(YorinTheme.shapes.small)
                .background(badgeContainerColor)
                .padding(horizontal = 8.dp)
                .wrapContentHeight(),
            color = badgeTextColor,
            text = stringResource(R.string.format_remain_day, ownIngredient.daysToExpiration),
            textAlign = TextAlign.Center,
            style = YorinTheme.typography.caption1
        )
    }
}

@Composable
private fun SimpleRecipeCard(
    name: String,
    photoUrl: String?,
    averageScore: Float,
    cookingTime: Int,
    onClickRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(YorinTheme.shapes.small)
            .clickable {
                onClickRecipe()
            },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // TODO:: 음식 이미지
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(96.dp)
                .clip(YorinTheme.shapes.small)
                .background(YorinTheme.colors.main6)
        )

        YorinText(
            text = name,
            style = YorinTheme.typography.body4
        )

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
                text = stringResource(R.string.format_score, averageScore),
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
                text = if (cookingTime > 0)
                    stringResource(R.string.format_cooking_time_min, cookingTime)
                else
                    stringResource(R.string.recipe_detail_cooking_time_empty),
                color = YorinTheme.colors.black3,
                style = YorinTheme.typography.caption2
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview
@Composable
private fun HomeScreenPreview() {
    // 프리뷰 더 깔끔하게 할 수 있는 방법?
    YorinTheme {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val ownIngredients = listOf(
            OwnedIngredientUiModel(
                id = 1,
                name = "재료1",
                purchaseDate = today.date.minus(10, DateTimeUnit.DAY),
                expirationDate = today.date,
                daysToExpiration = 0,
                weight = 100,
                weightUnit = "g",
                description = "",
            ),
            OwnedIngredientUiModel(
                id = 2,
                name = "재료2",
                purchaseDate = today.date.minus(10, DateTimeUnit.DAY),
                expirationDate = today.date.plus(3, DateTimeUnit.DAY),
                daysToExpiration = 3,
                weight = 200,
                weightUnit = "g",
                description = "",
            ),
            OwnedIngredientUiModel(
                id = 3,
                name = "재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3",
                purchaseDate = today.date.minus(10, DateTimeUnit.DAY),
                expirationDate = today.date.plus(8, DateTimeUnit.DAY),
                daysToExpiration = 8,
                weight = 300,
                weightUnit = "g",
                description = "",
            )
        )

        val recipeRecommendation = RecipeRecommendationUiModel(
            id = 1,
            name = "김치찌개",
            cookingTime = 30,
            averageScore = 4.1f,
            photoUrl = null,
            reasons = setOf(
                RecommendationReason.ReadyToCook,
                RecommendationReason.MissingIngredients(
                    listOf(
                        RecipeIngredientModel(
                            id = 1,
                            name = "재료1",
                            weightUnit = "g",
                            requireQuantity = 100
                        ), RecipeIngredientModel(
                            id = 2,
                            name = "재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3",
                            weightUnit = "g",
                            requireQuantity = 100
                        )
                    )
                ),
                RecommendationReason.UseExpiringIngredients(
                    listOf(
                        IngredientModel(
                            id = 1,
                            name = "재료1",
                            purchaseDate = 0,
                            expirationDate = 0,
                            weight = 0,
                            weightUnit = "g",
                            description = "",
                        ),
                        IngredientModel(
                            id = 2,
                            name = "재료1",
                            purchaseDate = 0,
                            expirationDate = 0,
                            weight = 0,
                            weightUnit = "g",
                            description = "",
                        ),
                        IngredientModel(
                            id = 3,
                            name = "재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3재료3",
                            purchaseDate = 0,
                            expirationDate = 0,
                            weight = 0,
                            weightUnit = "g",
                            description = "",
                        )
                    )
                )
            )
        )

        val recipeSummaries = listOf(
            RecipeSummaryModel(
                id = 1,
                name = "김치찌개",
                registerDate = 0L,
                cookingTime = 30,
                photoUrl = null,
                rawIngredients = "김치, 김치, 김치김치",
                averageScore = 4.0f
            ),
            RecipeSummaryModel(
                id = 2,
                name = "된장찌개",
                registerDate = 0L,
                cookingTime = 30,
                photoUrl = null,
                rawIngredients = "된장, 된장, 된장된장",
                averageScore = 4.0f
            ),
            RecipeSummaryModel(
                id = 3,
                name = "고추장찌개",
                registerDate = 0L,
                cookingTime = 30,
                photoUrl = null,
                rawIngredients = "고추장, 고추장, 고추장고추장",
                averageScore = 4.0f
            )
        )

        HomeScreen(
            modifier = Modifier
                .fillMaxWidth()
                .background(YorinTheme.colors.black7),
            expiredIngredients = ownIngredients.takeLast(2),
            expiringIngredients = ownIngredients.takeLast(2),
            recipeRecommendation = recipeRecommendation,
            readyToCookRecipes = recipeSummaries,
            recentlyCookedRecipes = recipeSummaries,
            onExpirationCardClick = {},
            onViewRecommendedRecipeClick = {},
            onRerollRecipeRecommendation = {},
            onViewExpiringIngredientsClick = {},
            onRecipeClick = {},
        )
    }
}