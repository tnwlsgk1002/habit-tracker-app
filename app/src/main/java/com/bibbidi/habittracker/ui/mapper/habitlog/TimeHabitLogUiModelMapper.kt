package com.bibbidi.habittracker.ui.mapper.habitlog

import com.bibbidi.habittracker.domain.model.log.TimeHabitLog
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.log.TimeHabitLogUiModel

object TimeHabitLogUiModelMapper : UiModelMapper<TimeHabitLogUiModel, TimeHabitLog> {

    override fun asDomain(uiModel: TimeHabitLogUiModel) = TimeHabitLog(
        logId = uiModel.logId,
        habitId = uiModel.habitId,
        habitTypeId = uiModel.habitTypeId,
        date = uiModel.date,
        emoji = uiModel.emoji,
        name = uiModel.name,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        goalDuration = uiModel.goalDuration,
        cntDuration = uiModel.cntDuration
    )

    override fun asUiModel(domain: TimeHabitLog) = TimeHabitLogUiModel(
        logId = domain.logId,
        habitId = domain.habitId,
        habitTypeId = domain.habitTypeId,
        date = domain.date,
        emoji = domain.emoji,
        name = domain.name,
        alarmTime = domain.alarmTime,
        whenRun = domain.whenRun,
        goalDuration = domain.goalDuration,
        cntDuration = domain.cntDuration,
        isStarted = false
    )
}

fun TimeHabitLog.asUiModel() = TimeHabitLogUiModelMapper.asUiModel(this)

fun TimeHabitLogUiModel.asDomain() = TimeHabitLogUiModelMapper.asDomain(this)
