package com.bibbidi.habittracker.ui.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.domain.model.DBResult
import com.bibbidi.habittracker.ui.mapper.asUiModel
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
                    result.data.map { it.asUiModel() }.forEach {
                        alarmHelper.registerAlarm(it)
                    }
                }
            }
        }
    }
}