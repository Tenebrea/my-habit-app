package com.example.myhabitapp.domain.repositories

import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun getHabits(): Flow<List<Habit>>
    suspend fun getHabitRecordsByHabitId(id: Int): List<HabitRecord>
    suspend fun getHabitById(habitId: Int?): Habit?
    suspend fun insertHabit(habit: Habit)
    suspend fun insertHabitRecord(habitRecord: HabitRecord)
    suspend fun deleteHabit(habit: Habit)
    suspend fun deleteHabitRecord(habitRecord: HabitRecord)
}