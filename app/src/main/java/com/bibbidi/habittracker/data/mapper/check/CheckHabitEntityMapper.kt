package com.bibbidi.habittracker.data.mapper.check

import com.bibbidi.habittracker.data.mapper.DataModelMapper
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.joined.check.HabitAndCheckHabitEntity
import com.bibbidi.habittracker.domain.model.habitinfo.CheckHabitInfo

object CheckHabitEntityMapper : DataModelMapper<HabitAndCheckHabitEntity, CheckHabitInfo> {

    override fun asData(domain: CheckHabitInfo): HabitAndCheckHabitEntity =
        HabitAndCheckHabitEntity(
            HabitEntity(
                id = domain.habitId,
                name = domain.name,
                startDate = domain.startDate,
                emoji = domain.emoji,
                alarmTime = domain.alarmTime,
                whenRun = domain.whenRun,
                repeatDayOfTheWeeks = domain.repeatsDayOfTheWeeks
            ),
            CheckHabitEntity(
                habitId = domain.habitId,
                checkHabitId = domain.habitId
            )
        )

    override fun asDomain(data: HabitAndCheckHabitEntity): CheckHabitInfo =
        CheckHabitInfo(
            habitId = data.habit.id,
            childId = data.checkHabit.checkHabitId,
            name = data.habit.name,
            emoji = data.habit.emoji,
            alarmTime = data.habit.alarmTime,
            whenRun = data.habit.whenRun,
            repeatsDayOfTheWeeks = data.habit.repeatDayOfTheWeeks,
            startDate = data.habit.startDate
        )
}

fun HabitAndCheckHabitEntity.asDomain() = CheckHabitEntityMapper.asDomain(this)

fun CheckHabitInfo.asData() = CheckHabitEntityMapper.asData(this)
