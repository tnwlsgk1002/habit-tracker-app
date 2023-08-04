package com.bibbidi.habittracker.ui.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.source.AlarmHelper
import com.bibbidi.habittracker.data.source.HabitsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RestartAlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var repository: HabitsRepository

    @Inject lateinit var coroutineScope: CoroutineScope

    @Inject lateinit var alarmHelper: AlarmHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            loadAlarms()
        }
    }

    private fun loadAlarms() {
        coroutineScope.launch {
            repository.getHabitAlarms().let { result ->
                if (result is DBResult.Success) {
                    result.data.forEach {
                        alarmHelper.registerAlarm(it)
                    }
                }
            }
        }
    }
}
