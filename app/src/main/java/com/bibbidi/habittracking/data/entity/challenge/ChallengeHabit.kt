package com.bibbidi.habittracking.data.entity.challenge

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.bibbidi.habittracking.data.entity.Habit
import org.threeten.bp.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = CASCADE
        )
    ]
)
data class ChallengeHabit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val habitId: Long,
    val endDate: LocalDate,
    val goalCount: Int
)
