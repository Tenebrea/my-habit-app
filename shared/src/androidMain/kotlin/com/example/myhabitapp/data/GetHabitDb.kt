package com.example.myhabitapp.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver


fun getHabitDb(context: Context): HabitDb {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(HabitDb.DATABASE_NAME)
    return  Room.databaseBuilder<HabitDb>(
        context = appContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}