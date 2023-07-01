package com.bibbidi.habittracker.data.mapper.track

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.TrackHabitLog

fun TrackHabitLog.asData() = TrackHabitLogEntity(
    trackHabitId = habitTypeId,
    trackHabitLogId = logId,
    date = date,
    value = value
)

fun createHabitLog(
    habit: HabitEntity,
    trackHabit: TrackHabitEntity,
    log: TrackHabitLogEntity
) = TrackHabitLog(
    habitId = habit.id,
    logId = log.trackHabitLogId,
    habitTypeId = trackHabit.trackHabitId,
    date = log.date,
    emoji = habit.emoji,
    name = habit.name,
    alarmTime = habit.alarmTime,
    whenRun = habit.whenRun,
    value = log.value
)
