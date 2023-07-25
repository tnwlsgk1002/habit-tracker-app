package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.Habit

object HabitEntityMapper : DataModelMapper<HabitEntity, Habit> {
    override fun asData(domain: Habit) = HabitEntity(
        id = domain.id,
        name = domain.name,
        startDate = domain.startDate,
        emoji = domain.emoji,
        alarmTime = domain.alarmTime,
        repeatDayOfTheWeeks = domain.repeatsDayOfTheWeeks
    )

    override fun asDomain(data: HabitEntity) = Habit(
        id = data.id,
        name = data.name,
        startDate = data.startDate,
        emoji = data.emoji,
        alarmTime = data.alarmTime,
        repeatsDayOfTheWeeks = data.repeatDayOfTheWeeks
    )
}

fun HabitEntity.asDomain(): Habit = HabitEntityMapper.asDomain(this)

fun Habit.asData(): HabitEntity = HabitEntityMapper.asData(this)
