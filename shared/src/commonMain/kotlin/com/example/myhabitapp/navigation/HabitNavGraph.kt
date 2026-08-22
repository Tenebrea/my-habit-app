package com.example.myhabitapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myhabitapp.presentation.habitCreation.EditCreateHabitRoute
import com.example.myhabitapp.presentation.habitCreation.HabitCreationViewModel
import com.example.myhabitapp.presentation.mainScreen.MainHabitRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HabitNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<NavRoutes.Main> {
            MainHabitRoute(
                imageSize = 32.dp,
                onAddHabit = { navController.navigate(NavRoutes.Create) },
                onEditHabit = { habitId -> navController.navigate(NavRoutes.Edit(habitId)) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        composable<NavRoutes.Create> {
            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf(null) }
            EditCreateHabitRoute(
                viewModel = viewModel,
                onBack = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        composable<NavRoutes.Edit> { backStackEntry ->
            val route = backStackEntry.toRoute<NavRoutes.Edit>()
            val viewModel: HabitCreationViewModel = koinViewModel { parametersOf(route.habitId) }
            EditCreateHabitRoute(
                viewModel = viewModel,
                onBack = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}