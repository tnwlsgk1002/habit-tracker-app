package com.bibbidi.habittracker.domain.model.log

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TimeHabitLog(
    override val logId: Long?,
    override val parentId: Long?,
    override val date: LocalDate,
    override val emoji: String,
    override val name: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val goalDuration: Duration,
    val cntDuration: Duration
) : HabitLog {

    companion object TimeHabitFactory {

        fun createHabitLog(
            habit: HabitEntity,
            timeHabit: TimeHabitEntity,
            log: TimeHabitLogEntity
        ) = TimeHabitLog(
            parentId = habit.id,
            logId = timeHabit.timeHabitId,
            date = log.date,
            emoji = habit.emoji,
            name = habit.name,
            alarmTime = habit.alarmTime,
            whenRun = habit.whenRun,
            goalDuration = timeHabit.goalDuration,
            cntDuration = log.duration
        )
    }
}
