package com.bibbidi.habittracker.data.mapper.time

import com.bibbidi.habittracker.data.mapper.DataModelMapper
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.joined.time.HabitAndTimeHabitEntity
import com.bibbidi.habittracker.domain.model.habitinfo.TimeHabitInfo

object TimeHabitEntityMapper : DataModelMapper<HabitAndTimeHabitEntity, TimeHabitInfo> {

    override fun asData(domain: TimeHabitInfo): HabitAndTimeHabitEntity =
        HabitAndTimeHabitEntity(
            HabitEntity(
                id = domain.habitId,
                name = domain.name,
                startDate = domain.startDate,
                emoji = domain.emoji,
                alarmTime = domain.alarmTime,
                whenRun = domain.whenRun,
                repeatDayOfTheWeeks = domain.repeatsDayOfTheWeeks
            ),
            TimeHabitEntity(
                habitId = domain.habitId,
                timeHabitId = domain.habitId,
                goalDuration = domain.goalDuration
            )
        )

    override fun asDomain(data: HabitAndTimeHabitEntity): TimeHabitInfo =
        TimeHabitInfo(
            habitId = data.habit.id,
            childId = data.timeHabit.timeHabitId,
            name = data.habit.name,
            emoji = data.habit.emoji,
            alarmTime = data.habit.alarmTime,
            whenRun = data.habit.whenRun,
            repeatsDayOfTheWeeks = data.habit.repeatDayOfTheWeeks,
            startDate = data.habit.startDate,
            goalDuration = data.timeHabit.goalDuration
        )
}

fun HabitAndTimeHabitEntity.asDomain() = TimeHabitEntityMapper.asDomain(this)

fun TimeHabitInfo.asData() = TimeHabitEntityMapper.asData(this)
