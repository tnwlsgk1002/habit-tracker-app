package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitLogEntity
import com.bibbidi.habittracker.data.model.habit.HabitLog

object HabitLogMapper : DataModelMapper<HabitLogEntity, HabitLog> {

    override fun asData(domain: HabitLog) =
        HabitLogEntity(
            id = domain.id,
            habitId = domain.habitId,
            date = domain.date,
            isCompleted = domain.isCompleted,
            memo = domain.memo
        )

    override fun asDomain(data: HabitLogEntity) =
        HabitLog(
            id = data.id,
            habitId = data.habitId,
            date = data.date,
            isCompleted = data.isCompleted,
            memo = data.memo
        )
}

fun HabitLog.asData() = HabitLogMapper.asData(this)

fun HabitLogEntity.asDomain() = HabitLogMapper.asDomain(this)
