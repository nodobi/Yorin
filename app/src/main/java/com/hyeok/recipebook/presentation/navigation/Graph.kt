package com.hyeok.recipebook.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hyeok.recipebook.R
import kotlinx.serialization.Serializable

object Route {

    @Serializable
    object Home

    @Serializable
    object Recipe

    @Serializable
    object Ingredient

    // TODO::이미지 수정
    val topLevelRoutes = listOf(
        TopLevelRoute<Home>(
            name = R.string.menu_home,
            route = Home,
            icon = R.drawable.ic_home
        ),
        TopLevelRoute<Recipe>(
            name = R.string.menu_recipe,
            route = Recipe,
            icon = R.drawable.ic_chefhat
        ),
        TopLevelRoute<Ingredient>(
            name = R.string.menu_ingredient,
            route = Ingredient,
            icon = R.drawable.ic_refrigerator
        )
    )
}

data class TopLevelRoute<T: Any> (
    @StringRes
    val name: Int,
    val route: T,
    @DrawableRes
    val icon: Int
)