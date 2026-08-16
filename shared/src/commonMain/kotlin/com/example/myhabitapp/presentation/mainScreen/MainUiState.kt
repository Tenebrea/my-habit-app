package com.example.myhabitapp.presentation.mainScreen

import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinMonth
import java.time.LocalDateTime
import java.util.Date

data class MainUiState(
    val shownHabits: Map<Habit, HabitRecord?> = emptyMap(),
    val currentDate: LocalDate = getCurrentDate(),
    val userName: String = ""
)
private fun getCurrentDate(): LocalDate {
    val today = LocalDateTime.now()
    return LocalDate(today.year, today.month.toKotlinMonth(), today.dayOfMonth)
}

