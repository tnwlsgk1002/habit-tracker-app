package com.bibbidi.habittracker.domain.model.log

import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TrackHabitLog(
    override val logId: Long?,
    override val parentId: Long?,
    override val date: LocalDate,
    override val emoji: String,
    override val name: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val value: Long?
) : HabitLog {

    companion object TrackHabitLogFactory {

        fun createHabitLog(
            habit: HabitEntity,
            trackHabit: TrackHabitEntity,
            log: TrackHabitLogEntity
        ) = TrackHabitLog(
            parentId = habit.id,
            logId = trackHabit.trackHabitId,
            date = log.date,
            emoji = habit.emoji,
            name = habit.name,
            alarmTime = habit.alarmTime,
            whenRun = habit.whenRun,
            value = log.value
        )
    }
}
