package com.example.myhabitapp.data.repositories

import com.example.myhabitapp.data.HabitDao
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import com.example.myhabitapp.domain.repositories.HabitRepository
import com.example.myhabitapp.presentation.mainScreen.getCurrentDate
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlin.coroutines.coroutineContext

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {
    override suspend fun getHabits(): Flow<List<Habit>> {
        val flow = habitDao.getHabits()

        return flow.onEach { habits ->
            habits.forEach { habit ->
                val lastRecord = habitDao
                    .getHabitRecordsByHabitId(habit.id)
                    .maxByOrNull { it.date }

                if (lastRecord == null) return@forEach

                if (lastRecord.date < getCurrentDate().minus(1, DateTimeUnit.DAY)) {
                    habitDao.insertHabit(habit.copy(streak = 0))
                }
                if (
                    lastRecord.date == getCurrentDate().minus(1, DateTimeUnit.DAY) &&
                    lastRecord.completionProgress >= (habit.numberGoal ?: 1)
                ) {
                    habitDao.insertHabit(habit.copy(streak = 0))
                }
            }
        }
    }

    override suspend fun getHabitRecordsByHabitId(id: Int): List<HabitRecord> {
        return habitDao.getHabitRecordsByHabitId(id)
    }

    override suspend fun getHabitById(habitId: Int?): Habit? {
        return if (habitId == null) {
            null
        } else {
            habitDao.getHabitById(habitId)
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