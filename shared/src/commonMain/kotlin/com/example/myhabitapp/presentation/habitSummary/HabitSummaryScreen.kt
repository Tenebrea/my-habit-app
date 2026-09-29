package com.example.myhabitapp.presentation.habitSummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.habitSummary.components.HabitProgressBar
import com.example.myhabitapp.presentation.utils.HabitColor
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.points
import org.jetbrains.compose.resources.pluralStringResource

@Composable
fun HabitSummaryScreen(
    uiState: SummaryUiState,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Your progress and insights",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            uiState.habitsAndProgress.forEach { (habit, f) ->
                HabitProgressBarWithLabel(
                    modifier = Modifier.weight(1f),
                    color = HabitColor.entries[habit.color].color,
                    progress = f,
                    vertical = true,
                    habitName = habit.name
                )
            }
        }
        BottomPanel(
            modifier = Modifier.weight(2f),
            points = uiState.totalPoints,
            onShare = onShare
        )

    }
}

@Composable
fun HabitProgressBarWithLabel(
    modifier: Modifier = Modifier,
    color: Color,
    progress: Double,
    vertical: Boolean,
    habitName: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        HabitProgressBar(
            color = color,
            progress = progress.toFloat(),
            vertical = vertical,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Text(
            text = habitName,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
        )
    }
}

@Composable
fun BottomPanel(
    modifier: Modifier = Modifier,
    points: Int,
    onShare: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .clip(shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Points Earned",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "For this week",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        fontStyle = MaterialTheme.typography.displayMedium.fontStyle,
                        color = MaterialTheme.colorScheme.primary)
                    ) {
                        append(points.toString())
                    }
                    append(pluralStringResource(Res.plurals.points, quantity = points))
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Button(
            onClick = onShare,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Share Progress",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}