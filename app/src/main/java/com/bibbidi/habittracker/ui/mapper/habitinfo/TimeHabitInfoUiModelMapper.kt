package com.bibbidi.habittracker.ui.mapper.habitinfo

import com.bibbidi.habittracker.domain.model.habitinfo.TimeHabitInfo
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TimeHabitInfoUiModel

object TimeHabitInfoUiModelMapper : UiModelMapper<TimeHabitInfoUiModel, TimeHabitInfo> {
    override fun asDomain(uiModel: TimeHabitInfoUiModel) = TimeHabitInfo(
        habitId = uiModel.habitId,
        childId = uiModel.childId,
        name = uiModel.name,
        emoji = uiModel.emoji,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        repeatsDayOfTheWeeks = uiModel.repeatsDayOfTheWeeks,
        startDate = uiModel.startDate,
        goalDuration = uiModel.goalDuration
    )

    override fun asUiModel(domain: TimeHabitInfo) = TimeHabitInfoUiModel(
        habitId = domain.habitId,
        childId = domain.childId,
        name = domain.name,
        emoji = domain.emoji,
        alarmTime = domain.alarmTime,
        whenRun = domain.whenRun,
        repeatsDayOfTheWeeks = domain.repeatsDayOfTheWeeks,
        startDate = domain.startDate,
        goalDuration = domain.goalDuration
    )
}

fun TimeHabitInfoUiModel.asDomain() = TimeHabitInfoUiModelMapper.asDomain(this)
