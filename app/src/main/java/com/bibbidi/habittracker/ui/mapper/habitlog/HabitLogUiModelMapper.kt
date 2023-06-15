package com.bibbidi.habittracker.ui.mapper.habitlog

import com.bibbidi.habittracker.domain.model.log.CheckHabitLog
import com.bibbidi.habittracker.domain.model.log.HabitLog
import com.bibbidi.habittracker.domain.model.log.TimeHabitLog
import com.bibbidi.habittracker.domain.model.log.TrackHabitLog

fun HabitLog.asUiModel() =
    when (this) {
        is CheckHabitLog -> asUiModel()
        is TimeHabitLog -> asUiModel()
        is TrackHabitLog -> asUiModel()
    }
