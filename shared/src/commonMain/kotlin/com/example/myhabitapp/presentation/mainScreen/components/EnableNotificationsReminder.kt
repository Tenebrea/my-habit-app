package com.example.myhabitapp.presentation.mainScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.ui.theme.HabitAppTheme
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.notification_bell
import org.jetbrains.compose.resources.painterResource

@Composable
fun EnableNotificationsReminder(
    modifier: Modifier = Modifier,
    enableNotifications: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults
            .cardColors(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Set the reminder",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = """
                        Never miss your morning routine!
                        Set a reminder to stay on track
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { enableNotifications() },
                    shape = RoundedCornerShape(45.dp),
                    colors = ButtonDefaults
                        .buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = "Set Now",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Image(
                painter = painterResource(Res.drawable.notification_bell),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun EnableNotificationsReminderPreview() {
    HabitAppTheme {
        EnableNotificationsReminder(
            modifier = Modifier.width(400.dp),
            enableNotifications = {}
        )
    }
}