package com.bibbidi.habittracker.data.entity.time

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TimeHabit::class,
            parentColumns = ["id"],
            childColumns = ["timeHabitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeHabitLog(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val timeHabitId: Long,
    val date: LocalDate = LocalDate.now(),
    val time: Duration = Duration.ofHours(0L)
)
