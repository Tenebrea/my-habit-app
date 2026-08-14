package com.example.myhabitapp.di

import com.example.myhabitapp.data.HabitDb
import com.example.myhabitapp.data.repositories.HabitRepositoryImpl
import com.example.myhabitapp.domain.repositories.HabitRepository
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(platformModule)
    single { get<HabitDb>().getDao() }
    single<HabitRepository> { HabitRepositoryImpl(get()) }
}

