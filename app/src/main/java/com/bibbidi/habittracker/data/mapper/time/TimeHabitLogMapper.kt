package com.bibbidi.habittracker.data.mapper.time

import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.TimeHabitLog

fun TimeHabitLog.asData() = TimeHabitLogEntity(
    timeHabitId = parentId,
    timeHabitLogId = logId,
    date = date,
    duration = cntDuration
)
