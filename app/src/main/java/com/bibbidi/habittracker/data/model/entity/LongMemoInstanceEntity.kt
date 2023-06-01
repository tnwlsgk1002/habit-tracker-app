package com.bibbidi.habittracker.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["long_memo_instance_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LongMemoInstanceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "long_memo_instance_id")
    val longMemoInstanceId: Long,
    val habitId: Int,
    val content: String
)
