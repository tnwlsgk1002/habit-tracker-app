package com.bibbidi.habittracker.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithLogEntity(
    @Embedded val habit: HabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "fk_habit_id",
        entity = HabitLogEntity::class
    )
    val habitLog: HabitLogEntity?
)
