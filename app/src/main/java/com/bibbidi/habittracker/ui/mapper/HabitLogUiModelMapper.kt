package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

object HabitLogUiModelMapper : UiModelMapper<HabitLogUiModel, HabitLog> {

    override fun asDomain(uiModel: HabitLogUiModel) = HabitLog(
        id = uiModel.id,
        habitId = uiModel.habitId,
        date = uiModel.date,
        isCompleted = uiModel.isCompleted,
        memo = uiModel.memo
    )

    override fun asUiModel(domain: HabitLog) = HabitLogUiModel(
        id = domain.id,
        habitId = domain.habitId,
        date = domain.date,
        isCompleted = domain.isCompleted,
        memo = domain.memo
    )
}

fun HabitLogUiModel.asDomain() = HabitLogUiModelMapper.asDomain(this)

fun HabitLog.asUiModel() = HabitLogUiModelMapper.asUiModel(this)
