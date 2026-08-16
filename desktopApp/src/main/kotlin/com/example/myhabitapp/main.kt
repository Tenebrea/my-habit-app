package com.example.myhabitapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myhabitapp.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyHabitApp",
    ) {
        App()
    }
}