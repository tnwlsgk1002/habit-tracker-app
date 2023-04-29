package com.bibbidi.habittracker.data.entity.time

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

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
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val timeHabitId: Long,
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.MIN
)
