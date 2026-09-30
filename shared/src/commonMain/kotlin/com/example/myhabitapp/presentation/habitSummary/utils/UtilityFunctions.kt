package com.example.myhabitapp.presentation.habitSummary.utils

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.minus

fun getAmountOfWeekDays(
    date: LocalDate,
    weekDays: List<DayOfWeek>
) : Int {
    if (weekDays.isEmpty()) {
        when (date.month) {
            Month.FEBRUARY -> {
                return if (date.year % 4 == 0) {
                    if (date.year % 100 == 0) {
                        if (date.year % 400 == 0) {
                            29
                        } else {
                            28
                        }
                    } else {
                        28
                    }
                } else {
                    28
                }
            }
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> {
                return 31
            }
            else -> {
                return 30
            }
        }
    }
    when (date.month) {
        Month.FEBRUARY -> {
            if (date.year % 4 == 0) {
                if (date.year % 100 == 0) {
                    if (date.year % 400 == 0) {
                        val additionalWeekDay = date.minus(date.day, DateTimeUnit.DAY).dayOfWeek
                        return weekDays.size * 4 + if (additionalWeekDay in weekDays) 1 else 0
                    } else {
                        return weekDays.size * 4
                    }
                } else {
                    val additionalWeekDay = date.minus(date.day, DateTimeUnit.DAY).dayOfWeek
                    return weekDays.size * 4 + if (additionalWeekDay in weekDays) 1 else 0
                }
            } else {
                return weekDays.size * 4
            }
        }
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> {
            val additionalWeekDays = listOf(
                date.minus(date.day, DateTimeUnit.DAY).dayOfWeek,
                date.minus(date.day+1, DateTimeUnit.DAY).dayOfWeek,
                date.minus(date.day+2, DateTimeUnit.DAY).dayOfWeek,
            )
            return weekDays.size * 4 + additionalWeekDays.count { it in weekDays }
        }
        else -> {
            val additionalWeekDays = listOf(
                date.minus(date.day, DateTimeUnit.DAY).dayOfWeek,
                date.minus(date.day+1, DateTimeUnit.DAY).dayOfWeek,
            )
            return weekDays.size * 4 + additionalWeekDays.count { it in weekDays }
        }
    }
}