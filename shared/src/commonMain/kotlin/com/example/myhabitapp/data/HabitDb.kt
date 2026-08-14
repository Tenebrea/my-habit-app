package com.example.myhabitapp.data

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.models.HabitRecord


@Database(entities = [Habit::class, HabitRecord::class], version = 1, exportSchema = true)
@ColumnTypeConverters(value = [Converters::class])
abstract class HabitDb : RoomDatabase() {
    abstract fun getDao(): HabitDao

    companion object {
        const val DATABASE_NAME = "habit_db.db"
    }
}