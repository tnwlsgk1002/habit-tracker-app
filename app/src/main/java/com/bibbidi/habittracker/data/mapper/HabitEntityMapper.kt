package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.domain.model.alarm.HabitAlarm
import com.bibbidi.habittracker.domain.model.habitinfo.HabitInfo

fun HabitInfo.asData(): HabitEntity = HabitEntity(
    id = habitId,
    name = name,
    startDate = startDate,
    emoji = emoji,
    alarmTime = alarmTime,
    whenRun = whenRun,
    repeatDayOfTheWeeks = repeatsDayOfTheWeeks
)

fun HabitEntity.asHabitAlarm() = HabitAlarm(
    habitId = id,
    name = name,
    startDate = startDate,
    emoji = emoji,
    alarmTime = alarmTime,
    repeatsDayOfTheWeeks = repeatDayOfTheWeeks
)
