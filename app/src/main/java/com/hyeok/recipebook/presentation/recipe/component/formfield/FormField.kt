package com.hyeok.recipebook.presentation.recipe.component.formfield

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.then
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.designsystem.components.TextFieldSize
import com.hyeok.recipebook.designsystem.components.YorinText
import com.hyeok.recipebook.designsystem.components.YorinTextField
import com.hyeok.recipebook.designsystem.components.YorinTextFieldDefault
import com.hyeok.recipebook.designsystem.theme.BackgroundPreview
import com.hyeok.recipebook.designsystem.theme.YorinTheme

@Composable
fun BasicFormField(
    state: FormFieldState,
    modifier: Modifier = Modifier,
    error: (@Composable () -> Unit)? = null,
    placeHolder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hideErrorOnInput: Boolean = true,
    size: TextFieldSize = TextFieldSize.Large,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    backgroundColor: Color = YorinTextFieldDefault.backgroundColor,
    textStyle: TextStyle = YorinTextFieldDefault.textStyle,
    placeHolderColor: Color = YorinTextFieldDefault.placeHolderColor,
    keyboardAction: KeyboardOptions = KeyboardOptions.Default,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    onKeyboardAction: ((() -> Unit) -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        val horizontalPaddingValues = when (size) {
            TextFieldSize.Large -> PaddingValues(horizontal = 8.dp)
            TextFieldSize.Small -> PaddingValues(horizontal = 4.dp)
        }

        val hideErrorTransformation = remember(state) {
            InputTransformation {
                if (asCharSequence().toString() != originalText.toString()) state.hideError()
            }
        }

        val combinedInputTransformation = if (hideErrorOnInput) {
            inputTransformation?.then(hideErrorTransformation) ?: hideErrorTransformation
        } else {
            inputTransformation
        }

        YorinTextField(
            state = state.fieldState,
            placeHolder = placeHolder,
            enabled = enabled,
            readOnly = readOnly,
            size = size,
            lineLimits = lineLimits,
            backgroundColor = backgroundColor,
            textStyle = textStyle,
            placeHolderColor = placeHolderColor,
            keyboardAction = keyboardAction,
            inputTransformation = combinedInputTransformation,
            outputTransformation = outputTransformation,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            onKeyboardAction = onKeyboardAction
        )

        if (error == null) {
            val message = state.errorMessage()
            if (state.shouldShownError && message != null) {
                YorinText(
                    modifier = Modifier
                        .padding(horizontalPaddingValues),
                    text = message,
                    color = YorinTheme.colors.sub1,
                    style = YorinTheme.typography.caption1
                )
            }
        } else {
            error()
        }
    }
}

@BackgroundPreview
@Composable
private fun BasicFormFieldPreview() {
    YorinTheme {
        Column(
            verticalArrangement = spacedBy(20.dp)
        ) {
            val ingredientNameFieldState = rememberFormFieldState(
                initialText = "Initial Text!@#*",
                type = FormType.IngredientName
            ).also { it.revealError() }

            BasicFormField(
                state = ingredientNameFieldState,
                size = TextFieldSize.Large
            )

            // 에러를 활성화하더라도 입력이 유효하면 에러 메세지는 보이지 않는다
            val ingredientNameFieldState2 = rememberFormFieldState(
                initialText = "ValidIngredientName",
                type = FormType.IngredientName
            ).also { it.revealError() }

            BasicFormField(
                state = ingredientNameFieldState2,
                size = TextFieldSize.Large
            )

            val weightQuantityFieldState = rememberFormFieldState(
                initialText = "alphabet",
                type = FormType.WeightQuantity
            ).also { it.revealError() }

            BasicFormField(
                state = weightQuantityFieldState,
                size = TextFieldSize.Large
            )
        }
    }
}