package com.hyeok.recipebook.presentation.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTmeUtil {

    @OptIn(ExperimentalTime::class)
    fun currentLocalDate(
        timeZone: TimeZone
    ): LocalDate = Clock.System.now().toLocalDateTime(timeZone).date
}