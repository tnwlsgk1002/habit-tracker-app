package com.bibbidi.habittracker.data.mapper.track

import com.bibbidi.habittracker.data.mapper.DataModelMapper
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.model.joined.track.HabitAndTrackHabitEntity
import com.bibbidi.habittracker.domain.model.habitinfo.TrackHabitInfo

object TrackHabitEntityMapper : DataModelMapper<HabitAndTrackHabitEntity, TrackHabitInfo> {

    override fun asData(domain: TrackHabitInfo): HabitAndTrackHabitEntity =
        HabitAndTrackHabitEntity(
            HabitEntity(
                id = domain.habitId,
                name = domain.name,
                startDate = domain.startDate,
                emoji = domain.emoji,
                alarmTime = domain.alarmTime,
                whenRun = domain.whenRun,
                repeatDayOfTheWeeks = domain.repeatsDayOfTheWeeks
            ),
            TrackHabitEntity(
                habitId = domain.habitId,
                trackHabitId = domain.habitId
            )
        )

    override fun asDomain(data: HabitAndTrackHabitEntity): TrackHabitInfo =
        TrackHabitInfo(
            habitId = data.habit.id,
            childId = data.trackHabit.trackHabitId,
            name = data.habit.name,
            emoji = data.habit.emoji,
            alarmTime = data.habit.alarmTime,
            whenRun = data.habit.whenRun,
            repeatsDayOfTheWeeks = data.habit.repeatDayOfTheWeeks,
            startDate = data.habit.startDate
        )
}

fun HabitAndTrackHabitEntity.asDomain() = TrackHabitEntityMapper.asDomain(this)

fun TrackHabitInfo.asData() = TrackHabitEntityMapper.asData(this)
