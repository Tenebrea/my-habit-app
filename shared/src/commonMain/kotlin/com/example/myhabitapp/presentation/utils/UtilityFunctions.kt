package com.example.myhabitapp.presentation.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun LocalDate.toLong(): Long {
    return this
        .atStartOfDayIn(TimeZone.UTC)
        .toEpochMilliseconds()
}

fun Long.toLocalDate(): LocalDate {
    val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
    return LocalDate(dateTime.year, dateTime.month, dateTime.day)
}