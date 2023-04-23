package com.bibbidi.habittracking.data.entity.check

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracking.data.entity.Habit

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
data class CheckHabit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val habitId: Long
)
