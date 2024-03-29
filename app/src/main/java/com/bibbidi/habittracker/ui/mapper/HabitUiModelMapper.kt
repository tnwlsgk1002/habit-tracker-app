package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.Habit
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel

object HabitUiModelMapper : UiModelMapper<HabitUiModel, Habit> {
    override fun asDomain(uiModel: HabitUiModel) = Habit(
        id = uiModel.id,
        name = uiModel.name,
        emoji = uiModel.emoji,
        alarmTime = uiModel.alarmTime,
        repeatsDayOfTheWeeks = uiModel.repeatsDayOfTheWeeks,
        startDate = uiModel.startDate,
        timeFilters = uiModel.timeFilters.map { it.asDomain() }.toSet(),
        color = uiModel.color?.asDomain()
    )

    override fun asUiModel(domain: Habit) = HabitUiModel(
        id = domain.id,
        name = domain.name,
        emoji = domain.emoji,
        alarmTime = domain.alarmTime,
        repeatsDayOfTheWeeks = domain.repeatsDayOfTheWeeks,
        startDate = domain.startDate,
        timeFilters = domain.timeFilters.map { it.asUiModel() }.toSet(),
        color = domain.color?.asUiModel()
    )
}

fun HabitUiModel.asDomain() = HabitUiModelMapper.asDomain(this)

fun Habit.asUiModel() = HabitUiModelMapper.asUiModel(this)
