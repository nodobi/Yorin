package com.hyeok.recipebook.presentation.util.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun Long.toLocalDate(
    timeZone: TimeZone = TimeZone.UTC
): LocalDate =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date


fun LocalDate.toEpochMilliseconds(): Long =
    this.toEpochDays() * 86400000