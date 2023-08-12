package com.bibbidi.habittracker.ui.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bibbidi.habittracker.domain.AlarmHelper
import com.bibbidi.habittracker.ui.mapper.asDomain
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationManager: NotificationHelper

    @Inject
    lateinit var alarmHelper: AlarmHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        val habit =
            intent?.extras?.getParcelable<HabitUiModel>(Constants.HABIT_INFO_KEY)
                ?: return
        notificationManager.showNotification(habit)
        alarmHelper.registerAlarm(habit.asDomain(), isAgain = true)
    }
}
