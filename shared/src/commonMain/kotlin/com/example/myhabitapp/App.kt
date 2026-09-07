package com.example.myhabitapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.example.myhabitapp.navigation.HabitNavGraph
import com.example.myhabitapp.ui.theme.HabitAppTheme

@Composable
fun App(
    screenSize: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    HabitAppTheme {
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            HabitNavGraph(screenSize = screenSize)
        }
    }
}