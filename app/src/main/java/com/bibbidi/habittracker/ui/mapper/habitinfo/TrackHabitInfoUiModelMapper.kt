package com.bibbidi.habittracker.ui.mapper.habitinfo

import com.bibbidi.habittracker.domain.model.habitinfo.TrackHabitInfo
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TrackHabitInfoUiModel

object TrackHabitInfoUiModelMapper : UiModelMapper<TrackHabitInfoUiModel, TrackHabitInfo> {
    override fun asDomain(uiModel: TrackHabitInfoUiModel) = TrackHabitInfo(
        habitId = uiModel.habitId,
        childId = uiModel.childId,
        name = uiModel.name,
        emoji = uiModel.emoji,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        repeatsDayOfTheWeeks = uiModel.repeatsDayOfTheWeeks,
        startDate = uiModel.startDate
    )

    override fun asUiModel(domain: TrackHabitInfo) = TrackHabitInfoUiModel(
        habitId = domain.habitId,
        childId = domain.childId,
        name = domain.name,
        emoji = domain.emoji,
        alarmTime = domain.alarmTime,
        whenRun = domain.whenRun,
        repeatsDayOfTheWeeks = domain.repeatsDayOfTheWeeks,
        startDate = domain.startDate
    )
}

fun TrackHabitInfoUiModel.asDomain() = TrackHabitInfoUiModelMapper.asDomain(this)

fun TrackHabitInfo.asUiModel() = TrackHabitInfoUiModelMapper.asUiModel(this)
