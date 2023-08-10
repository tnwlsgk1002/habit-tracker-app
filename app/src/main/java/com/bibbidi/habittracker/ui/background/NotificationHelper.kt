package com.bibbidi.habittracker.ui.background

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.MainActivity
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.utils.Constants.START_HABIT_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun getNotifyId(habit: HabitUiModel): Int {
        return "${habit.id} - ${System.currentTimeMillis()}".hashCode()
    }

    fun showNotification(habit: HabitUiModel) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, START_HABIT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentText(
                context.getString(
                    R.string.start_habit_alarm_message,
                    habit.name,
                    habit.emoji
                )
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(getNotifyId(habit), builder.build())
        }
    }
}
