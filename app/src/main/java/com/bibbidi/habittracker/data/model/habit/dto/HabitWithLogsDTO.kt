package com.bibbidi.habittracker.data.model.habit.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity

data class HabitWithLogsDTO(
    @Embedded val habit: HabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "fk_habit_id",
        entity = HabitLogEntity::class
    )
    val habitLogs: List<HabitLogEntity> = listOf()
)
