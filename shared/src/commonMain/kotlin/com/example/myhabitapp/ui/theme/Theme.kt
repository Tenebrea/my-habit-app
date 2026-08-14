package com.example.myhabitapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Orange,
    onPrimary = Color.White,

    primaryContainer = OrangeLight,
    onPrimaryContainer = Brown,

    secondary = Green,
    onSecondary = Color.White,

    secondaryContainer = GreenLight,
    onSecondaryContainer = Brown,

    tertiary = Pink,
    onTertiary = Color.White,

    tertiaryContainer = PinkLight,
    onTertiaryContainer = Brown,

    background = Cream,
    onBackground = Color.Black,

    surface = WarmWhite,
    onSurface = Color(0xFF0C0C0C),

    outline = BrownLight,

    error = Error,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = Brown,

    primaryContainer = OrangeDark,
    onPrimaryContainer = WarmWhite,

    secondary = Green,
    onSecondary = Brown,

    secondaryContainer = Brown,
    onSecondaryContainer = GreenLight,

    tertiary = Pink,
    onTertiary = Brown,

    tertiaryContainer = Brown,
    onTertiaryContainer = PinkLight,

    background = Color(0xFF171412),
    onBackground = WarmWhite,

    surface = Color(0xFF0C0C0C),
    onSurface = WarmWhite,

    outline = Color(0xFF9D8982),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun HabitAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        },
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}