package com.example.myhabitapp.presentation.habitCreation

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class CreateHabitUiState(
    val habitName: String = "",
    val goalEnabled: Boolean = false,
    val endDate: LocalDate? = null,
    val goalNumber: Int? = null,
    val repeatable: Boolean = false,
    val repeatDays: List<DayOfWeek> = emptyList(),
    val getReminders: Boolean = false,
    val createNote: Boolean = true,
    val dialogShown: Boolean = false
)
