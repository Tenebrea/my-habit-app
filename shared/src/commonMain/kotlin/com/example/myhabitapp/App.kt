package com.example.myhabitapp

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.example.myhabitapp.navigation.HabitNavGraph
import com.example.myhabitapp.navigation.NavRoutes
import com.example.myhabitapp.presentation.habitCreation.EditCreateHabitRoute
import com.example.myhabitapp.presentation.habitCreation.HabitCreationViewModel
import com.example.myhabitapp.presentation.mainScreen.MainHabitRoute
import com.example.myhabitapp.ui.theme.HabitAppTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun App(
    screenSize: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    HabitAppTheme {
        when {
            screenSize
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {

            }

            screenSize
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
                    var route: NavRoutes? by remember { mutableStateOf(NavRoutes.Main) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    MainHabitRoute(
                        imageSize = 32.dp,
                        onAddHabit = { route = NavRoutes.Create },
                        onEditHabit = { habitId -> route = NavRoutes.Edit(habitId) },
                        modifier = Modifier.weight(1f)
                    )
                    when (route) {
                        is NavRoutes.Create -> {
                            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf(null) }
                            EditCreateHabitRoute(
                                viewModel = viewModel,
                                onBack = { route = null },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp)
                            )
                        }
                        is NavRoutes.Edit -> {
                            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf((route as NavRoutes.Edit).habitId) }
                            EditCreateHabitRoute(
                                viewModel = viewModel,
                                onBack = { route = null },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp)
                            )
                        }
                        else -> {

                        }
                    }
                }
            }
            else -> {
                HabitNavGraph()
            }
        }
    }
}