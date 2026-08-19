package com.example.myhabitapp.presentation.habitCreation

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class CreateHabitUiState(
    val habitName: String = "",
    val goalEnabled: Boolean = false,
    val endDate: LocalDate? = null,
    val goalNumber: Int? = null,
    val reminder: LocalTime? = null,
    val repeatable: Boolean = false,
    val repeatDays: List<DayOfWeek> = emptyList(),
    val getReminders: Boolean = false,
    val createNote: Boolean = true,
    val dateDialogShown: Boolean = false,
    val reminderDialogShown: Boolean = false,

    val emptyNameError: Boolean = false,
    val emptyGoalsError: Boolean = false,
    val emptyRepeatDaysError: Boolean = false,
)
