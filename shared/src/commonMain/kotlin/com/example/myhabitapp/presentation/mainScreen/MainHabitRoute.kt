package com.example.myhabitapp.presentation.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import org.koin.compose.koinInject

@Composable
fun MainHabitRoute(
    viewModel: MainScreenViewModel = koinInject(),
    imageSize: Dp
) {
    val uiState = viewModel.uiState.collectAsState()
    MainHabitScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        imageSize = imageSize,
        increaseCompletionStatus = { habit, habitRecord -> viewModel.increaseHabitCompletion(habit, habitRecord) },
        decreaseCompletionStatus = { habit, habitRecord -> viewModel.decreaseHabitCompletion(habit, habitRecord) }
    )
}