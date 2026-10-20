package com.hyeok.recipebook.presentation.recipe.component.formfield

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.hyeok.recipebook.data.validation.FormType
import com.hyeok.recipebook.data.validation.FormValidator
import com.hyeok.recipebook.presentation.util.ext.toUiText

@Stable
class FormFieldState(
    initialText: String,
    private val type: FormType
) {
    val fieldState = TextFieldState(initialText)

    val text get() = fieldState.text.toString()

    private val error by derivedStateOf { FormValidator.validate(text, type) }

    // 입력의 검증 결과가 유효한지
    val isValid: Boolean get() = error == null

    var shouldShownError by mutableStateOf(false)
        private set

    // 에러를 활성화 하더라도, 검증 결과가 유효하면 에러 메세지는 보이지 않는다.
    fun enableError() {
        shouldShownError = true
    }

    fun disableError() {
        shouldShownError = false
    }

    @Composable
    fun errorMessage(): String? = error?.toUiText()

    companion object {
        fun saver(type: FormType) = listSaver(
            save = {
                listOf(it.text, it.shouldShownError, it.type.toString())
            },
            restore = {
                FormFieldState(it[0] as String, type).apply {
                    shouldShownError = it[1] as Boolean
                }
            }
        )
    }
}

@Composable
fun rememberFormFieldState(
    initialText: String,
    type: FormType
): FormFieldState = rememberSaveable(saver = FormFieldState.saver(type)) {
    FormFieldState(initialText, type)
}