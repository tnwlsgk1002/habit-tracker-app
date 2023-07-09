package com.bibbidi.habittracker.ui.background

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.model.habit.HabitAlarmUiModel
import com.bibbidi.habittracker.utils.asLong
import dagger.hilt.android.qualifiers.ApplicationContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

class AlarmHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun registerAlarm(alarmInfo: HabitAlarmUiModel) {
        val alarmTime = alarmInfo.alarmTime ?: return

        val calendar = Calendar.getInstance().apply {
            timeInMillis = if (LocalDateTime.now().withHour(alarmTime.hour).withMinute(alarmTime.minute)
                .isBefore(LocalDateTime.now())
            ) {
                LocalDate.now().plusDays(1).asLong()
            } else {
                alarmInfo.startDate.asLong()
            }
            set(Calendar.HOUR_OF_DAY, alarmTime.hour)
            set(Calendar.MINUTE, alarmTime.minute)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            createPendingIntent(alarmInfo)
        )
    }

    fun updateAlarm(alarmInfo: HabitAlarmUiModel) {
        cancelAlarm(alarmInfo)
        registerAlarm(alarmInfo)
    }

    fun cancelAlarm(alarmInfo: HabitAlarmUiModel) {
        val pendingIntent = createPendingIntent(alarmInfo)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun getRequestCode(alarmInfo: HabitAlarmUiModel): Int {
        return "${alarmInfo.habitId}".hashCode()
    }

    private fun createPendingIntent(alarmInfo: HabitAlarmUiModel): PendingIntent {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(Constants.HABIT_START_ALARM_KEY, alarmInfo)
            }
            putExtras(bundle)
        }

        return PendingIntent.getBroadcast(
            context,
            getRequestCode(alarmInfo),
            alarmIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
    }
}
