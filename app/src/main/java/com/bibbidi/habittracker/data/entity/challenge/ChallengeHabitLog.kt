package com.bibbidi.habittracker.data.entity.challenge

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ChallengeHabit::class,
            parentColumns = ["id"],
            childColumns = ["challengeHabitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChallengeHabitLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val challengeHabitId: Long,
    val date: LocalDate = LocalDate.now(),
    val isCompleted: Boolean = false
)
