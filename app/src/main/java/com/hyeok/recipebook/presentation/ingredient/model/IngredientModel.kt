package com.hyeok.recipebook.presentation.ingredient.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Stable
data class IngredientModel(
    val name: String,
    val purchaseDate: LocalDate,
    val expirationDate: LocalDate,
    val weight: Int,
    val weightUnit: String,
    val description: String = ""
) {
    val remainExpirationDate: Long =
        (expirationDate.toEpochDay() - LocalDate.now().toEpochDay()).let {
            if (it < 0) 0 else it
        }

    val totalDay: Long =
        expirationDate.toEpochDay() - purchaseDate.toEpochDay().let {
            if (it < 0) 0 else it
        }

    @Composable
    fun toExpirationDateFormat(): String {
        val format = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
        return expirationDate.format(format)
    }

    @Composable
    fun toPurchaseDateFormat(): String {
        val format = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
        return purchaseDate.format(format)
    }

    companion object Companion {
        fun mockState() = IngredientModel(
            name = "돼지고기",
            purchaseDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusDays(10),
            weight = 200,
            weightUnit = "g",
            description = "메모"
        )
    }
}