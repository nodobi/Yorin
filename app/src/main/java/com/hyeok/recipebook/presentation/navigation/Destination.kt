package com.hyeok.recipebook.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector = Icons.Default.Build
) {
    ROULETTE("roulette", "Roulette"),
    HOME("home", "Home"),
    MENU("menu", "Menu"),
}