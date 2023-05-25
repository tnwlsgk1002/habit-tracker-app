package com.bibbidi.habittracker.data.entity.track

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.entity.HabitEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackHabitEntity(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val habitId: Long
)
