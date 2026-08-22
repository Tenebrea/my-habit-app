package com.example.myhabitapp.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import com.example.myhabitapp.presentation.mainScreen.components.EnableNotificationsReminder
import com.example.myhabitapp.presentation.mainScreen.components.HabitList
import com.example.myhabitapp.presentation.mainScreen.components.UserDetails
import com.example.myhabitapp.presentation.mainScreen.components.WeekCalendar
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.placeholder_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainHabitScreen(
    modifier: Modifier = Modifier,
    uiState: MainUiState,
    imageSize: Dp = 32.dp,
    increaseCompletionStatus: (Habit, HabitRecord?) -> Unit,
    decreaseCompletionStatus: (Habit, HabitRecord?) -> Unit,
    enableNotifications: () -> Unit,
    editHabit: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserDetails(
            modifier = Modifier.fillMaxWidth(),
            userName = uiState.userName,
            image = painterResource(Res.drawable.placeholder_image),
            date = uiState.currentDate,
            imageSize = imageSize
        )
        WeekCalendar(
            modifier = Modifier.fillMaxWidth(),
            currentDay = uiState.currentDate
        )
        if (!uiState.notificationsEnabled) {
            EnableNotificationsReminder(
                modifier = Modifier.fillMaxWidth(),
                enableNotifications = { enableNotifications() }
            )
        }
        Text(
            text = "Daily routine",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        HabitList(
            modifier = Modifier.fillMaxWidth(),
            habits = uiState.shownHabits,
            increaseCompletionStatus = { habit, record -> increaseCompletionStatus(habit, record) },
            decreaseCompletionStatus = { habit, record -> decreaseCompletionStatus(habit, record) },
            editHabit = editHabit
        )
    }
}