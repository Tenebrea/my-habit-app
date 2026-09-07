package com.example.myhabitapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.window.core.layout.WindowSizeClass
import com.example.myhabitapp.presentation.habitCreation.EditCreateHabitRoute
import com.example.myhabitapp.presentation.habitCreation.HabitCreationViewModel
import com.example.myhabitapp.presentation.mainScreen.MainHabitRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HabitNavGraph(
    navController: NavHostController = rememberNavController(),
    screenSize: WindowSizeClass
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        composable<NavRoutes.Main> {
            MainHabitRoute(
                imageSize = 32.dp,
                onAddHabit = { navController.navigate(NavRoutes.Create) },
                onEditHabit = { habitId -> navController.navigate(NavRoutes.Edit(habitId)) },
                viewModel = koinViewModel(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        composable<NavRoutes.Create> {
            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf(null) }
            when {
                screenSize.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {

                }
                screenSize.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        MainHabitRoute(
                            imageSize = 32.dp,
                            onAddHabit = { navController.navigate(NavRoutes.Create) },
                            onEditHabit = { habitId -> navController.navigate(NavRoutes.Edit(habitId)) },
                            modifier = Modifier.weight(1f),
                            viewModel = koinViewModel(),
                            fabVisible = false
                        )
                        EditCreateHabitRoute(
                            viewModel = viewModel,
                            onBack = { navController.navigate(NavRoutes.Main) },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .weight(1f)
                                .padding(start = 12.dp)
                        )
                    }
                }
                else -> {
                    EditCreateHabitRoute(
                        viewModel = viewModel,
                        onBack = { navController.navigate(NavRoutes.Main) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
        }
        composable<NavRoutes.Edit> { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoutes.Edit>()
            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf(route.habitId) }
            when {
                screenSize.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> {

                }
                screenSize.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        MainHabitRoute(
                            imageSize = 32.dp,
                            onAddHabit = { navController.navigate(NavRoutes.Create) },
                            onEditHabit = { habitId -> navController.navigate(NavRoutes.Edit(habitId)) },
                            modifier = Modifier.weight(1f),
                            viewModel = koinViewModel(),
                            fabVisible = false
                        )
                        EditCreateHabitRoute(
                            viewModel = viewModel,
                            onBack = { navController.navigate(NavRoutes.Main) },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .weight(1f)
                                .padding(start = 12.dp)
                        )
                    }
                }
                else -> {
                    EditCreateHabitRoute(
                        viewModel = viewModel,
                        onBack = { navController.navigate(NavRoutes.Main) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}