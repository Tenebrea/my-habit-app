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
                        repository.getHabitRecordsByHabitId(habit.id).maxBy { it.date }
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
        val newShownHabits = _uiState.value.shownHabits.toMutableMap()
        newShownHabits[habit] = newHabitRecord
        _uiState.update {
            it.copy(
                shownHabits = newShownHabits
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHabitRecord(newHabitRecord)
        }
    }

    fun decreaseHabitCompletion(
        habit: Habit,
        habitRecord: HabitRecord?
    ) {
        if (habitRecord != null) {
            val newHabitRecord = habitRecord
                .copy(completionProgress = habitRecord.completionProgress + 1)
            val newShownHabits = _uiState.value.shownHabits.toMutableMap()
            newShownHabits[habit] = newHabitRecord
            _uiState.update {
                it.copy(
                    shownHabits = newShownHabits
                )
            }
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertHabitRecord(newHabitRecord)
            }
        }
    }
}