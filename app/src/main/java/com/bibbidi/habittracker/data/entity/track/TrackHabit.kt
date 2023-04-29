package com.bibbidi.habittracker.data.entity.track

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.entity.Habit

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackHabit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val habitId: Long
)
