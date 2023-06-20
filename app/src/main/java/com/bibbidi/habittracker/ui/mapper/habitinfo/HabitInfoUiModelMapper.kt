package com.bibbidi.habittracker.ui.mapper.habitinfo

import com.bibbidi.habittracker.domain.model.habitinfo.CheckHabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.HabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.TimeHabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.TrackHabitInfo
import com.bibbidi.habittracker.ui.model.habit.habitinfo.CheckHabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TimeHabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TrackHabitInfoUiModel

fun HabitInfoUiModel.asDomain() =
    when (this) {
        is CheckHabitInfoUiModel -> asDomain()
        is TimeHabitInfoUiModel -> asDomain()
        is TrackHabitInfoUiModel -> asDomain()
    }

fun HabitInfo.asUiModel() =
    when (this) {
        is CheckHabitInfo -> asUiModel()
        is TimeHabitInfo -> asUiModel()
        is TrackHabitInfo -> asUiModel()
    }
