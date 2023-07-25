package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.data.model.habit.HabitLog
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

object HabitLogUiModelMapper : UiModelMapper<HabitLogUiModel, HabitLog> {
    override fun asDomain(uiModel: HabitLogUiModel) = HabitLog(
        id = uiModel.id,
        habitInfo = uiModel.habitInfo.asDomain(),
        date = uiModel.date,
        isCompleted = uiModel.isCompleted,
        memo = uiModel.memo
    )

    override fun asUiModel(domain: HabitLog) = HabitLogUiModel(
        id = domain.id,
        habitInfo = domain.habitInfo.asUiModel(),
        date = domain.date,
        isCompleted = domain.isCompleted,
        memo = domain.memo
    )
}

fun HabitLogUiModel.asDomain() = HabitLogUiModelMapper.asDomain(this)

fun HabitLog.asUiModel() = HabitLogUiModelMapper.asUiModel(this)
