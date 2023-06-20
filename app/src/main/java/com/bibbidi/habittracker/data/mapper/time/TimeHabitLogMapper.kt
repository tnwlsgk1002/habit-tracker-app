package com.bibbidi.habittracker.data.mapper.time

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.TimeHabitLog

fun TimeHabitLog.asData() = TimeHabitLogEntity(
    timeHabitId = habitId,
    timeHabitLogId = logId,
    date = date,
    duration = cntDuration
)

fun createHabitLog(
    habit: HabitEntity,
    timeHabit: TimeHabitEntity,
    log: TimeHabitLogEntity
) = TimeHabitLog(
    habitId = habit.id,
    logId = log.timeHabitLogId,
    habitTypeId = timeHabit.timeHabitId,
    date = log.date,
    emoji = habit.emoji,
    name = habit.name,
    alarmTime = habit.alarmTime,
    whenRun = habit.whenRun,
    goalDuration = timeHabit.goalDuration,
    cntDuration = log.duration
)
