package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.AppTheme

/**
 * @param title
 * @param modifier
 * @param useNavigation
 * @param action
 * @param titleAlignment
 */
@Composable
fun YorinAppbar(
    title: String,
    modifier: Modifier = Modifier,
    useNavigation: Boolean = false,
    titleAlignment: Alignment = Alignment.Center,
    windowInsets: WindowInsets = WindowInsets.systemBars,
    action: @Composable (() -> Unit)? = null,
) {
    val navIcon: @Composable (() -> Unit)? = {
        if (useNavigation) {
            Icon(
                modifier = Modifier,
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                contentDescription = null
            )
        } else {
            null
        }
    }

    BasicYorinAppbar(
        modifier = modifier
            .height(AppbarHeight),
        title = {
            YorinText(
                text = title,
                style = AppTheme.typography.title1,
            )
        },
        titleAlignment = titleAlignment,
        windowInsets = windowInsets,
        leftButton = navIcon,
        rightButton = action,
    )
}

/**
 * 각 요소의 배치와 인셋만 처리하는 Basic Component
 *
 * @param title
 * @param modifier
 * @param titleAlignment
 * @param windowInsets
 * @param leftButton
 * @param rightButton
 */
@Composable
private fun BasicYorinAppbar(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    titleAlignment: Alignment = Alignment.Center,
    windowInsets: WindowInsets = WindowInsets.systemBars,
    leftButton: @Composable (() -> Unit)? = null,
    rightButton: @Composable (() -> Unit)? = null,
) {
    BasicYorinAppbarLayout(
        modifier = modifier
            .padding(horizontal = AppbarHorizontalPadding)
            .windowInsetsPadding(
                windowInsets.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                )
            ),
        title = title,
        titleAlignment = titleAlignment,
        leftButton = leftButton,
        rightButton = rightButton
    )
}

@Composable
private fun BasicYorinAppbarLayout(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    titleAlignment: Alignment = Alignment.Center,
    leftButton: @Composable (() -> Unit)? = null,
    rightButton: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = titleAlignment
    ) {
        title()

        if (leftButton != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                leftButton()
            }
        }

        if (rightButton != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                rightButton()
            }
        }
    }
}


@Preview
@Composable
private fun YorinAppbarPreview() {
    AppTheme {
        YorinAppbar(
            modifier = Modifier
                .background(AppTheme.colors.black7)
                .width(390.dp),
            title = "타이틀",
            useNavigation = true
        )
    }
}

private val AppbarHorizontalPadding = 24.dp
private val AppbarHeight = 46.dp