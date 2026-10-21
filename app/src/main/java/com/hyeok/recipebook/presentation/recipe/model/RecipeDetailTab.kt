package com.hyeok.recipebook.presentation.recipe.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.hyeok.recipebook.R

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