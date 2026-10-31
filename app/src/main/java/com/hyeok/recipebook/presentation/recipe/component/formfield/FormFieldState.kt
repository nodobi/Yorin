package com.hyeok.recipebook.presentation.recipe.component.formfield

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextFieldState.Saver.restore
import androidx.compose.foundation.text.input.TextFieldState.Saver.save
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
class FormFieldState private constructor(
    val fieldState: TextFieldState,
    private val type: FormType
) {
    constructor(
        initialText: String,
        type: FormType
    ) : this(
        fieldState = TextFieldState(initialText),
        type = type
    )

    val text get() = this@FormFieldState.fieldState.text.toString()

    private val error by derivedStateOf { FormValidator.validate(text, type) }

    // 입력의 검증 결과가 유효한지
    val isValid: Boolean get() = error == null

    var shouldShownError by mutableStateOf(false)
        private set

    // 에러를 활성화 하더라도, 검증 결과가 유효하면 에러 메세지는 보이지 않는다.
    fun revealError() {
        shouldShownError = true
    }

    fun hideError() {
        shouldShownError = false
    }

    @Composable
    fun errorMessage(): String? = error?.toUiText()

    companion object {
        val saver = listSaver(
            save = {
                listOf(
                    save(it.fieldState)!!,
                    it.type.name,
                    it.shouldShownError,
                )
            },
            restore = {
                FormFieldState(
                    fieldState = restore(it[0])!!,
                    type = FormType.valueOf(it[1] as String)
                ).apply {
                    shouldShownError = it[2] as Boolean
                }
            }
        )
    }
}

@Composable
fun rememberFormFieldState(
    initialText: String,
    type: FormType
): FormFieldState = rememberSaveable(saver = FormFieldState.saver) {
    FormFieldState(initialText, type)
}