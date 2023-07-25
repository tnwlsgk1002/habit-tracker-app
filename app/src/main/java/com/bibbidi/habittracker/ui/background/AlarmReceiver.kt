package com.bibbidi.habittracker.ui.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        val habit =
            intent?.extras?.getParcelable<HabitUiModel>(Constants.HABIT_INFO_KEY)
                ?: return
        notificationManager.showNotification(habit)
    }
}
