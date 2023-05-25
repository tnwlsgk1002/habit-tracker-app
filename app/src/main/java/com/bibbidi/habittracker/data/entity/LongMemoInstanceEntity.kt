package com.bibbidi.habittracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habitId"],
            childColumns = ["longMemoInstanceId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LongMemoInstanceEntity(
    @PrimaryKey(autoGenerate = true)
    val longMemoInstanceId: Long,
    val habitId: Int,
    val content: String
)
