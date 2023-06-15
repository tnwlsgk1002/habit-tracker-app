package com.bibbidi.habittracker.ui.mapper.habitlog

import com.bibbidi.habittracker.domain.model.log.TrackHabitLog
import com.bibbidi.habittracker.ui.mapper.UiModelMapper
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel

object TrackHabitLogUiModelMapper : UiModelMapper<TrackHabitLogUiModel, TrackHabitLog> {

    override fun asDomain(uiModel: TrackHabitLogUiModel) = TrackHabitLog(
        logId = uiModel.logId,
        parentId = uiModel.parentId,
        date = uiModel.date,
        emoji = uiModel.emoji,
        name = uiModel.name,
        alarmTime = uiModel.alarmTime,
        whenRun = uiModel.whenRun,
        value = uiModel.value
    )

    override fun asUiModel(domain: TrackHabitLog) = TrackHabitLogUiModel(
        logId = domain.logId,
        parentId = domain.parentId,
        date = domain.date,
        emoji = domain.emoji,
        name = domain.name,
        alarmTime = domain.alarmTime,
        whenRun = domain.whenRun,
        value = domain.value
    )
}

fun TrackHabitLog.asUiModel() = TrackHabitLogUiModelMapper.asUiModel(this)

fun TrackHabitLogUiModel.asDomain() = TrackHabitLogUiModelMapper.asDomain(this)
