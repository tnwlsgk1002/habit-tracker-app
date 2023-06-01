package com.bibbidi.habittracker.data.model.entity.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.model.entity.HabitEntity

@Entity(
    tableName = "track_habits",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackHabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("track_habit_id")
    val trackHabitId: Long?,
    @ColumnInfo("habit_parent_id")
    val habitId: Long?
)
