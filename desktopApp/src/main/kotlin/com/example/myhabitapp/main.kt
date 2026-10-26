package com.example.myhabitapp

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import com.example.myhabitapp.di.initKoin

fun main() = application {
    initKoin()
    val trayState = rememberTrayState()
    val trayIcon = painterResource("icon.webp")

    Tray(
        icon = trayIcon,
        state = trayState,
        tooltip = "MyHabitApp",
        onAction = {

        }
    ) {
        Item("Quit", onClick = ::exitApplication)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyHabitApp",
    ) {

        App()
    }
}