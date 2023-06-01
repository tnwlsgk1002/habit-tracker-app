package com.bibbidi.habittracker.data.model.joined.time

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity

data class HabitAndTimeHabitEntity(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val timeHabit: TimeHabitEntity
)
