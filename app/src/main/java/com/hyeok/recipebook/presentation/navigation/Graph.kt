package com.hyeok.recipebook.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hyeok.recipebook.R
import com.hyeok.recipebook.presentation.home.HomeNavKey
import com.hyeok.recipebook.presentation.ingredient.IngredientListNavKey
import com.hyeok.recipebook.presentation.recipe.RecipeListNavKey
import kotlinx.serialization.Serializable

object Route {
    val topLevelRoutes = setOf(
        TopLevelRoute(
            name = R.string.menu_home,
            navKey = HomeNavKey,
            icon = R.drawable.ic_home
        ),
        TopLevelRoute(
            name = R.string.menu_recipe,
            navKey = RecipeListNavKey,
            icon = R.drawable.ic_chefhat
        ),
        TopLevelRoute(
            name = R.string.menu_ingredient,
            navKey = IngredientListNavKey,
            icon = R.drawable.ic_refrigerator
        )
    )
}

data class TopLevelRoute<T : Any>(
    @StringRes
    val name: Int,
    val navKey: T,
    @DrawableRes
    val icon: Int
)