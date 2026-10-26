package com.example.myhabitapp.reminder

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myhabitapp.presentation.mainScreen.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDateTime
import java.time.Duration
import java.util.Calendar

class ReminderWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val id = inputData.getInt(KEY_ID, 0)
        val title = inputData.getString(KEY_TITLE)
        val description = inputData.getString(KEY_MESSAGE)
        val time = inputData.getString(KEY_TIME)?.let { stringToTime(it) } ?: return Result.failure()
        val repeatDays = inputData.getString(KEY_REPEAT_DAYS)?.let { stringToList(it) } ?: return Result.failure()
        showNotification(id, title, description)

        scheduleNext(
            context,
            repeatDays,
            time,
            "${title}_$id"
        )
        return Result.success()
    }

    private fun scheduleNext(
        context: Context,
        days: List<DayOfWeek>,
        time: LocalTime,
        workName: String
    ) {
        val calendar = Calendar.getInstance().time

        val now = LocalDateTime(
            date = getCurrentDate(),
            time = LocalTime(hour = calendar.hours, minute = calendar.minutes)
        )
        val nextRun = calculateNextRun(now, days, time)
        val delayDuration = Duration.between(now.toJavaLocalDateTime(), nextRun.toJavaLocalDateTime())

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayDuration)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            workName,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
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


    private fun showNotification(
        id: Int,
        title: String?,
        description: String?
    ) {
        val channelId = "reminders_channel"
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Reminders",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat
            .Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }

    private fun stringToList(string: String): List<DayOfWeek>? {
        return if (string == "") {
            null
        } else {
            listOf(
                *string
                    .split(",")
                    .map { day -> DayOfWeek.valueOf(day) }
                    .toTypedArray()
            )
        }
    }

    private fun stringToTime(string: String): LocalTime? {
        return try {
            string.substring(0, 4).split(":").let { list ->
                LocalTime(hour = list[0].toInt(), minute = list[1].toInt())
            }
        } catch(e: Exception) {
            return null
        }
    }

    companion object {
        const val KEY_ID = "REMINDER_ID"
        const val KEY_TITLE = "REMINDER_TITLE"
        const val KEY_MESSAGE = "REMINDER_MESSAGE"
        const val KEY_REPEAT_DAYS = "REMINDER_REPEAT_DAYS"
        const val KEY_TIME = "REMINDER_TIME"
    }
}