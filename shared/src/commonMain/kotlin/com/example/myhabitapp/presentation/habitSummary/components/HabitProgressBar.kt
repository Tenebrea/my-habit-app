package com.example.myhabitapp.presentation.habitSummary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.ui.theme.HabitAppTheme

@Composable
fun HabitProgressBar(
    modifier: Modifier = Modifier,
    color: Color,
    progress: Float,
    vertical: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(45.dp))
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        if (vertical) {
            Box(
                modifier = Modifier
                    .background(color = color)
                    .fillMaxWidth()
                    .fillMaxHeight(progress)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .background(color = color)
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
            ) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterStart).padding(16.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HabitProgressBarVerticalPreview() {
    HabitAppTheme {
        HabitProgressBar(
            color = MaterialTheme.colorScheme.primary,
            progress = 0.6f,
            vertical = true,
            modifier = Modifier.fillMaxHeight().width(60.dp)
        )
    }
}

@PreviewLightDark
@Composable
fun HabitProgressBarHorizontalPreview() {
    HabitAppTheme {
        HabitProgressBar(
            color = MaterialTheme.colorScheme.primary,
            progress = 0.6f,
            vertical = false,
            modifier = Modifier.fillMaxWidth().height(60.dp)
        )
    }
}