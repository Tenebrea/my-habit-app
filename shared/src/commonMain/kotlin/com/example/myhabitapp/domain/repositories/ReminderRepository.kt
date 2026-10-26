package com.example.myhabitapp.domain.repositories

import com.example.myhabitapp.domain.models.Habit

interface ReminderRepository {
    fun createReminder(habit: Habit)
    fun removeReminder(habit: Habit)
}