package com.bibbidi.habittracker.data.mapper.check

import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity
import com.bibbidi.habittracker.domain.model.log.CheckHabitLog

fun CheckHabitLog.asData() = CheckHabitLogEntity(
    checkHabitId = parentId,
    checkHabitLogId = logId,
    date = date,
    isCompleted = isChecked
)
