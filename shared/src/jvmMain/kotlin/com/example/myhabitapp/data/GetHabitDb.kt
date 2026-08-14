package com.example.myhabitapp.data

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

fun getHabitDb(): HabitDb {
    val dbFile = File(System.getProperty("java.io.tmpdir"), HabitDb.DATABASE_NAME)
    return Room.databaseBuilder<HabitDb>(
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}