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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.common.SelectableCircle
import com.example.myhabitapp.presentation.habitCreation.components.SetDateDialog
import com.example.myhabitapp.presentation.habitCreation.components.SetReminderDialog
import com.example.myhabitapp.ui.theme.HabitAppTheme
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
    onDateChanged: (Long?) -> Unit,
    onReminderChanged: (TimePickerState) -> Unit,
    onGoalAmountChanged: (Int) -> Unit,
    onToggleWeekDay: (DayOfWeek) -> Unit,
    toggleSetGoal: (Boolean) -> Unit,
    toggleRepeatDays: (Boolean) -> Unit,
    toggleGetReminders: (Boolean) -> Unit,
    showDateDialogPicker: () -> Unit,
    onSaveHabit: () -> Unit,
    onDismissDateDialog: () -> Unit,
    onDismissTimeDialog: () -> Unit
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
            Box(contentAlignment = Alignment.CenterStart){
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
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            if (uiState.emptyNameError) {
                Text(
                    text = "Name your habit",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Set a goal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Checkbox(
                checked = uiState.goalEnabled,
                onCheckedChange = { toggleSetGoal(it) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { showDateDialogPicker() })
                    .semantics { if (uiState.endDate == null) contentDescription = "Pick end date" }
            ){
                Box(contentAlignment = Alignment.CenterStart) {
                    TextField(
                        value =
                            if (uiState.endDate != null) "${uiState.endDate.day} ${uiState.endDate.month.name} ${uiState.endDate.year}"
                            else "",
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
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .weight(1f)
            ){
                Box(contentAlignment = Alignment.CenterStart) {
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
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        if (uiState.emptyGoalsError) {
            Text(
                text = "Set a goal",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Repeatable",
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
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                SelectableCircle(
                    modifier = Modifier
                        .clickable(onClick = { onToggleWeekDay(dayOfWeek) })
                        .size(48.dp),
                    selected = dayOfWeek in uiState.repeatDays,
                    text = dayOfWeek.name[0].toString(),
                    disabled = !uiState.repeatable
                )
            }
        }
        if (uiState.emptyRepeatDaysError) {
            Text(
                text = "Select repeat days",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Save Habit",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
    if (uiState.dateDialogShown) {
        SetDateDialog(
            modifier = Modifier,
            date = uiState.endDate,
            onDismiss = onDismissDateDialog,
            onDateSelected = { onDateChanged(it) }
        )
    }
    if (uiState.reminderDialogShown) {
        SetReminderDialog(
            modifier = Modifier,
            onDismiss = onDismissTimeDialog,
            onConfirm = { onReminderChanged(it) }
        )
    }
}

@PreviewLightDark
@Composable
fun CreateHabitScreenPreview() {
    HabitAppTheme {
        EditCreateHabitScreen(
            modifier = Modifier.width(400.dp),
            uiState = CreateHabitUiState(),
            onNameChanged = { },
            onDateChanged = { },
            onGoalAmountChanged = { },
            onToggleWeekDay = { },
            toggleSetGoal = { },
            toggleRepeatDays = { },
            toggleGetReminders = { },
            showDateDialogPicker = { },
            onSaveHabit = { },
            onReminderChanged = {  },
            onDismissDateDialog = {  },
            onDismissTimeDialog = {  }
        )
    }
}