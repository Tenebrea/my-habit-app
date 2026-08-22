package com.example.myhabitapp.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habit_record WHERE habit_id = :id")
    suspend fun getHabitRecordsByHabitId(id: Int): List<HabitRecord>

    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitById(id: Int): Habit

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitRecord(habitRecord: HabitRecord)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Delete
    suspend fun deleteHabitRecord(habitRecord: HabitRecord)
}