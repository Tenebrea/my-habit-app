package com.example.myhabitapp.di

import androidx.compose.ui.window.TrayState
import com.example.myhabitapp.data.getHabitDb
import com.example.myhabitapp.reminder.ReminderScheduler
import org.koin.dsl.module

actual val platformModule = module {
    single { getHabitDb() }
    single<TrayState> { TrayState() }
    single { ReminderScheduler(get()) }
}