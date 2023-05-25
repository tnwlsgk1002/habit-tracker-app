package com.bibbidi.habittracker.data.entity.track

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TrackHabit::class,
            parentColumns = ["id"],
            childColumns = ["trackHabitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackHabitLog(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val trackHabitId: Long,
    val value: Long
)
