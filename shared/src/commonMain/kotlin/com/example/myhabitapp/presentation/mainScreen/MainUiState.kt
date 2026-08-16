package com.example.myhabitapp.presentation.mainScreen

import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import kotlinx.datetime.LocalDate
import java.util.Date

data class MainUiState(
    val shownHabits: Map<Habit, HabitRecord?> = emptyMap(),
    val currentDate: LocalDate = getCurrentDate(),
    val userName: String = ""
)
private fun getCurrentDate(): LocalDate {
    val date = Date()
    return LocalDate(date.year, date.month+1, date.day+1)
}

