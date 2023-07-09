package com.bibbidi.habittracker.ui.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.model.habit.HabitAlarmUiModel
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        context ?: return

        val habitAlarm =
            intent.extras?.getParcelable<HabitAlarmUiModel>(Constants.HABIT_START_ALARM_KEY)
                ?: return
        notificationManager.showNotification(habitAlarm)
    }
}
