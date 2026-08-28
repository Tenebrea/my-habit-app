package com.example.myhabitapp.data

import androidx.room3.ColumnTypeConverter
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month


class Converters {
    @ColumnTypeConverter
    fun stringToList(string: String): List<DayOfWeek> {
        if (string == "") {
            return emptyList<DayOfWeek>()
        } else {
            return listOf(
                *string
                    .split(",")
                    .map { day -> DayOfWeek.valueOf(day) }
                    .toTypedArray()
            )
        }
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
            year = list[0].toInt(),
            month = Month.valueOf(list[1]),
            day = list[2].toInt()
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