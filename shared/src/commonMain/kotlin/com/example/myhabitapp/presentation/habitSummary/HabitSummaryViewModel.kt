package com.example.myhabitapp.presentation.habitSummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.habitSummary.utils.getAmountOfWeekDays
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HabitSummaryViewModel(
    val repository: HabitRepository,
    val currentDate: LocalDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getHabits().collect { habits ->
                // We get only 4 random habits
                val habitMap = habits.shuffled().take(4).associateWith { habit ->
                    // Maximum possible amount of progress of a habit
                    val goal = getAmountOfWeekDays(
                        date = currentDate,
                        weekDays = habit.repeatDays
                    )
                    // Current progress of a habit
                    val progress = repository.getHabitRecordsByHabitId(habit.id)
                        .filter { it.date.month == currentDate.month }
                        .sumOf { it.completionProgress.toDouble() / (habit.numberGoal ?: 1) }
                    progress / goal
                }
                _uiState.update {
                    it.copy(
                        habitsAndProgress = habitMap
                    )
                }
            }
        }
    }

    fun onShare() {
        return
    }
}