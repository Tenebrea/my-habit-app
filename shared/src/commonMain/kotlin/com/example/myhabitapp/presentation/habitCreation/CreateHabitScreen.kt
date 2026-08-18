package com.example.myhabitapp.presentation.habitCreation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.common.SelectableCircle
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.calendar
import org.jetbrains.compose.resources.painterResource

@Composable
fun EditCreateHabitScreen(
    modifier: Modifier = Modifier,
    uiState: CreateHabitUiState,
    onNameChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onGoalAmountChanged: (Int) -> Unit,
    onToggleWeekDay: (DayOfWeek) -> Unit,
    toggleSetGoal: (Boolean) -> Unit,
    toggleRepeatDays: (Boolean) -> Unit,
    toggleGetReminders: (Boolean) -> Unit,
    showDateDialogPicker: () -> Unit,
    onSaveHabit: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = if (uiState.createNote) "New habit" else "Edit habit",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(Res.drawable.calendar),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = "Name of the habit",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1
        )
        Card(
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            TextField(
                value = uiState.habitName,
                onValueChange = { onNameChanged(it) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.habitName == "") {
                Text(
                    text = "Habit name",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Set a goal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Checkbox(
                checked = uiState.goalEnabled,
                onCheckedChange = { toggleSetGoal(!it) }
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { showDateDialogPicker() })
                    .semantics { if (uiState.endDate == null) contentDescription = "Pick end date" }
            ){
                TextField(
                    value = uiState.habitName,
                    onValueChange = {},
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.EditCalendar,
                            contentDescription = null
                        )
                    }
                )
                if (uiState.endDate == null) {
                    Text(
                        text = "Add date",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .weight(1f)
            ){
                TextField(
                    value = if (uiState.goalNumber != null) uiState.goalNumber.toString() else "",
                    onValueChange = { onGoalAmountChanged(it.toInt()) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                )
                if (uiState.goalNumber == null) {
                    Text(
                        text = "Add amount",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Set a goal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Checkbox(
                checked = uiState.repeatable,
                onCheckedChange = { toggleRepeatDays(it) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                SelectableCircle(
                    modifier = Modifier.clickable(onClick = { onToggleWeekDay(dayOfWeek) }),
                    selected = dayOfWeek in uiState.repeatDays,
                    text = dayOfWeek.name[0].toString()
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Get reminders",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Switch(
                checked = uiState.getReminders,
                onCheckedChange = { toggleGetReminders(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
        Button(
            onClick = { onSaveHabit() },
            shape = RoundedCornerShape(45.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Save Habit",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}