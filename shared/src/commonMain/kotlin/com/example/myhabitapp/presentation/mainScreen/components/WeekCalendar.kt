package com.example.myhabitapp.presentation.mainScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.common.SelectableCircle
import com.example.myhabitapp.ui.theme.HabitAppTheme
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.next
import kotlinx.datetime.previous

@Composable
fun WeekCalendar(
    modifier: Modifier = Modifier,
    currentDay: LocalDate
) {
    Row(modifier = modifier) {
        DayOfWeek.entries.forEach { dayOfWeek ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = dayOfWeek.name.substring(0, 3),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                val day = when {
                    dayOfWeek.ordinal < currentDay.dayOfWeek.ordinal -> currentDay.previous(dayOfWeek)
                    dayOfWeek.ordinal > currentDay.dayOfWeek.ordinal -> currentDay.next(dayOfWeek)
                    else -> currentDay
                }

                SelectableCircle(
                    modifier = Modifier.size(32.dp),
                    selected = dayOfWeek == currentDay.dayOfWeek,
                    text = day.day.toString()
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun WeekCalendarPreview() {
    HabitAppTheme {
        WeekCalendar(
            currentDay = LocalDate(2026, 1, 1)
        )
    }
}