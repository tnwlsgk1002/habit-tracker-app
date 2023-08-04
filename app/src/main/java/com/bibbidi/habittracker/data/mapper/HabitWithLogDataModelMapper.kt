package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.habit.dto.HabitWithLogDTO
import com.bibbidi.habittracker.domain.model.HabitWithLog

object HabitWithLogDataModelMapper : DataModelMapper<HabitWithLogDTO, HabitWithLog> {
    override fun asData(domain: HabitWithLog) = HabitWithLogDTO(
        habit = domain.habit.asData(),
        habitLog = domain.habitLog.asData()
    )

    override fun asDomain(data: HabitWithLogDTO): HabitWithLog {
        data.habit?.asDomain() ?: error("HabitWithLogMapper: habit is null")
        data.habitLog ?: error("HabitWithLogMapper: habitLog is null")
        return HabitWithLog(
            habit = data.habit.asDomain(),
            habitLog = data.habitLog.asDomain()
        )
    }
}

fun HabitWithLogDTO.asDomain() = HabitWithLogDataModelMapper.asDomain(this)

fun HabitWithLog.asData() = HabitWithLogDataModelMapper.asData(this)
