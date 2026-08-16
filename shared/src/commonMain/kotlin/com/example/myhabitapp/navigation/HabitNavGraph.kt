package com.example.myhabitapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myhabitapp.presentation.mainScreen.MainHabitRoute

@Composable
fun HabitNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Main.name
    ) {
        composable(route = NavRoutes.Main.name) {

        }
    }
}