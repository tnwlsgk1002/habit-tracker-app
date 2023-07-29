package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel

object HabitWithLogsUiModelMapper : UiModelMapper<HabitWithLogsUiModel, HabitWithLogs> {

    override fun asDomain(uiModel: HabitWithLogsUiModel) = HabitWithLogs(
        habit = uiModel.habit.asDomain(),
        habitLogs = uiModel.habitLogs.map { (k, v) -> k to v.asDomain() }.toMap()
    )

    override fun asUiModel(domain: HabitWithLogs) = HabitWithLogsUiModel(
        habit = domain.habit.asUiModel(),
        habitLogs = domain.habitLogs.map { (k, v) -> k to v.asUiModel() }.toMap()
    )
}

fun HabitWithLogsUiModel.asDomain() = HabitWithLogsUiModelMapper.asDomain(this)

fun HabitWithLogs.asUiModel() = HabitWithLogsUiModelMapper.asUiModel(this)
