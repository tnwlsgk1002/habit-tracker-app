package com.bibbidi.habittracker.domain

import com.bibbidi.habittracker.domain.model.Habit

interface AlarmHelper {
    fun registerAlarm(habit: Habit, isAgain: Boolean = false)

    fun cancelAlarm(habitId: Long?)

    fun updateAlarm(habit: Habit)
}
