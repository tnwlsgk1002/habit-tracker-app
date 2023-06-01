package com.bibbidi.habittracker.data.model.joined.check

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity

data class HabitAndCheckHabitEntity(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    @Embedded
    val checkHabit: CheckHabitEntity
)
