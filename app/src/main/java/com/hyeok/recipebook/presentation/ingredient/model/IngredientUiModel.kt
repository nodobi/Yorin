package com.hyeok.recipebook.presentation.ingredient.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class IngredientUiModel(
    val name: String,
    val purchaseDate: LocalDate,
    val expirationDate: LocalDate,
    val weight: Int,
    val weightUnit: String,
    val description: String
) {
    fun formatPurchaseDate(
        format: DateTimeFormat<LocalDate> = LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
        }
    ): String {
        return purchaseDate.format(format)
    }

    fun formatExpirationDate(
        format: DateTimeFormat<LocalDate> = LocalDate.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
        }
    ): String {
        return expirationDate.format(format)
    }

    companion object {
        @OptIn(ExperimentalTime::class)
        fun fake(): IngredientUiModel = IngredientUiModel(
            name = "돼지고기",
            purchaseDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            expirationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.plus(
                5L,
                DateTimeUnit.DAY
            ),
            weight = 200,
            weightUnit = "g",
            description = "메모"
        )
    }
}
