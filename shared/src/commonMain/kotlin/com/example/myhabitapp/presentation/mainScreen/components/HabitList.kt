package com.example.myhabitapp.presentation.mainScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import com.example.myhabitapp.presentation.utils.HabitColor
import com.example.myhabitapp.presentation.utils.HabitIcon
import com.example.myhabitapp.ui.theme.HabitAppTheme
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
fun HabitList(
    modifier: Modifier = Modifier,
    habits: Map<Habit, HabitRecord?>,
    increaseCompletionStatus: (Habit, HabitRecord?) -> Unit,
    decreaseCompletionStatus: (Habit, HabitRecord?) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(habits.keys.toList(), key = { it.id }) { habit ->
            HabitListItem(
                modifier = Modifier.fillMaxWidth(),
                increaseCompletionStatus = { increaseCompletionStatus(habit, habits[habit]) },
                decreaseCompletionStatus = { decreaseCompletionStatus(habit, habits[habit]) },
                habit = habit,
                habitRecord = habits[habit]
            )
        }
    }
}

@Composable
fun HabitListItem(
    modifier: Modifier = Modifier,
    increaseCompletionStatus: () -> Unit,
    decreaseCompletionStatus: () -> Unit,
    habit: Habit,
    habitRecord: HabitRecord?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val checked = when {
            habitRecord == null -> false
            habitRecord.completionProgress >= (habit.numberGoal ?: 1) -> true
            else -> false
        }
        val color = HabitColor.entries[habit.color].color
        Icon(
            imageVector = if (checked) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
            contentDescription = if (checked) "Habit is completed" else "Habit isn't completed",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .background(
                    color = if (checked) MaterialTheme.colorScheme.surface else Color.Transparent,
                    shape = CircleShape
                )
                .size(24.dp)
        )
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = HabitIcon.entries[habit.icon].icon,
                    tint = color,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(color.copy(alpha = 0.5F))
                        .padding(4.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (habit.streak == 0) {
                        Text(
                            text = "It's time to start a streak",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Text(
                            text = "Streak ${habit.streak} ${if (habit.streak == 1) "day" else "days"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                VerticalDivider(modifier = Modifier.height(48.dp))
                Column {
                    val completion = habitRecord?.completionProgress ?: 0
                    Text(
                        text = completion.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable(onClick = { increaseCompletionStatus() })
                            .padding(4.dp)
                    )
                    HorizontalDivider(modifier = Modifier.width(24.dp))
                    Text(
                        text = habit.numberGoal.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable(onClick = { decreaseCompletionStatus() })
                            .padding(4.dp)
                    )
                }
            }
        }

    }
}

@PreviewLightDark
@Composable
fun ListItemPreview() {
    HabitAppTheme {
        HabitListItem(
            increaseCompletionStatus = { },
            decreaseCompletionStatus = { },
            habit = Habit(
                id = 1,
                name = "Start doing something",
                endDate = null,
                repeatDays = listOf(DayOfWeek.THURSDAY),
                reminderTime = null,
                numberGoal = 4,
                color = 2,
                icon = 2,
                streak = 2
            ),
            habitRecord = HabitRecord(
                id = 1,
                date = LocalDate(2026, 8, 15),
                completionProgress = 3,
                habitId = 1
            ),
            modifier = Modifier.padding(4.dp).width(500.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun HabitListPreview() {
    val habits = mapOf(
        Habit(
            id = 1,
            name = "Start doing something",
            endDate = null,
            repeatDays = listOf(DayOfWeek.THURSDAY),
            reminderTime = null,
            numberGoal = 4,
            color = 2,
            icon = 2,
            streak = 2
        ) to HabitRecord(
            id = 1,
            date = LocalDate(2026, 8, 15),
            completionProgress = 3,
            habitId = 1
        ),

        Habit(
            id = 2,
            name = "Read a book",
            endDate = null,
            repeatDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
            reminderTime = null,
            numberGoal = 1,
            color = 2,
            icon = 1,
            streak = 5
        ) to null,

        Habit(
            id = 3,
            name = "Exercise",
            endDate = null,
            repeatDays = listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
            reminderTime = null,
            numberGoal = 3,
            color = 3,
            icon = 3,
            streak = 7
        ) to HabitRecord(
            id = 2,
            date = LocalDate(2026, 8, 15),
            completionProgress = 5,
            habitId = 3
        )
    )

    HabitAppTheme {
        HabitList(
            habits = habits,
            increaseCompletionStatus = { _, _ -> },
            decreaseCompletionStatus = { _, _ -> },
            modifier = Modifier
        )
    }
}