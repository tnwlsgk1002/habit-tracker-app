package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.habit.dto.HabitWithLogsDTO
import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.domain.model.HabitWithLogs
import org.threeten.bp.LocalDate

object HabitWithLogsDataModelMapper : DataModelMapper<HabitWithLogsDTO, HabitWithLogs> {

    override fun asData(domain: HabitWithLogs): HabitWithLogsDTO {
        return HabitWithLogsDTO(
            habit = domain.habit.asData(),
            habitLogs = domain.habitLogs.map { it.value.asData() }
        )
    }

    override fun asDomain(data: HabitWithLogsDTO): HabitWithLogs {
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

fun HabitWithLogsDTO.asDomain() = HabitWithLogsDataModelMapper.asDomain(this)
