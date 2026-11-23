package com.hyeok.recipebook.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.hyeok.recipebook.R
import kotlinx.serialization.Serializable

object Route {

    @Serializable
    object Home : NavKey

    @Serializable
    object Recipe {
        @Serializable
        object Recipes : NavKey

        @Serializable
        data class Detail(val recipeId: Long) : NavKey

        @Serializable
        data class Edit(val recipeId: Long?) : NavKey
    }

    @Serializable
    object Ingredient {
        @Serializable
        object Ingredients : NavKey

        @Serializable
        data class Detail(val ingredientId: Long) : NavKey

        @Serializable
        data class Edit(val ingredientId: Long?) : NavKey
    }

    val topLevelRoutes = setOf(
        TopLevelRoute(
            name = R.string.menu_home,
            navKey = Home,
            icon = R.drawable.ic_home
        ),
        TopLevelRoute(
            name = R.string.menu_recipe,
            navKey = Recipe.Recipes,
            icon = R.drawable.ic_chefhat
        ),
        TopLevelRoute(
            name = R.string.menu_ingredient,
            navKey = Ingredient.Ingredients,
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