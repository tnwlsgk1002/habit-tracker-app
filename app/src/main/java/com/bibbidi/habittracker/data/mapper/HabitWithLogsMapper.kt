package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitWithLogsEntity
import com.bibbidi.habittracker.data.model.habit.HabitWithLogs

object HabitWithLogsMapper : DataModelMapper<HabitWithLogsEntity, HabitWithLogs> {

    override fun asData(domain: HabitWithLogs): HabitWithLogsEntity {
        return HabitWithLogsEntity(
            habit = domain.habit.asData(),
            habitLogs = domain.habitLogs.map { it.value.asData() }
        )
    }

    override fun asDomain(data: HabitWithLogsEntity): HabitWithLogs {
        return HabitWithLogs(
            habit = data.habit?.asDomain()
                ?: error("HabitWithLogsMapper: HabitWithHabitLogsEntity is Null"),
            habitLogs = data.habitLogs.associate { it.date to it.asDomain() }
        )
    }
}

fun HabitWithLogs.asData() = HabitWithLogsMapper.asData(this)

fun HabitWithLogsEntity.asDomain() = HabitWithLogsMapper.asDomain(this)
