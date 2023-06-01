package com.bibbidi.habittracker.data.model.entity.time

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import org.threeten.bp.Duration

@Entity(
    tableName = "time_habits",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeHabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("time_habit_id")
    val timeHabitId: Long?,
    @ColumnInfo("habit_parent_id")
    val habitId: Long?,
    val goalDuration: Duration
)
