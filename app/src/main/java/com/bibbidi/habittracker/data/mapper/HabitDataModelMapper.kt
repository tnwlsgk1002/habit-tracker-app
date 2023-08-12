package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.domain.model.Habit

object HabitDataModelMapper : DataModelMapper<HabitEntity, Habit> {
    override fun asData(domain: Habit) = HabitEntity(
        id = domain.id,
        name = domain.name,
        startDate = domain.startDate,
        emoji = domain.emoji,
        alarmTime = domain.alarmTime,
        repeatDayOfTheWeeks = domain.repeatsDayOfTheWeeks,
        timeFilters = domain.timeFilters,
        color = domain.color?.asData()
    )

    override fun asDomain(data: HabitEntity) = Habit(
        id = data.id,
        name = data.name,
        startDate = data.startDate,
        emoji = data.emoji,
        alarmTime = data.alarmTime,
        repeatsDayOfTheWeeks = data.repeatDayOfTheWeeks,
        timeFilters = data.timeFilters,
        color = data.color?.asDomain()
    )
}

fun HabitEntity.asDomain(): Habit = HabitDataModelMapper.asDomain(this)

fun Habit.asData(): HabitEntity = HabitDataModelMapper.asData(this)
