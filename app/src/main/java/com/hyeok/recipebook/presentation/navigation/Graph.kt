package com.hyeok.recipebook.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
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
        TopLevelRoute<Recipe>(
            name = "Recipe",
            route = Recipe,
            icon = Icons.Filled.Home
        ),
        TopLevelRoute<Home>(
            name = "Home",
            route = Home,
            icon = Icons.Filled.Home
        ),
        TopLevelRoute<Ingredient>(
            name = "Ingredient",
            route = Ingredient,
            icon = Icons.Filled.Home
        )
    )
}

data class TopLevelRoute<T: Any> (
    val name: String,
    val route: T,
    val icon: ImageVector
)