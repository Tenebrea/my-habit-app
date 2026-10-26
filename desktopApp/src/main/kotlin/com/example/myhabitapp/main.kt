package com.example.myhabitapp

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myhabitapp.di.initKoin

fun main() = application {
    val koinApp = initKoin()
    val trayState = koinApp.koin.get<TrayState>()
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