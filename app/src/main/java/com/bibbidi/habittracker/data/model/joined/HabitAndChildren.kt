package com.bibbidi.habittracker.data.model.joined

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity

data class HabitAndChildren(
    @Embedded val habit: HabitEntity,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_parent_id",
        entity = CheckHabitEntity::class
    )
    val checkHabit: CheckHabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_parent_id",
        entity = TrackHabitEntity::class
    )
    val trackHabit: TrackHabitEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_parent_id",
        entity = TimeHabitEntity::class
    )
    val timeHabit: TimeHabitEntity?
)
