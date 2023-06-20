package com.bibbidi.habittracker.ui.mapper.habitlog

import com.bibbidi.habittracker.domain.model.log.CheckHabitLog
import com.bibbidi.habittracker.domain.model.log.HabitLog
import com.bibbidi.habittracker.domain.model.log.TimeHabitLog
import com.bibbidi.habittracker.domain.model.log.TrackHabitLog
import com.bibbidi.habittracker.ui.model.habit.log.CheckHabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TimeHabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel

fun HabitLog.asUiModel() =
    when (this) {
        is CheckHabitLog -> asUiModel()
        is TimeHabitLog -> asUiModel()
        is TrackHabitLog -> asUiModel()
    }

fun HabitLogUiModel.asDomain() =
    when (this) {
        is CheckHabitLogUiModel -> asDomain()
        is TimeHabitLogUiModel -> asDomain()
        is TrackHabitLogUiModel -> asDomain()
    }
