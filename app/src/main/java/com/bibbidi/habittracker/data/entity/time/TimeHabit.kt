package com.bibbidi.habittracker.data.entity.time

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.entity.Habit
import org.threeten.bp.Duration

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
data class TimeHabit(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val habitId: Long,
    val goalDuration: Duration
)
