package com.hyeok.recipebook.presentation.util.ext

import androidx.compose.ui.Modifier

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