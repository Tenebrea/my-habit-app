package com.example.myhabitapp

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.example.myhabitapp.presentation.mainScreen.MainHabitRoute
import com.example.myhabitapp.ui.theme.HabitAppTheme

@Composable
fun App(
    screenSize: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    HabitAppTheme {
        MainHabitRoute(
            imageSize = 32.dp
        )
        when {
            screenSize
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {

            }
            screenSize
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {

            }
            else -> {

            }
        }
    }
}