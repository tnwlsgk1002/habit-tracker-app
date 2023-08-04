package com.bibbidi.habittracker.ui.background

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bibbidi.habittracker.domain.AlarmHelper
import com.bibbidi.habittracker.domain.model.Habit
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.mapper.asUiModel
import com.bibbidi.habittracker.utils.asLong
import dagger.hilt.android.qualifiers.ApplicationContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmHelperImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AlarmHelper {

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

    private fun getRequestCode(habit: Habit): Int {
        return "${habit.id}".hashCode()
    }

    private fun getRequestCode(id: Long): Int {
        return "$id".hashCode()
    }

    private fun createPendingIntent(habit: Habit): PendingIntent {
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(Constants.HABIT_INFO_KEY, habit.asUiModel())
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

    override fun registerAlarm(habit: Habit, isAgain: Boolean) {
        val alarmTime = habit.alarmTime ?: return
        val calender = getAlarmCalender(habit.startDate, alarmTime, isAgain)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            createPendingIntent(habit)
        )
    }

    override fun updateAlarm(habit: Habit) {
        cancelAlarm(habit.id)
        registerAlarm(habit)
    }

    override fun cancelAlarm(habitId: Long?) {
        habitId ?: error("취소할 알람의 대상이 NULL 입니다.")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            getRequestCode(habitId),
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
