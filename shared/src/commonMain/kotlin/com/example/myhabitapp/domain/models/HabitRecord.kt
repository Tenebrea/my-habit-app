package com.example.myhabitapp.domain.models

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "habit_record",
    indices = [
        Index(
            value = ["date", "habit_id"],
            unique = true
        )
    ]
)
data class HabitRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    @ColumnInfo(name = "completion_progress")
    val completionProgress: Int?,
    val completed: Boolean,
    @ColumnInfo(name = "habit_id")
    val habitId: Int
)