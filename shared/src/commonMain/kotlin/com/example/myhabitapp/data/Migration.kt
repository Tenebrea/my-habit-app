package com.example.myhabitapp.data

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.myhabitapp.presentation.utils.HabitIcon

val MIGRATION_1_2 = object : Migration(1, 2) {
    override suspend fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
            CREATE TABLE habit_new (
                id INTEGER NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                end_date TEXT,
                repeat_days TEXT NOT NULL,
                reminder_time TEXT,
                number_goal INTEGER NULL,
                color INTEGER NOT NULL,
                icon INTEGER NOT NULL,
                streak INTEGER NOT NULL
            )
        """.trimIndent())

        connection.execSQL("""
            INSERT INTO habit_new (
                id,
                name,
                end_date,
                repeat_days,
                reminder_time,
                number_goal,
                color,
                icon,
                streak
            )
            SELECT
                id,
                name,
                end_date,
                repeat_days,
                reminder_time,
                COALESCE(number_goal, 0),
                color,
                icon,
                streak
            FROM habit
        """.trimIndent())

        connection.execSQL("DROP TABLE habit")
        connection.execSQL("ALTER TABLE habit_new RENAME TO habit")
    }
}