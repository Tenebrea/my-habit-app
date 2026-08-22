package com.example.myhabitapp.data.repositories

import com.example.myhabitapp.data.HabitDao
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import com.example.myhabitapp.domain.repositories.HabitRepository
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {
    override suspend fun getHabits(): Flow<List<Habit>> {
        return habitDao.getHabits()
    }

    override suspend fun getHabitRecordsByHabitId(id: Int): List<HabitRecord> {
        return habitDao.getHabitRecordsByHabitId(id)
    }

    override suspend fun getHabitById(habitId: Int?): Habit? {
        if (habitId == null) {
            return null
        } else {
            return habitDao.getHabitById(habitId)
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    override suspend fun insertHabitRecord(habitRecord: HabitRecord) {
        habitDao.insertHabitRecord(habitRecord)
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    override suspend fun deleteHabitRecord(habitRecord: HabitRecord) {
        habitDao.deleteHabitRecord(habitRecord)
    }
}