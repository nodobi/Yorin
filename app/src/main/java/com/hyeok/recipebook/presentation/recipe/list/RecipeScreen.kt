package com.hyeok.recipebook.presentation.recipe.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.components.YorinAppbar
import com.hyeok.recipebook.designsystem.components.YorinCard
import com.hyeok.recipebook.designsystem.components.YorinRadioButton
import com.hyeok.recipebook.designsystem.components.YorinSearchbar
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun RecipeRoute(
    recipesUiState: RecipesUiState,
    searchQueryState: TextFieldState,
    onAddRecipe: () -> Unit,
    onClickRecipe: (Long) -> Unit,
    onSelectFilter: (RecipeSearchFilter) -> Unit,
) {

    RecipeScreen(
        modifier = Modifier.fillMaxSize(),
        recipesUiState = recipesUiState,
        searchQueryState = searchQueryState,
        onAddRecipe = onAddRecipe,
        onClickRecipe = onClickRecipe,
        onSelectFilter = onSelectFilter
    )
}

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipesUiState: RecipesUiState,
    searchQueryState: TextFieldState,
    onAddRecipe: () -> Unit = {},
    onClickRecipe: (Long) -> Unit = {},
    onSelectFilter: (RecipeSearchFilter) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        YorinAppbar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.recipe_title),
            titleAlignment = Alignment.CenterStart,
            action = {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onAddRecipe()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                    contentDescription = null,
                )
            }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YorinSearchbar(
                modifier = Modifier.fillMaxWidth(),
                state = searchQueryState,
                onSearch = {
                    // Debounce 처리했으므로 이벤트 등록할 필요는 없다.
                }
            )

            SearchFilters(
                modifier = Modifier.fillMaxWidth(),
                selectedFilter = recipesUiState.selectedSearchFilter,
                onSelectFilter = onSelectFilter
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(recipesUiState.recipes) { recipeUiState ->
                    RecipeCard(
                        name = recipeUiState.name,
                        photoUri = recipeUiState.photoUri,
                        ingredients = recipeUiState.ingredients,
                        averageScore = recipeUiState.averageScore,
                        cookingTime = recipeUiState.cookingTime,
                        onClick = {
                            onClickRecipe(recipeUiState.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchFilters(
    modifier: Modifier = Modifier,
    selectedFilter: RecipeSearchFilter,
    onSelectFilter: (RecipeSearchFilter) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        YorinRadioButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.recipe_search_filter_recipe_title),
            selected = selectedFilter == RecipeSearchFilter.BY_RECIPE,
            onClick = { onSelectFilter(RecipeSearchFilter.BY_RECIPE) }
        )
        YorinRadioButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.recipe_search_filter_ingredient_title),
            selected = selectedFilter == RecipeSearchFilter.BY_INGREDIENT,
            onClick = { onSelectFilter(RecipeSearchFilter.BY_INGREDIENT) }
        )
    }
}

@Composable
private fun RecipeCard(
    name: String,
    photoUri: String?,
    ingredients: List<String>,
    averageScore: Float,
    cookingTime: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val formattedIngredients = remember(ingredients) {
        ingredients.joinToString(", ")
    }

    YorinCard(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (photoUri == null) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(YorinTheme.colors.black6),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_insert_photo),
                        tint = YorinTheme.colors.black4,
                        contentDescription = null
                    )
                }
            } else {
                // TODO:: URI 이미지 추가
            }

            Column {
                YorinText(
                    text = name,
                    style = YorinTheme.typography.caption1
                )

                Spacer(modifier = Modifier.height(8.dp))

                YorinText(
                    text = formattedIngredients,
                    color = YorinTheme.colors.black3,
                    style = YorinTheme.typography.caption2
                )

                Spacer(modifier = Modifier.height(4.dp))

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
                        text = "$averageScore",
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
                        text = "${cookingTime}분",
                        color = YorinTheme.colors.black3,
                        style = YorinTheme.typography.caption2
                    )
                }
            }
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
private fun RecipeScreenPreview() {
    YorinTheme {
        RecipeScreen(
            recipesUiState = RecipesUiState(
                recipes = listOf(
                    RecipeItemUiModel(
                        id = 1,
                        name = "김치찌개",
                        photoUri = null,
                        ingredients = listOf("김치, 두부"),
                        averageScore = 4.2f,
                        cookingTime = 30
                    )
                )
            ),
            searchQueryState = rememberTextFieldState()
        )
    }
}

enum class RecipeSearchFilter {
    BY_RECIPE,
    BY_INGREDIENT,
}