package com.example.myhabitapp.reminder

import com.example.myhabitapp.domain.models.Habit
import com.example.myhabitapp.domain.repositories.ReminderRepository
import java.util.Locale.getDefault

class ReminderRepositoryImpl(
    private val reminderScheduler: ReminderScheduler
) : ReminderRepository {
    override fun createReminder(habit: Habit) {

        val reminderItem = habit.reminderTime?.let {
            ReminderItem(
                id = habit.id.toString(),
                title = habit.name,
                description = "${
                    habit.name.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(
                            getDefault()
                        ) else char.toString()
                    }
                } awaits to be done",
                time = it,
                repeatDays = habit.repeatDays
            )
        } ?: return

        reminderScheduler.scheduleReminder(reminderItem)
    }

    override fun removeReminder(habit: Habit) {
        reminderScheduler.cancelReminder(habit.id.toString())
    }
}