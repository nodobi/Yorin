package com.hyeok.recipebook.presentation.recipe.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.detail.step.StepTabContent
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.IngredientsTabContent
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.RecipeIngredientUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecordTabContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailRoute(

) {
    val tabs = RecipeDetailTab.entries
    var isEditing by remember { mutableStateOf(false) }

    RecipeDetailScreen(
        isEditing = isEditing,
        onEditRecipe = {
            isEditing = !isEditing
        }
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    isEditing: Boolean = false,
    scope: CoroutineScope = rememberCoroutineScope(),
    onEditRecipe: () -> Unit = {}
) {
    val pagerState = rememberPagerState(0) { 3 }
    val currentPage = pagerState.currentPage

    val tabs = RecipeDetailTab.entries

    var tabBarHeightPx by remember { mutableIntStateOf(0) }
    var columnHeightPx by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
    ) {
        YorinAppbar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            titleAlignment = Alignment.CenterStart,
            useNavigation = true,
            action = {
                YorinText(
                    modifier = Modifier
                        .clickable(
                            onClick = onEditRecipe
                        ),
                    text =
                        if (isEditing)
                            stringResource(R.string.btn_complete)
                        else
                            stringResource(R.string.btn_edit),
                    style = YorinTheme.typography.title1
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .onSizeChanged {
                    columnHeightPx = it.height
                }
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(YorinTheme.colors.black5)
                )
            }
            stickyHeader {
                TabRow(
                    modifier = Modifier
                        .wrapContentHeight()
                        .onSizeChanged {
                            tabBarHeightPx = it.height
                        },
                    indicator = { tabPositions ->
                        if (currentPage < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[currentPage]),
                                height = 1.dp,
                                color = YorinTheme.colors.main1
                            )
                        }
                    },
                    selectedTabIndex = currentPage
                ) {
                    RecipeDetailTab.entries.forEachIndexed { index, tab ->
                        Tab(
                            modifier = Modifier.height(70.dp),
                            selected = currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_refrigerator),
                                    contentDescription = null
                                )
                            },
                            text = {
                                YorinText(
                                    text = tab.toLabel(),
                                    style = YorinTheme.typography.body5,
                                    color = if (currentPage == index) {
                                        YorinTheme.colors.main1
                                    } else {
                                        YorinTheme.colors.black3
                                    }
                                )
                            },
                            selectedContentColor = YorinTheme.colors.main1,
                            unselectedContentColor = YorinTheme.colors.black3
                        )
                    }
                }
            }

            item {
                val pagerHeight = with(LocalDensity.current) {
                    (columnHeightPx - tabBarHeightPx).toDp()
                }

                HorizontalPager(
                    modifier = Modifier
                        .height(pagerHeight)
                        .fillMaxWidth(),
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { page ->
                    when (tabs[page]) {
                        RecipeDetailTab.INGREDIENTS ->
                            IngredientsTabContent(
                                modifier = Modifier.fillMaxWidth(),
                                ingredients = listOf(
                                    RecipeIngredientUiModel.dummy1,
                                    RecipeIngredientUiModel.dummy2,
                                ),
                                isEditing = isEditing
                            )

                        RecipeDetailTab.COOKING_STEPS ->
                            StepTabContent(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                isEditing = isEditing
                            )

                        RecipeDetailTab.RECORD -> {
                            RecordTabContent(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth(),
                                isEditing = isEditing
                            )
                        }
                    }
                }


            }

        }
    }
}

enum class RecipeDetailTab {
    INGREDIENTS,
    COOKING_STEPS,
    RECORD;

    @Composable
    fun toLabel(): String {
        return when (this) {
            INGREDIENTS -> stringResource(R.string.recipe_detail_tab_ingredient)
            COOKING_STEPS -> stringResource(R.string.recipe_detail_tab_step)
            RECORD -> stringResource(R.string.recipe_detail_tab_record)
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    RecipeDetailScreen(

    )
}