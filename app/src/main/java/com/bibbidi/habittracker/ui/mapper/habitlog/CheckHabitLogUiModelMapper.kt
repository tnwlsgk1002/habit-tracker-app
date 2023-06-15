package com.bibbidi.habittracker.ui.mapper.habitlog

import com.bibbidi.habittracker.domain.model.log.CheckHabitLog
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.log.CheckHabitLogUiModel

object CheckHabitLogUiModelMapper : UiModelMapper<CheckHabitLogUiModel, CheckHabitLog> {

    override fun asDomain(uiModel: CheckHabitLogUiModel) = CheckHabitLog(
        logId = uiModel.logId,
        parentId = uiModel.parentId,
        date = uiModel.date,
        emoji = uiModel.emoji,
        name = uiModel.name,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        isChecked = uiModel.isChecked
    )

    override fun asUiModel(domain: CheckHabitLog) = CheckHabitLogUiModel(
        logId = domain.logId,
        parentId = domain.parentId,
        date = domain.date,
        emoji = domain.emoji,
        name = domain.name,
        alarmTime = domain.alarmTime,
        whenRun = domain.whenRun,
        isChecked = domain.isChecked
    )
}

fun CheckHabitLog.asUiModel() = CheckHabitLogUiModelMapper.asUiModel(this)

fun CheckHabitLogUiModel.asDomain() = CheckHabitLogUiModelMapper.asDomain(this)
