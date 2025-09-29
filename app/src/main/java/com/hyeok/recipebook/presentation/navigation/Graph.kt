package com.hyeok.recipebook.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

object Route {

    @Serializable
    object Home

    @Serializable
    object Menu

    @Serializable
    object Roulette

    // TODO::이미지 수정
    val topLevelRoutes = listOf(
        TopLevelRoute<Menu>(
            name = "Menu",
            route = Menu,
            icon = Icons.Filled.Home
        ),
        TopLevelRoute<Home>(
            name = "Home",
            route = Home,
            icon = Icons.Filled.Home
        ),
        TopLevelRoute<Roulette>(
            name = "Roulette",
            route = Roulette,
            icon = Icons.Filled.Home
        )
    )
}

data class TopLevelRoute<T: Any> (
    val name: String,
    val route: T,
    val icon: ImageVector
)