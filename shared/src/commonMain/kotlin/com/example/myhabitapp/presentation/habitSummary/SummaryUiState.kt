package com.example.myhabitapp.presentation.habitSummary

import com.example.myhabitapp.domain.models.Habit

data class SummaryUiState(
    val habitsAndProgress: Map<Habit, Float> = emptyMap(),
    val totalPoints: Int = 0
)
