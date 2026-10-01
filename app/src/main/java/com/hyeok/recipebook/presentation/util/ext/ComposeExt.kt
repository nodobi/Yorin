package com.hyeok.recipebook.presentation.util.ext

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier = if (condition) {
    this.modifier()
} else {
    this
}

fun <T> Modifier.applyIfNotNull(
    value: T?,
    modifier: Modifier.(T) -> Modifier
): Modifier = if(value != null) {
    this.modifier(value)
} else {
    this
}

/**
 * 이 Modifier 가 적용된 영역 내에서 탭 제스처가 발생하면 포커스를 제거하여 키보드를 숨긴다.
 *
 * @param actionBeforeClear 포커스가 제거되기 전 수행해야 하는 동작
 * @return
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.hideKeyboardOnTapOutside(
    actionBeforeClear: (() -> Unit)? = null
): Modifier = composed {
    val focusManager = LocalFocusManager.current

    pointerInput(Unit) {
        detectTapGestures(onTap = { _ ->
            actionBeforeClear?.invoke()
            focusManager.clearFocus()
        })
    }
}