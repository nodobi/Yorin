package com.hyeok.recipebook.presentation.recipe.component

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.detail.RecipeDetailTab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailLayout(
    actionText: String,
    header: @Composable () -> Unit,
    pages: @Composable (RecipeDetailTab) -> Unit,
    navigateUp: () -> Unit,
    onClickAppbarAction: () -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val pagerState = rememberPagerState(0) { 3 }
    val currentPage = pagerState.currentPage

    val tabs = RecipeDetailTab.entries

    var tabBarHeightPx by remember { mutableIntStateOf(0) }
    var columnHeightPx by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier,
    ) {
        YorinAppbar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            titleAlignment = Alignment.CenterStart,
            useNavigation = true,
            navigateUp = {
                navigateUp()
            },
            action = {
                YorinText(
                    modifier = Modifier
                        .clickable {
                            onClickAppbarAction()
                        },
                    text = actionText,
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
                header()
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
                                    imageVector = tab.toImage(),
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
                    pages(tabs[page])
                }
            }
        }
    }
}