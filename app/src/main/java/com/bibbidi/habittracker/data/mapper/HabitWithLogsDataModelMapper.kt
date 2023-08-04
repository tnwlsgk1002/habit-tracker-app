package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.entity.HabitWithLogsEntity
import com.bibbidi.habittracker.data.model.habit.HabitLog
import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import org.threeten.bp.LocalDate

object HabitWithLogsDataModelMapper : DataModelMapper<HabitWithLogsEntity, HabitWithLogs> {

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
            habitLogs = LinkedHashMap<LocalDate, HabitLog>().apply {
                data.habitLogs.forEach { habitLog ->
                    this[habitLog.date] = habitLog.asDomain()
                }
            }
        )
    }
}

fun HabitWithLogs.asData() = HabitWithLogsDataModelMapper.asData(this)

fun HabitWithLogsEntity.asDomain() = HabitWithLogsDataModelMapper.asDomain(this)
