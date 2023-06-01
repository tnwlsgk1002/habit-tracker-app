package com.bibbidi.habittracker.data.mapper.track

import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.TrackHabitLog

fun TrackHabitLog.asData() = TrackHabitLogEntity(
    trackHabitId = parentId,
    trackHabitLogId = logId,
    date = date,
    value = value
)
