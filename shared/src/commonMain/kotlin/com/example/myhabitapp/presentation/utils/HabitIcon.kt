package com.example.myhabitapp.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.SetMeal
import androidx.compose.material.icons.outlined.Water
import androidx.compose.ui.graphics.vector.ImageVector
import myhabitapp.shared.generated.resources.Res
import myhabitapp.shared.generated.resources.glass_cup
import org.jetbrains.compose.resources.vectorResource


enum class HabitIcon(val icon: ImageVector) {
    Sport(Icons.AutoMirrored.Outlined.DirectionsBike),
    Health(Icons.AutoMirrored.Filled.DirectionsRun),
    Water(Icons.Outlined.Water),
    Food(Icons.Outlined.Fastfood)
}