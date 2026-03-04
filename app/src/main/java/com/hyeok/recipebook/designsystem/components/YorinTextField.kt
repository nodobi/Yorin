package com.hyeok.recipebook.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.R
import com.hyeok.recipebook.designsystem.theme.YorinTheme

enum class TextFieldSize {
    Large,
    Small
}

@Composable
fun YorinTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    size: TextFieldSize = TextFieldSize.Large,
    backgroundColor: Color = YorinTextFieldDefault.backgroundColor,
    textStyle: TextStyle = YorinTextFieldDefault.textStyle,
    placeHolderColor: Color = YorinTextFieldDefault.placeHolderColor,
    keyboardAction: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    onKeyboardAction: ((() -> Unit) -> Unit)? = null
) {
    val innerPadding = when (size) {
        TextFieldSize.Large -> PaddingValues(horizontal = 16.dp)
        TextFieldSize.Small -> PaddingValues(horizontal = 12.dp)
    }
    val height = when (size) {
        TextFieldSize.Large -> 44.dp
        TextFieldSize.Small -> 36.dp
    }
    val shape = when (size) {
        TextFieldSize.Large -> RoundedCornerShape(14.dp)
        TextFieldSize.Small -> RoundedCornerShape(10.dp)
    }

    BasicYorinTextField(
        modifier = modifier,
        state = state,
        shape = shape,
        height = height,
        innerPadding = innerPadding,
        enabled = enabled,
        readOnly = readOnly,
        placeHolder = placeHolder,
        backgroundColor = backgroundColor,
        textStyle = textStyle,
        placeHolderColor = placeHolderColor,
        keyboardAction = keyboardAction,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onKeyboardAction = onKeyboardAction
    )
}

@Composable
private fun BasicYorinTextField(
    state: TextFieldState,
    shape: Shape,
    height: Dp,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeHolder: String = "",
    backgroundColor: Color = YorinTextFieldDefault.backgroundColor,
    textStyle: TextStyle = YorinTextFieldDefault.textStyle,
    placeHolderColor: Color = YorinTextFieldDefault.placeHolderColor,
    keyboardAction: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    onKeyboardAction: ((() -> Unit) -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = keyboardAction,
            onKeyboardAction = onKeyboardAction,
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier
                        .clip(shape)
                        .background(backgroundColor, shape)
                        .fillMaxWidth()
                        .height(height)
                        .padding(innerPadding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()

                        Spacer(
                            modifier = Modifier.width(10.dp)
                        )
                    }

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

                    if (trailingIcon != null) {
                        trailingIcon()
                    }
                }
            }
        )
    }
}

object YorinTextFieldDefault {
    val backgroundColor: Color
        @Composable get() = YorinTheme.colors.black6

    val placeHolderColor: Color
        @Composable get() = YorinTheme.colors.black3

    val textStyle: TextStyle
        @Composable get() = YorinTheme.typography.button1
}

@Preview
@Composable
private fun YorinTextFieldPreview() {
    YorinTheme {
        val state1 = rememberTextFieldState("텍스트")

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            YorinTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(200.dp),
                state = state1,
            )

            YorinTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(200.dp),
                state = state1,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                        contentDescription = null
                    )
                }
            )
            YorinTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(200.dp),
                state = state1,
                size = TextFieldSize.Small
            )

            YorinTextField(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(200.dp),
                state = state1,
                size = TextFieldSize.Small,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_mini),
                        contentDescription = null
                    )
                }
            )
        }
    }
}
