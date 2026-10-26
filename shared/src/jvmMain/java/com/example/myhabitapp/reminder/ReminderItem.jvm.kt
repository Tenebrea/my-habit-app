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
        val currentDate = getCurrentDate()
        val currentTime = System.currentTimeMillis()
        val currentDateTime = LocalDateTime(
            date = currentDate,
            time = LocalTime(hour = currentTime.hours.toInt(DurationUnit.HOURS), minute = currentTime.minutes.toInt(DurationUnit.MINUTES))
        )

        val dateNeeded = calculateNextRun(currentDateTime, reminder.repeatDays, reminder.time)


        val delaySeconds =
            dateNeeded.toJavaLocalDateTime().toEpochSecond(java.time.ZoneOffset.UTC) - System.currentTimeMillis().seconds.toInt(
                DurationUnit.SECONDS)
        cancelReminder("${reminder.title}_${reminder.id}")

        val future = executor.schedule({
            try {
                val notification = Notification(
                    reminder.title,
                    reminder.description,
                    Notification.Type.Info
                )
                trayState.sendNotification(notification)
                scheduleReminder(reminder)
            } finally {
                scheduledTasks.remove("${reminder.title}_${reminder.id}")
                scheduleReminder(reminder)
            }
        }, delaySeconds, TimeUnit.SECONDS)

        scheduledTasks["${reminder.title}_${reminder.id}"] = future
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