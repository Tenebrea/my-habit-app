package com.example.myhabitapp.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import com.example.myhabitapp.domain.repositories.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class MainScreenViewModel(
    val repository: HabitRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .getHabits()
                .map { habits ->
                    habits.associateWith { habit ->
                        val result: List<HabitRecord> =
                            repository.getHabitRecordsByHabitId(habit.id)
                        result.find { it.date == _uiState.value.currentDate }
                    }
                }
                .collect { habits ->
                    _uiState.update {
                        it.copy(
                            shownHabits = habits
                        )
                    }
                }
        }
    }

    fun increaseHabitCompletion(
        habit: Habit,
        habitRecord: HabitRecord?
    ) {
        val newHabitRecord = habitRecord
            ?.copy(completionProgress = habitRecord.completionProgress + 1)
            ?: HabitRecord(
                id = 0,
                date = _uiState.value.currentDate,
                completionProgress = 1,
                habitId = habit.id
            )
        val newShownHabits = _uiState.value.shownHabits + (habit to newHabitRecord)
        _uiState.update {
            it.copy(
                shownHabits = newShownHabits
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHabitRecord(newHabitRecord)
            if ((habit.numberGoal ?: 1) == newHabitRecord.completionProgress) addStreak(habit)
        }
    }

    fun decreaseHabitCompletion(
        habit: Habit,
        habitRecord: HabitRecord?
    ) {
        if (habitRecord == null) return

        if (habitRecord.completionProgress <= 0) {
            return
        }
        val newHabitRecord = habitRecord
            .copy(completionProgress = habitRecord.completionProgress - 1)
        val newShownHabits = _uiState.value.shownHabits + (habit to newHabitRecord)
        _uiState.update {
            it.copy(
                shownHabits = newShownHabits
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHabitRecord(newHabitRecord)
            if ((habit.numberGoal ?: 1) - 1 == newHabitRecord.completionProgress) {
                undoStreak(habit)
            }
        }
    }

    private suspend fun addStreak(habit: Habit) {
        val newHabit = habit.copy(streak = habit.streak+1)
        repository.insertHabit(newHabit)
    }

    private suspend fun undoStreak(habit: Habit) {
        val lastRecord = repository.getHabitRecordsByHabitId(habit.id).maxByOrNull { it.date }
        if (lastRecord == null) return

        if (lastRecord.date == _uiState.value.currentDate && habit.streak > 0) {
            repository.insertHabit(habit.copy(streak = habit.streak-1))
        }
    }
}