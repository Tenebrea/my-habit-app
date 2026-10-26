package com.example.myhabitapp.di

import com.example.myhabitapp.data.getHabitDb
import com.example.myhabitapp.reminder.ReminderScheduler
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { getHabitDb(get()) }
    single { ReminderScheduler(get()) }
}