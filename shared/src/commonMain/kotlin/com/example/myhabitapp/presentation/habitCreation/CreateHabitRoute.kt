package com.example.myhabitapp.presentation.habitCreation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.common.CloseButton

@Composable
fun EditCreateHabitRoute(
    viewModel: HabitCreationViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ){
        CloseButton(
            modifier = Modifier.size(48.dp),
            onClick = { onBack() }
        )
        EditCreateHabitScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            onNameChanged = { viewModel.onNameChanged(it) },
            onDateChanged = { viewModel.onDateChanged(it) },
            onReminderChanged = { viewModel.onReminderChanged(it) },
            onGoalAmountChanged = { viewModel.onGoalAmountChanged(it) },
            onToggleWeekDay = { viewModel.onToggleWeekDay(it) },
            toggleSetGoal = { viewModel.toggleSetGoal(it) },
            toggleRepeatDays = { viewModel.toggleRepeatDays(it) },
            toggleGetReminders = { viewModel.toggleGetReminders(it) },
            showDateDialogPicker = { viewModel.showDateDialogPicker() },
            onSaveHabit = { viewModel.onSaveHabit() },
            onDismissDateDialog = { viewModel.onDismissDateDialog() },
            onDismissTimeDialog = { viewModel.onDismissTimeDialog() }
        )
    }
}