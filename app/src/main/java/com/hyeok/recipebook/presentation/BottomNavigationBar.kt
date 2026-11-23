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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import com.hyeok.recipebook.presentation.navigation.Navigator
import com.hyeok.recipebook.presentation.navigation.Route


@Composable
fun BottomNavigationBar(
    navigator: Navigator
) {
    Box {
        Spacer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(YorinTheme.colors.black5)
        )
        YorinBottomBar {
            Route.topLevelRoutes.forEachIndexed { _, route ->
                YorinBottomBarItem(
                    label = stringResource(route.name),
                    imageVector = ImageVector.vectorResource(route.icon),
                    selected = route.navKey == navigator.state.topLevelRoute,
                    onClick = {
                        navigator.navigate(route.navKey)
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

@BackgroundPreview
@Composable
private fun BottomNavigationPreview() {
    YorinTheme {
        YorinBottomBar {
            Route.topLevelRoutes.forEachIndexed { _, route ->
                YorinBottomBarItem(
                    label = stringResource(route.name),
                    imageVector = ImageVector.vectorResource(route.icon),
                    selected = false,
                    onClick = { }
                )
            }
        }
    }
}