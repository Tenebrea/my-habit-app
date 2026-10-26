package com.example.myhabitapp.reminder

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myhabitapp.presentation.mainScreen.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDateTime
import java.time.Duration
import java.util.Calendar

actual class ReminderScheduler(private val context: Context) {
    private val workManager = WorkManager.getInstance(context)
    actual fun scheduleReminder(reminder: ReminderItem) {
        val calendar = Calendar.getInstance().time

        val now = LocalDateTime(
            date = getCurrentDate(),
            time = LocalTime(hour = calendar.hours, minute = calendar.minutes)
        )

        val nextRun = calculateNextRun(now, reminder.repeatDays, reminder.time)

        val delayDuration = Duration
            .between(now.toJavaLocalDateTime(), nextRun.toJavaLocalDateTime())
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayDuration)
            .build()

        workManager.enqueueUniqueWork(
            reminder.id,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    actual fun cancelReminder(reminderId: String) {
        workManager.cancelUniqueWork(reminderId)
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