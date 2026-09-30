package com.example.myhabitapp.presentation.habitSummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.habitSummary.utils.getAmountOfWeekDays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class HabitSummaryViewModel(
    val repository: HabitRepository,
    val currentDate: LocalDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getHabits()
                .map { it.shuffled().take(4).sortedBy { habit -> habit.id } }
                .flatMapLatest { habits ->
                    if (habits.isEmpty()) {
                        flowOf(emptyMap())
                    } else {
                        val progressFlows = habits.map { habit ->
                            repository.getHabitRecordsFlowByHabitId(habit.id)
                                .map { records ->
                                    val goal = getAmountOfWeekDays(currentDate, habit.repeatDays)
                                        .toDouble()

                                    val progress = records
                                        .filter { it.date.month == currentDate.month }
                                        .sumOf {
                                            val progress = it.completionProgress.toDouble()/(habit.numberGoal ?: 1)
                                            if (progress >= 1.0) 1.0 else progress
                                        }
                                    habit to if (goal>0) progress/goal else 0.0
                                }
                        }
                        combine(progressFlows) { pairs ->
                            pairs.toMap()
                        }
                    }
                }.collect { habitMap ->
                    _uiState.update { state ->
                        state.copy(
                            habitsAndProgress = habitMap,
                            totalPoints = habitMap.values.sumOf { it*100 }.toInt()
                        )
                    }
                }
        }
    }
}