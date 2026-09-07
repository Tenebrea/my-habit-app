package com.example.myhabitapp.presentation.habitCreation

import androidx.compose.material3.TimePickerState
import androidx.compose.material3.isInputValid
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.utils.HabitColor
import com.example.myhabitapp.presentation.utils.HabitIcon
import com.example.myhabitapp.presentation.utils.toLocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

class HabitCreationViewModel(
    val repository: HabitRepository,
    val habitId: Int?,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CreateHabitUiState> =
        MutableStateFlow(CreateHabitUiState())
    val uiState = _uiState.asStateFlow()
    private val habit = mutableStateOf<Habit?>(null)

    init {
        viewModelScope.launch {
            habit.value = repository.getHabitById(habitId)
            habit.value?.let { it1 ->
                _uiState.update {
                    it.copy(
                        habitName = it1.name,
                        goalEnabled = it1.numberGoal != null,
                        endDate = it1.endDate,
                        goalNumber = it1.numberGoal,
                        repeatable = it1.repeatDays.isNotEmpty(),
                        repeatDays = it1.repeatDays,
                        getReminders = it1.reminderTime != null,
                        createNote = false
                    )
                }
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

    fun onDateChanged(newDate: Long?) {
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

    fun onGoalAmountChanged(newGoalAmount: String) {
        if (newGoalAmount.all { it.isDigit() }) {
            _uiState.update {
                it.copy(
                    goalNumber = newGoalAmount.toInt()
                )
            }
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
        var nameError = false
        var goalError = false
        var repeatError = false

        if (_uiState.value.habitName == "") {
            nameError = true
        }
        if (
            _uiState.value.goalEnabled &&
            _uiState.value.goalNumber == null &&
            _uiState.value.endDate == null
        ) {
            goalError = true
        }
        if (
            _uiState.value.repeatDays.isEmpty() &&
            _uiState.value.repeatable
        ) {
            repeatError = true
        }
        if (nameError || goalError || repeatError) {
            _uiState.update {
                it.copy(
                    emptyNameError = nameError,
                    emptyGoalsError = goalError,
                    emptyRepeatDaysError = repeatError
                )
            }
        } else {
            val newHabit = habit.value?.copy(
                name = _uiState.value.habitName,
                endDate = _uiState.value.endDate,
                repeatDays = _uiState.value.repeatDays,
                reminderTime = _uiState.value.reminder,
                numberGoal = _uiState.value.goalNumber,
            ) ?: Habit(
                id = 0,
                name = _uiState.value.habitName,
                endDate = _uiState.value.endDate,
                repeatDays = _uiState.value.repeatDays,
                reminderTime = _uiState.value.reminder,
                numberGoal = _uiState.value.goalNumber,
                color = HabitColor.entries.random().ordinal,
                icon = HabitIcon.entries.random().ordinal,
                streak = 0
            )

            viewModelScope
                .launch(Dispatchers.IO) { repository.insertHabit(newHabit) }
            _uiState.update {
                it.copy(
                    habitName = "",
                    goalEnabled = false,
                    endDate = null,
                    goalNumber = null,
                    reminder = null,
                    repeatable = false,
                    repeatDays = emptyList(),
                    getReminders = false,
                    createNote = true,
                    dateDialogShown = false,
                    reminderDialogShown = false,
                    emptyNameError = false,
                    emptyGoalsError = false,
                    emptyRepeatDaysError = false
                )
            }
        }
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

    fun onHabitDelete() {
        viewModelScope.launch {
            repository.deleteHabit(habit.value)
        }
    }
}