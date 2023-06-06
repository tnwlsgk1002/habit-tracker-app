package com.bibbidi.habittracker.ui.mapper.habitinfo

import com.bibbidi.habittracker.domain.model.habitinfo.CheckHabitInfo
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.habitinfo.CheckHabitInfoUiModel

object CheckHabitInfoUiModelMapper : UiModelMapper<CheckHabitInfoUiModel, CheckHabitInfo> {
    override fun asDomain(uiModel: CheckHabitInfoUiModel) = CheckHabitInfo(
        habitId = uiModel.habitId,
        childId = uiModel.childId,
        name = uiModel.name,
        emoji = uiModel.emoji,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        repeatsDayOfTheWeeks = uiModel.repeatsDayOfTheWeeks,
        startDate = uiModel.startDate
    )

    override fun asUiModel(domain: CheckHabitInfo) = CheckHabitInfoUiModel(
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

fun CheckHabitInfoUiModel.asDomain() = CheckHabitInfoUiModelMapper.asDomain(this)

fun CheckHabitInfo.asUiModel() = CheckHabitInfoUiModelMapper.asUiModel(this)
