package com.hyeok.recipebook.presentation.ingredient.model

import com.hyeok.recipebook.data.database.model.IngredientModel
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

// REVIEW:: OwnedIngredientUiModel 로 대체하는 것 고려
data class IngredientUiModel(
    val id: Long,
    val name: String,
    val purchaseDate: LocalDate,
    val expirationDate: LocalDate?,
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
    ): String? {
        return expirationDate?.format(format)
    }

    companion object {
        @OptIn(ExperimentalTime::class)
        fun fake(): IngredientUiModel = IngredientUiModel(
            id = 0,
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

        @OptIn(ExperimentalTime::class)
        fun empty(): IngredientUiModel = IngredientUiModel(
            id = -1,
            name = "",
            purchaseDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            expirationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            weight = 0,
            weightUnit = "",
            description = "",
        )
    }
}

fun IngredientModel.toUiModel(): IngredientUiModel =
    IngredientUiModel(
        id = id,
        name = name,
        purchaseDate = LocalDate.fromEpochDays(purchaseDay),
        expirationDate = expirationDay?.let { LocalDate.fromEpochDays(it) },
        weight = weight,
        weightUnit = weightUnit,
        description = description
    )

fun IngredientUiModel.toModel() =
    IngredientModel(
        id = id,
        name = name,
        purchaseDay = purchaseDate.toEpochDays(),
        expirationDay = expirationDate?.toEpochDays(),
        weight = weight,
        weightUnit = weightUnit,
        description = description
    )