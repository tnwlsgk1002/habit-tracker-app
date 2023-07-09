package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.alarm.HabitAlarm
import com.bibbidi.habittracker.ui.model.habit.HabitAlarmUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel

fun HabitAlarm.asUiModel() = HabitAlarmUiModel(
    habitId = habitId,
    name = name,
    emoji = emoji,
    alarmTime = alarmTime,
    repeatsDayOfTheWeeks = repeatsDayOfTheWeeks,
    startDate = startDate
)

fun HabitInfoUiModel.asHabitStartAlarmUiModel() = HabitAlarmUiModel(
    habitId = habitId,
    name = name,
    emoji = emoji,
    alarmTime = alarmTime,
    repeatsDayOfTheWeeks = repeatsDayOfTheWeeks,
    startDate = startDate
)
