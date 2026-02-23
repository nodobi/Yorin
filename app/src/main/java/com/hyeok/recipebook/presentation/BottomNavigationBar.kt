package com.hyeok.recipebook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.navigation.Route


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    Box() {
        Spacer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(YorinTheme.colors.black5)
        )
        YorinBottomBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            Route.topLevelRoutes.forEachIndexed { idx, route ->
                YorinBottomBarItem(
                    label = stringResource(route.name),
                    imageVector = ImageVector.vectorResource(route.icon),
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                    onClick = {
                        navController.navigate(route.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun YorinBottomBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    val insets = windowInsets.only(
        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
    )

    Box(
        modifier = modifier
            .windowInsetsPadding(insets)
            .fillMaxWidth()
            .height(BottomBarHeight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup()
        ) {
            content()
        }
    }
}

@Composable
private fun RowScope.YorinBottomBarItem(
    label: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val contentColor = if (selected) YorinTheme.colors.main1 else YorinTheme.colors.black2

    Box(
        modifier = modifier
            .fillMaxHeight()
            .weight(1f)
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = label,
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            YorinText(
                text = label,
                color = contentColor,
                style = YorinTheme.typography.caption2
            )
        }
    }
}

private val BottomBarHeight = 72.dp

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    YorinBottomBar {
        Route.topLevelRoutes.forEachIndexed { idx, route ->
            YorinBottomBarItem(
                label = stringResource(route.name),
                imageVector = ImageVector.vectorResource(route.icon),
                selected = false,
                onClick = { }
            )
        }
    }
}