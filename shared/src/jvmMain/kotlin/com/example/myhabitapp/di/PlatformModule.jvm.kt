package com.example.myhabitapp.di

import com.example.myhabitapp.data.getHabitDb
import org.koin.dsl.module

actual val platformModule = module {
    single {
        getHabitDb()
    }
}