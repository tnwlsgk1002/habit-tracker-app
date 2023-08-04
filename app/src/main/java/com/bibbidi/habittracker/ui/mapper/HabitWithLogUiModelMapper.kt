package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel

object HabitWithLogUiModelMapper : UiModelMapper<HabitWithLogUiModel, HabitWithLog> {

    override fun asDomain(uiModel: HabitWithLogUiModel) = HabitWithLog(
        habit = uiModel.habit.asDomain(),
        habitLog = uiModel.habitLog.asDomain()
    )

    override fun asUiModel(domain: HabitWithLog) = HabitWithLogUiModel(
        habit = domain.habit.asUiModel(),
        habitLog = domain.habitLog.asUiModel()
    )
}

fun HabitWithLogUiModel.asDomain() = HabitWithLogUiModelMapper.asDomain(this)

fun HabitWithLog.asUiModel() = HabitWithLogUiModelMapper.asUiModel(this)
