package com.bibbidi.habittracker.domain.model.log

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class CheckHabitLog(
    override val logId: Long?,
    override val parentId: Long?,
    override val date: LocalDate,
    override val emoji: String,
    override val name: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val isChecked: Boolean
) : HabitLog {

    companion object CheckHabitLogFactory {

        fun createHabitLog(
            habit: HabitEntity,
            checkHabit: CheckHabitEntity,
            log: CheckHabitLogEntity
        ) = CheckHabitLog(
            parentId = habit.id,
            logId = checkHabit.checkHabitId,
            date = log.date,
            emoji = habit.emoji,
            name = habit.name,
            alarmTime = habit.alarmTime,
            whenRun = habit.whenRun,
            isChecked = log.isCompleted
        )
    }
}
