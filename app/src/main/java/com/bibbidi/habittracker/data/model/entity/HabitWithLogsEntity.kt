package com.bibbidi.habittracker.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithLogsEntity(
    @Embedded val habit: HabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "fk_habit_id",
        entity = HabitLogEntity::class
    )
    val habitLogs: List<HabitLogEntity> = listOf()
)
