package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitWithLogEntity
import com.bibbidi.habittracker.data.model.habit.HabitWithLog

object HabitWithLogDataModelMapper : DataModelMapper<HabitWithLogEntity, HabitWithLog> {
    override fun asData(domain: HabitWithLog) = HabitWithLogEntity(
        habit = domain.habit.asData(),
        habitLog = domain.habitLog.asData()
    )

    override fun asDomain(data: HabitWithLogEntity): HabitWithLog {
        data.habit?.asDomain() ?: error("HabitWithLogMapper: habit is null")
        data.habitLog ?: error("HabitWithLogMapper: habitLog is null")
        return HabitWithLog(
            habit = data.habit.asDomain(),
            habitLog = data.habitLog.asDomain()
        )
    }
}

fun HabitWithLogEntity.asDomain() = HabitWithLogDataModelMapper.asDomain(this)

fun HabitWithLog.asData() = HabitWithLogDataModelMapper.asData(this)
