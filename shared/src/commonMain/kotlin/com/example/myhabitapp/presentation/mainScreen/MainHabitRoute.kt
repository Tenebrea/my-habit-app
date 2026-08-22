package com.example.myhabitapp.presentation.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun MainHabitRoute(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinInject(),
    imageSize: Dp,
    onAddHabit: () -> Unit,
    onEditHabit: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            IconButton(
                onClick = { onAddHabit() },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Icon(
                    imageVector = Icons.Default.PlusOne,
                    contentDescription = "Add note",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        MainHabitScreen(
            modifier = Modifier
                .padding(innerPadding),
            uiState = uiState.value,
            imageSize = imageSize,
            increaseCompletionStatus = { habit, habitRecord ->
                viewModel.increaseHabitCompletion(
                    habit,
                    habitRecord
                )
            },
            decreaseCompletionStatus = { habit, habitRecord ->
                viewModel.decreaseHabitCompletion(
                    habit,
                    habitRecord
                )
            },
            enableNotifications = {},
            editHabit = onEditHabit
        )
    }
}