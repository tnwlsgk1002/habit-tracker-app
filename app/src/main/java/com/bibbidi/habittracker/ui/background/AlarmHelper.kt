package com.bibbidi.habittracker.ui.background

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.utils.asLong
import dagger.hilt.android.qualifiers.ApplicationContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun getAlarmCalender(
        startDate: LocalDate,
        alarmTime: LocalTime,
        reRegister: Boolean
    ): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = LocalDateTime.now().run {
                when {
                    reRegister -> toLocalDate().plusDays(1)
                    toLocalDate().isAfter(startDate) && toLocalTime().isBefore(alarmTime) -> toLocalDate()
                    isAfter(LocalDateTime.of(startDate, alarmTime)) -> toLocalDate()
                        .plusDays(1)
                    else -> startDate
                }.asLong()
            }

            set(Calendar.HOUR_OF_DAY, alarmTime.hour)
            set(Calendar.MINUTE, alarmTime.minute)
            set(Calendar.SECOND, 0)
        }
    }

    private fun getRequestCode(habit: HabitUiModel): Int {
        return "${habit.id}".hashCode()
    }

    private fun createPendingIntent(habit: HabitUiModel): PendingIntent {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(Constants.HABIT_INFO_KEY, habit)
            }
            putExtras(bundle)
        }

        return PendingIntent.getBroadcast(
            context,
            getRequestCode(habit),
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun registerAlarm(habit: HabitUiModel, reRegister: Boolean = false) {
        val alarmTime = habit.alarmTime ?: return
        val calender = getAlarmCalender(habit.startDate, alarmTime, reRegister)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            createPendingIntent(habit)
        )
    }

    fun updateAlarm(habit: HabitUiModel) {
        cancelAlarm(habit)
        registerAlarm(habit)
    }

    fun cancelAlarm(habit: HabitUiModel) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            getRequestCode(habit),
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
