package com.hyeok.recipebook.presentation.home

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyeok.recipebook.presentation.navigation.Route

fun NavGraphBuilder.homeScreen(
    modifier: Modifier = Modifier
) {
    composable<Route.Home> {
        HomeScreen(
            modifier = modifier
        )
    }
}