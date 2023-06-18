package com.bibbidi.habittracker.data.mapper.check

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.CheckHabitLog

fun CheckHabitLog.asData() = CheckHabitLogEntity(
    checkHabitId = habitId,
    checkHabitLogId = logId,
    date = date,
    isCompleted = isChecked
)

fun createHabitLog(
    habit: HabitEntity,
    checkHabit: CheckHabitEntity,
    log: CheckHabitLogEntity
) = CheckHabitLog(
    logId = log.checkHabitLogId,
    habitId = habit.id,
    habitTypeId = checkHabit.checkHabitId,
    date = log.date,
    emoji = habit.emoji,
    name = habit.name,
    alarmTime = habit.alarmTime,
    whenRun = habit.whenRun,
    isChecked = log.isCompleted
)
