package com.bibbidi.habittracker.data.entity.time

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.entity.Habit
import org.threeten.bp.LocalTime

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
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val habitId: Long,
    val goalTime: LocalTime
)
