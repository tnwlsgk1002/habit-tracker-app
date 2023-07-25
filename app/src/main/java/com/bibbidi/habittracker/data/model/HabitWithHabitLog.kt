package com.bibbidi.habittracker.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.HabitLogEntity

data class HabitWithHabitLog(
    @Embedded val habit: HabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "fk_habit_id",
        entity = HabitLogEntity::class
    )
    val habitLog: HabitLogEntity?
)
