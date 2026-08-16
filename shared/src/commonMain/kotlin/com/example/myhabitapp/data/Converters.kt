package com.example.myhabitapp.data

import androidx.room3.ColumnTypeConverter
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime


class Converters {
    @ColumnTypeConverter
    fun stringToList(string: String): List<DayOfWeek> {
        return listOf(*string.split(",").map { DayOfWeek.valueOf(it) }.toTypedArray())
    }
    @ColumnTypeConverter
    fun listToString(list: List<DayOfWeek>): String {
        return list.joinToString(",")
    }

    @ColumnTypeConverter
    fun dateToString(date: LocalDate): String {
        return "${date.year}-${date.month.name}-${date.day}"
    }

    @ColumnTypeConverter
    fun stringToDate(string: String): LocalDate {
        val list = listOf(*string.split("-").toTypedArray())
        val date = LocalDate(
            list[0].toInt(),
            list[1].toInt(),
            list[2].toInt()
        )
        return date
    }
    @ColumnTypeConverter
    fun timeToString(time: LocalTime): String {
        return "${time.hour}-${time.minute}"
    }

    @ColumnTypeConverter
    fun stringToTime(string: String): LocalTime {
        val list = listOf(*string.split("-").map { it.toInt() }.toTypedArray())
        return LocalTime(
            hour = list[0],
            minute = list[1]
        )
    }
}