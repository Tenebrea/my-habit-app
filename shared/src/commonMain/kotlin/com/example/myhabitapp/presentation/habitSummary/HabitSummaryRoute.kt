package com.example.myhabitapp.presentation.habitSummary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myhabitapp.presentation.common.CloseButton

@Composable
fun HabitSummaryRoute(
    modifier: Modifier,
    onBack: () -> Unit,
    viewModel: HabitSummaryViewModel
) {
    val uiState = viewModel.uiState.collectAsState().value
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {
        CloseButton(
            modifier = Modifier.size(48.dp),
            onClick = { onBack() }
        )
        HabitSummaryScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState
        )
    }
}