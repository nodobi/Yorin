package com.hyeok.recipebook.presentation.recipe.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinNumberTextField
import com.hyeok.recipebook.designsystem.components.YorinRatingBar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.EditingIngredientTabContent
import com.hyeok.recipebook.presentation.recipe.detail.ingredients.IngredientTabContent
import com.hyeok.recipebook.presentation.recipe.detail.records.EditingRecordTabContent
import com.hyeok.recipebook.presentation.recipe.detail.records.RecipeRecordUiModel
import com.hyeok.recipebook.presentation.recipe.detail.records.RecordTabContent
import com.hyeok.recipebook.presentation.recipe.detail.step.EditingStepTabContent
import com.hyeok.recipebook.presentation.recipe.detail.step.StepTabContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailRoute(
    state: RecipeDetailUiState,
    onEditStart: () -> Unit,
    onCompleteEdit: () -> Unit,
    onAddRecord: (RecipeRecordUiModel) -> Unit
) {
    when (state) {
        RecipeDetailUiState.Loading -> Unit

        is RecipeDetailUiState.Success -> RecipeDetailScreen(
            modifier = Modifier,
            state = state,
            onEditStart = onEditStart,
            onAddNewRecord = onAddRecord,
        )

        is RecipeDetailUiState.Edit -> {
            val editState = remember(state) {
                RecipeDetailEditState.fromUiModel(state.initialRecipe)
            }

            EditingRecipeDetailScreen(
                modifier = Modifier,
                editState = editState,
                onCompleteEdit = onCompleteEdit
            )
        }
    }
}

@Composable
private fun RecipeDetailScreen(
    state: RecipeDetailUiState.Success,
    onEditStart: () -> Unit,
    onAddNewRecord: (RecipeRecordUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    RecipeDetailLayout(
        modifier = modifier,
        actionText = stringResource(R.string.btn_edit),
        header = {
            RecipeDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                name = state.recipe.name,
                cookingTime = state.recipe.cookingTime,
                averageScore = state.recipe.averageScore,
            )
        },
        pages = { page ->
            when (page) {
                RecipeDetailTab.INGREDIENTS ->
                    IngredientTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe
                    )

                RecipeDetailTab.COOKING_STEPS ->
                    StepTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe
                    )

                RecipeDetailTab.RECORD -> {
                    RecordTabContent(
                        modifier = Modifier.fillMaxWidth(),
                        recipe = state.recipe,
                        onAddNewRecord = onAddNewRecord
                    )
                }
            }
        },
        onClickAppbarAction = onEditStart
    )
}

@Composable
private fun EditingRecipeDetailScreen(
    editState: RecipeDetailEditState,
    modifier: Modifier = Modifier,
    onCompleteEdit: () -> Unit = {}
) {
    RecipeDetailLayout(
        modifier = modifier,
        actionText = stringResource(R.string.btn_complete),
        header = {
            EditingRecipeDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                name = editState.name,
                cookingTime = editState.cookingTime,
                photoUrl = editState.photoUrl,
            )
        },
        pages = { page ->
            when (page) {
                RecipeDetailTab.INGREDIENTS -> EditingIngredientTabContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = editState
                )

                RecipeDetailTab.COOKING_STEPS -> EditingStepTabContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = editState
                )

                RecipeDetailTab.RECORD -> EditingRecordTabContent(
                    modifier = Modifier.fillMaxWidth(),
                    state = editState,
                    onEditedRecord = { editedRecord ->
                        editState.record.add(
                            editedRecord
                        )
                    },
                )
            }
        },
        onClickAppbarAction = onCompleteEdit,
    )
}

@Composable
private fun RecipeDetailLayout(
    actionText: String,
    header: @Composable () -> Unit,
    pages: @Composable (RecipeDetailTab) -> Unit,
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
                header
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

@Composable
private fun RecipeDetailHeader(
    name: String,
    cookingTime: Int,
    averageScore: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // TODO:: 이미지 로드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(YorinTheme.colors.black5)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinText(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                style = YorinTheme.typography.title1
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                    contentDescription = null,
                    tint = YorinTheme.colors.black3
                )

                Spacer(modifier = Modifier.size(4.dp))

                YorinText(
                    text = "${cookingTime}분",
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.body2
                )

                Spacer(modifier = Modifier.size(16.dp))

                YorinRatingBar(
                    score = averageScore.toInt()
                )

                Spacer(modifier = Modifier.size(8.dp))

                YorinText(
                    text = "$averageScore",
                    color = YorinTheme.colors.main2,
                    style = YorinTheme.typography.body2
                )
            }
        }
    }
}


@Composable
private fun EditingRecipeDetailHeader(
    name: TextFieldState,
    cookingTime: TextFieldState,
    photoUrl: String?,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // TODO:: 이미지 로드 photoUrl 사용
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(YorinTheme.colors.black5)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            YorinText(
                text = "레시피 이름",
                style = YorinTheme.typography.body2
            )

            YorinTextField(
                state = name,
                placeHolder = "예: 김치찌개"
            )

            YorinText(
                text = "요리 시간(분)",
                style = YorinTheme.typography.body2
            )

            YorinNumberTextField(
                state = cookingTime,
                placeHolder = "30(분)",
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clock),
                        contentDescription = null
                    )
                }
            )
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

    @Composable
    fun toImage(): ImageVector {
        return when (this) {
            INGREDIENTS -> ImageVector.vectorResource(R.drawable.ic_refrigerator)
            COOKING_STEPS -> ImageVector.vectorResource(R.drawable.ic_article)
            RECORD -> ImageVector.vectorResource(R.drawable.ic_chat_bubble)
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    YorinTheme {
        RecipeDetailScreen(
            state = RecipeDetailUiState.Success(recipe = RecipeUiModel.fake()),
            onEditStart = { },
            onAddNewRecord = { },
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun RecipeDetailEditScreenPreview() {
    YorinTheme {
        EditingRecipeDetailScreen(
            editState = RecipeDetailEditState.fake(),
            onCompleteEdit = { },
        )
    }
}