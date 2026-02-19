package com.hyeok.recipebook.presentation.util.ext

import androidx.compose.ui.Modifier

fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier = if (condition) {
    then(modifier())
} else {
    this
}

fun <T> Modifier.applyIfNotNull(
    value: T?,
    modifier: Modifier.(T) -> Modifier
): Modifier = if(value != null) {
    then(modifier(value))
} else {
    this
}