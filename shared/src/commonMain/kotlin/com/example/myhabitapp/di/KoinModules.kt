package com.example.myhabitapp.di

import com.example.myhabitapp.data.HabitDb
import com.example.myhabitapp.data.repositories.HabitRepositoryImpl
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.habitCreation.HabitCreationViewModel
import com.example.myhabitapp.presentation.mainScreen.MainScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

expect val platformModule: Module

val sharedModule = module {
    includes(platformModule)
    single { get<HabitDb>().getDao() }
    single<HabitRepository> { HabitRepositoryImpl(get()) }
    viewModel<MainScreenViewModel> { MainScreenViewModel(get()) }
    viewModel<HabitCreationViewModel> { (habitId: Int?) -> HabitCreationViewModel(get(), habitId) }
}

