package com.example.myhabitapp.domain.models

import androidx.compose.ui.graphics.Color
import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Ignore
import androidx.room3.PrimaryKey
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "end_date")
    val endDate: LocalDate?,
    @ColumnInfo(name = "repeat_days")
    val repeatDays: List<DayOfWeek>,
    @ColumnInfo(name = "reminder_time")
    val reminderTime: LocalTime?,
    @ColumnInfo(name = "number_goal")
    val numberGoal: Int,
    val color: Int,
    val icon: Int,
    val streak: Int
)