package com.example.myhabitapp.domain.repositories

import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    fun getHabitRecordsByHabitId(id: Int): Flow<List<HabitRecord>>
    suspend fun insertHabit(habit: Habit)
    suspend fun insertHabitRecord(habitRecord: HabitRecord)
    suspend fun deleteHabit(habit: Habit)
    suspend fun deleteHabitRecord(habitRecord: HabitRecord)
}