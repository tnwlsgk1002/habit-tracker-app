package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.HabitWithHabitLog
import com.bibbidi.habittracker.data.model.entity.HabitLogEntity
import com.bibbidi.habittracker.data.model.habit.HabitLog

object HabitWithLogMapper : DataModelMapper<HabitWithHabitLog, HabitLog> {
    override fun asData(domain: HabitLog) = HabitWithHabitLog(
        habit = domain.habitInfo.asData(),
        habitLog = HabitLogEntity(
            id = domain.id,
            habitId = domain.habitInfo.id,
            date = domain.date,
            isCompleted = domain.isCompleted,
            memo = domain.memo
        )
    )

    override fun asDomain(data: HabitWithHabitLog): HabitLog {
        val habit = data.habit?.asDomain() ?: error("HabitWithLogMapper: habit is null")
        data.habitLog ?: error("HabitWithLogMapper: habitLog is null")
        return HabitLog(
            id = data.habitLog.id,
            habitInfo = habit,
            date = data.habitLog.date,
            isCompleted = data.habitLog.isCompleted,
            memo = data.habitLog.memo
        )
    }
}

fun HabitWithHabitLog.asDomain() = HabitWithLogMapper.asDomain(this)

fun HabitLog.asData() = HabitWithLogMapper.asData(this)
