package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun YorinSearchbar(
    state: TextFieldState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = YorinTheme.colors.black6,
    textStyle: TextStyle = YorinTheme.typography.body5,
    placeHolder: String? = null,
    placeHolderColor: Color = YorinTheme.colors.black3,
    iconTint: Color = YorinTheme.colors.black3,
) {
    YorinSearchBarLayout(
        state = state,
        modifier = modifier
            .height(DefaultSearchBarHeight),
        backgroundColor = backgroundColor,
        textStyle = textStyle,
        placeHolder = placeHolder ?: "",
        placeHolderColor = placeHolderColor,
        iconTint = iconTint,
        onSearch = {
            onSearch(state.text.toString())
        }
    )
}

@Composable
private fun YorinSearchBarLayout(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    textStyle: TextStyle = TextStyle.Default,
    placeHolder: String = "",
    placeHolderColor: Color = Color.Unspecified,
    iconTint: Color,
    onSearch: () -> Unit
) {
    val spaceBetweenIcon = 8.dp

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            textStyle = textStyle,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            onKeyboardAction = { performDefaultAction ->
                onSearch()
                performDefaultAction()
            },
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(backgroundColor)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spaceBetweenIcon)
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.text.isEmpty()) {
                            YorinText(
                                text = placeHolder,
                                style = textStyle,
                                color = placeHolderColor
                            )
                        }
                        innerTextField()
                    }

                    Icon(
                        modifier = Modifier
                            .clickable {
                                onSearch()
                            },
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = iconTint
                    )
                }
            }
        )
    }
}

private val DefaultSearchBarHeight = 44.dp

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun YorinSearchBarPreview() {
    YorinTheme {
        val state = rememberTextFieldState("")
        YorinSearchbar(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onSearch = {}
        )
    }
}