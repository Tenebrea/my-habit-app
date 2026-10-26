package com.example.myhabitapp.reminder

import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import com.example.myhabitapp.presentation.mainScreen.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDateTime
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

actual class ReminderScheduler(private val trayState: TrayState) {
    private val executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
    private val scheduledTasks = ConcurrentHashMap<String, ScheduledFuture<*>>()

    actual fun scheduleReminder(reminder: com.example.myhabitapp.reminder.ReminderItem) {
        val now = java.time.LocalDateTime.now()
        val currentDateTime = LocalDateTime(
            date = getCurrentDate(),
            time = LocalTime(hour = now.hour, minute = now.minute, second = now.second)
        )

        val dateNeeded = calculateNextRun(currentDateTime, reminder.repeatDays, reminder.time)

        val nowEpochSecond = Instant.now().epochSecond
        val targetEpochSecond = dateNeeded.toJavaLocalDateTime().atZone(ZoneId.systemDefault()).toEpochSecond()
        val delaySeconds = maxOf(0L, targetEpochSecond - nowEpochSecond)

        cancelReminder(reminder.id)

        val future = executor.schedule({
            try {
                val notification = Notification(
                    reminder.title,
                    reminder.description,
                    Notification.Type.Info
                )
                trayState.sendNotification(notification)
            } finally {
                scheduledTasks.remove(reminder.id)
                scheduleReminder(reminder)
            }
        }, delaySeconds, TimeUnit.SECONDS)

        scheduledTasks[reminder.id] = future
    }

    actual fun cancelReminder(reminderId: String) {
        val task = scheduledTasks.remove(reminderId)
        task?.cancel(false)
    }

    private fun calculateNextRun(
        now: LocalDateTime,
        days: List<DayOfWeek>,
        time: LocalTime
    ): LocalDateTime = now.let { adjustedDay ->
        val result = LocalDateTime(time = time, date = adjustedDay.date)
        val days = days.ifEmpty { DayOfWeek.entries }
        val dayDif = when {
            (result.dayOfWeek in days) && (now.time < time) -> 0
            days.any { it.ordinal > result.dayOfWeek.ordinal } ->
                days.filter { it.ordinal > result.dayOfWeek.ordinal }
                    .min().ordinal - result.dayOfWeek.ordinal
            else -> days.min().ordinal + DayOfWeek.entries.size - result.dayOfWeek.ordinal
        }
        result.apply {
            this.date.plus(dayDif, DateTimeUnit.DAY)
        }
    }
}