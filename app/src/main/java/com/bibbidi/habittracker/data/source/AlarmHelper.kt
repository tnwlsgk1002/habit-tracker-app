package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.model.habit.Habit

interface AlarmHelper {
    fun registerAlarm(habit: Habit, isAgain: Boolean = false)

    fun cancelAlarm(habit: Habit)

    fun updateAlarm(habit: Habit)
}
