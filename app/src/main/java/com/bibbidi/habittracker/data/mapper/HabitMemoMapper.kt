package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.habit.HabitMemo
import com.bibbidi.habittracker.data.model.habit.HabitMemoDTO

object HabitMemoMapper : DataModelMapper<HabitMemoDTO, HabitMemo> {

    override fun asData(domain: HabitMemo) = HabitMemoDTO(
        logId = domain.logId,
        habitId = domain.habitId,
        date = domain.date,
        memo = domain.memo
    )

    override fun asDomain(data: HabitMemoDTO) = HabitMemo(
        logId = data.logId,
        habitId = data.habitId,
        date = data.date,
        memo = data.memo
    )
}

fun HabitMemoDTO.asDomain() = HabitMemoMapper.asDomain(this)

fun HabitMemo.asData() = HabitMemoMapper.asData(this)
