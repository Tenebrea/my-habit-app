package com.example.myhabitapp.presentation.habitCreation.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.utils.toLong
import com.example.myhabitapp.ui.theme.HabitAppTheme
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDateDialog(
    modifier: Modifier = Modifier,
    date: LocalDate?,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date?.toLong()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                }
            ) {
                Text(
                    text = "Ok",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        modifier = modifier
    ) {
        DatePicker(state = datePickerState)
    }
}

@PreviewLightDark
@Composable
fun SetDateDialogPreview() {
    HabitAppTheme {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = LocalDate(2026, 8, 19).toLong()
        )
        DatePicker(state = datePickerState)
    }
}