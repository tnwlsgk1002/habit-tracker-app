package com.bibbidi.habittracker.ui.background

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.MainActivity
import com.bibbidi.habittracker.ui.common.Constants.START_HABIT_CHANNEL_ID
import com.bibbidi.habittracker.ui.model.habit.HabitAlarmUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun getNotifyId(alarm: HabitAlarmUiModel): Int {
        return "${alarm.habitId} - ${System.currentTimeMillis()}".hashCode()
    }

    fun showNotification(alarmInfo: HabitAlarmUiModel) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(context, START_HABIT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(
                context.getString(
                    R.string.start_habit_alarm_message,
                    alarmInfo.name,
                    alarmInfo.emoji
                )
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(getNotifyId(alarmInfo), builder.build())
        }
    }
}
