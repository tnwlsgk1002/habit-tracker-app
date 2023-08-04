package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.data.model.habit.HabitLog
import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import org.threeten.bp.LocalDate

object HabitWithLogsUiModelMapper : UiModelMapper<HabitWithLogsUiModel, HabitWithLogs> {

    override fun asDomain(uiModel: HabitWithLogsUiModel) = HabitWithLogs(
        habit = uiModel.habit.asDomain(),
        habitLogs = LinkedHashMap<LocalDate, HabitLog>(uiModel.habitLogs.size).apply {
            uiModel.habitLogs.forEach { (k, v) ->
                this[k] = v.asDomain()
            }
        }
    )

    override fun asUiModel(domain: HabitWithLogs) = HabitWithLogsUiModel(
        habit = domain.habit.asUiModel(),
        habitLogs = LinkedHashMap<LocalDate, HabitLogUiModel>(domain.habitLogs.size).apply {
            domain.habitLogs.forEach { (k, v) ->
                this[k] = v.asUiModel()
            }
        }
    )
}

fun HabitWithLogsUiModel.asDomain() = HabitWithLogsUiModelMapper.asDomain(this)

fun HabitWithLogs.asUiModel() = HabitWithLogsUiModelMapper.asUiModel(this)
