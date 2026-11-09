package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme
import timber.log.Timber


@Composable
fun YorinSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier
            .defaultMinSize(minWidth = SnackbarContainerMinHeight)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        action = snackbarData.visuals.actionLabel?.let {
            @Composable {
                Timber.e("Action 을 사용하는 스낵바는 사용을 자제")
                YorinText(
                    text = it,
                    style = YorinTheme.typography.button2
                )
            }
        },
        dismissAction = if (snackbarData.visuals.withDismissAction) {
            @Composable {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable {
                            snackbarData.dismiss()
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = YorinTheme.colors.black2
                )
            }
        } else null,
        shape = YorinTheme.shapes.small,
        containerColor = YorinTheme.colors.main7,
        contentColor = YorinTheme.colors.black1,
        actionContentColor = YorinTheme.colors.black1,
        dismissActionContentColor = YorinTheme.colors.black1
    ) {
        YorinText(
            text = snackbarData.visuals.message,
            style = YorinTheme.typography.button2
        )
    }
}

private val SnackbarContainerMinHeight = 48.dp

@Preview
@Composable
private fun YorinSnackbarPreview() {
    YorinTheme {
        val state = SnackbarHostState()

        SnackbarHost(state) {
            YorinSnackbar(it)
        }

        LaunchedEffect(Unit) {
            state.showSnackbar("메세지")
        }
    }
}