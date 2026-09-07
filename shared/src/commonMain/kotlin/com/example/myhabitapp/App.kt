package com.example.myhabitapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                var route: NavRoutes by remember { mutableStateOf(NavRoutes.Main) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MainHabitRoute(
                        imageSize = 32.dp,
                        onAddHabit = { route = NavRoutes.Create },
                        onEditHabit = { habitId -> route = NavRoutes.Edit(habitId) },
                        modifier = Modifier.weight(1f),
                        viewModel = koinViewModel()
                    )
                    AnimatedVisibility(
                        visible = (route != NavRoutes.Main),
                        enter = expandHorizontally(
                            expandFrom = Alignment.End,
                            animationSpec = tween(durationMillis = 250)
                        ),
                        exit = shrinkHorizontally(
                            shrinkTowards = Alignment.End,
                            animationSpec = tween(durationMillis = 250)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)
                    ) {
                        when (route) {
                            is NavRoutes.Create -> {
                                val viewModel: HabitCreationViewModel =
                                    koinViewModel { parametersOf(null) }
                                EditCreateHabitRoute(
                                    viewModel = viewModel,
                                    onBack = { route = NavRoutes.Main },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                )
                            }
                            is NavRoutes.Edit -> {
                                val viewModel: HabitCreationViewModel =
                                    koinViewModel { parametersOf((route as NavRoutes.Edit).habitId) }
                                EditCreateHabitRoute(
                                    viewModel = viewModel,
                                    onBack = { route = NavRoutes.Main },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                )
                            }
                            else -> {

                            }
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