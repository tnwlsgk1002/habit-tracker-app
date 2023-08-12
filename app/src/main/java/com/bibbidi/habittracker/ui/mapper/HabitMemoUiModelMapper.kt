package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.HabitMemo
import com.bibbidi.habittracker.ui.model.habit.HabitMemoItem

object HabitMemoUiModelMapper : UiModelMapper<HabitMemoItem, HabitMemo> {
    override fun asDomain(uiModel: HabitMemoItem) = HabitMemo(
        logId = uiModel.logId,
        habitId = uiModel.habitId,
        memo = uiModel.memo,
        date = uiModel.date
    )

    override fun asUiModel(domain: HabitMemo) = HabitMemoItem(
        logId = domain.logId,
        habitId = domain.habitId,
        memo = domain.memo,
        date = domain.date
    )
}

fun HabitMemo.asUiModel() = HabitMemoUiModelMapper.asUiModel(this)

fun HabitMemoItem.asDomain() = HabitMemoUiModelMapper.asDomain(this)
