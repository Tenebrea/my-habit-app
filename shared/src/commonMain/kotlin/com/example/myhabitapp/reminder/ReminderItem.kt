package com.example.myhabitapp.reminder

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

data class ReminderItem(
    val id: String,
    val title: String,
    val description: String,
    val time: LocalTime,
    val repeatDays: List<DayOfWeek>
)

expect class ReminderScheduler {
    fun scheduleReminder(reminder: ReminderItem)
    fun cancelReminder(reminderId: String)
}
