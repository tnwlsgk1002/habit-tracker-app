package com.bibbidi.habittracker.data.entity.check

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CheckHabit::class,
            parentColumns = ["id"],
            childColumns = ["checkHabitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CheckHabitLog(
    val id: Long,
    @PrimaryKey(autoGenerate = true)
    val checkHabitId: Long,
    val date: LocalDate = LocalDate.now(),
    val isCompleted: Boolean = false
)
