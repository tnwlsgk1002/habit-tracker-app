package com.bibbidi.habittracker.data.model.joined.track

import androidx.room.Embedded
import androidx.room.Relation
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity

data class HabitAndTrackHabitEntity(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val trackHabit: TrackHabitEntity
)
