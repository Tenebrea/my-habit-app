package com.example.myhabitapp.presentation.habitSummary

import com.example.myhabitapp.domain.models.Habit

data class SummaryUiState(
    val habitsAndProgress: Map<Habit, Double> = emptyMap(),
    val totalPoints: Int = 0,
    val shareSheetShown: Boolean = false
)
