package com.example.myhabitapp.presentation.habitCreation

import androidx.compose.material3.TimePickerState
import androidx.compose.material3.isInputValid
import androidx.lifecycle.ViewModel
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.utils.toLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.String

class HabitCreationViewModel(
    val habit: Habit?,
    val repository: HabitRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<CreateHabitUiState> = MutableStateFlow(CreateHabitUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (habit != null) {
            _uiState.update {
                it.copy(
                    habitName = habit.name,
                    goalEnabled = habit.numberGoal != null,
                    endDate = habit.endDate,
                    goalNumber = habit.numberGoal,
                    repeatable = habit.repeatDays.isNotEmpty(),
                    repeatDays = habit.repeatDays,
                    getReminders = habit.reminderTime != null,
                    createNote = false
                )
            }
        }
    }

    fun onNameChanged(newName: String) {
        _uiState.update {
            it.copy(
                habitName = newName,
                emptyNameError = false
            )
        }
    }
    fun onDateChanged(newDate: Long?){
        if (newDate != null) {
            val date = newDate.toLocalDate()
            _uiState.update {
                it.copy(
                    endDate = date
                )
            }
            onDismissDateDialog()
        }
    }
    fun onReminderChanged(newReminder: TimePickerState) {
        if (newReminder.isInputValid) {
            val time = LocalTime(hour = newReminder.hour, minute = newReminder.minute)
            _uiState.update {
                it.copy(
                    reminder = time,
                    reminderDialogShown = false
                )
            }
        }
    }
    fun onGoalAmountChanged(newGoalAmount: Int) {
        _uiState.update {
            it.copy(
                goalNumber = newGoalAmount
            )
        }
    }
    fun onToggleWeekDay(day: DayOfWeek) {
        if (day in _uiState.value.repeatDays) {
            val newList = _uiState.value
                .repeatDays
                .toMutableList()
            newList.remove(day)
            _uiState.update {
                it.copy(
                    repeatDays = newList
                )
            }
        } else {
            val newList = _uiState.value
                .repeatDays
                .toMutableList()
            newList.add(day)
            _uiState.update {
                it.copy(
                    repeatDays = newList
                )
            }
        }
    }
    fun toggleSetGoal(newState: Boolean) {
        _uiState.update {
            it.copy(
                goalEnabled = newState
            )
        }
    }
    fun toggleRepeatDays(newState: Boolean) {
        _uiState.update {
            it.copy(
                repeatable = newState
            )
        }
    }
    fun toggleGetReminders(newState: Boolean) {
        _uiState.update {
            it.copy(
                getReminders = newState,
                reminderDialogShown = true
            )
        }
    }
    fun showDateDialogPicker() {
        _uiState.update {
            it.copy(
                dateDialogShown = true
            )
        }
    }
    fun onSaveHabit() {

    }
    fun onDismissDateDialog() {
        _uiState.update {
            it.copy(
                dateDialogShown = false
            )
        }
    }
    fun onDismissTimeDialog() {
        _uiState.update {
            it.copy(
                reminderDialogShown = false,
                getReminders = false
            )
        }
    }
}