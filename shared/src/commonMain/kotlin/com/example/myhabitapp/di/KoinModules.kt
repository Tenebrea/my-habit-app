package com.example.myhabitapp.di

import com.example.myhabitapp.data.HabitDb
import com.example.myhabitapp.data.repositories.HabitRepositoryImpl
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.habitCreation.HabitCreationViewModel
import com.example.myhabitapp.presentation.habitSummary.HabitSummaryViewModel
import com.example.myhabitapp.presentation.mainScreen.MainScreenViewModel
import kotlinx.datetime.LocalDate
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(platformModule)
    single { get<HabitDb>().getDao() }
    single<HabitRepository> { HabitRepositoryImpl(get()) }
    viewModel<MainScreenViewModel> { MainScreenViewModel(get()) }
    viewModel<HabitCreationViewModel> { (habitId: Int?) -> HabitCreationViewModel(get(), habitId) }
    viewModel<HabitSummaryViewModel> { (currentDate: LocalDate) -> HabitSummaryViewModel(get(), currentDate) }
}

