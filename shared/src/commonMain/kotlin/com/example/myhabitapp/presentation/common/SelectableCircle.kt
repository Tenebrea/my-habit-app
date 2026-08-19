package com.example.myhabitapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.ui.theme.HabitAppTheme

@Composable
fun SelectableCircle(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    disabled: Boolean = false
) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = modifier
            .clip(CircleShape)
    ){
        Box(
            Modifier
                .background(
                    if (disabled) MaterialTheme.colorScheme.surface.copy(0.5f)
                    else if (selected) MaterialTheme.colorScheme.onSurface
                    else MaterialTheme.colorScheme.surface
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = if (disabled) MaterialTheme.colorScheme.onSurface.copy(0.5f)
                else if (selected) MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SelectedCirclePreview() {
    HabitAppTheme {
        SelectableCircle(
            modifier = Modifier.size(64.dp),
            selected = true,
            text = "12",
            disabled = false
        )
    }
}

@PreviewLightDark
@Composable
fun NotSelectedCirclePreview() {
    HabitAppTheme {
        SelectableCircle(
            modifier = Modifier.size(64.dp),
            selected = false,
            text = "12",
            disabled = false
        )
    }
}

@PreviewLightDark
@Composable
fun DisabledCirclePreview() {
    HabitAppTheme {
        SelectableCircle(
            modifier = Modifier.size(64.dp),
            selected = false,
            text = "12",
            disabled = true
        )
    }
}