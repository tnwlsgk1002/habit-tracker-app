package com.bibbidi.habittracking.data.entity.check

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
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val checkHabitId: Long,
    val date: LocalDate = LocalDate.now(),
    val isCompleted: Boolean = false
)
