package com.bibbidi.habittracker.data.model.entity.check

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bibbidi.habittracker.data.model.entity.HabitEntity

@Entity(
    tableName = "check_habits",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CheckHabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "check_habit_id")
    val checkHabitId: Long?,
    @ColumnInfo(name = "habit_parent_id")
    val habitId: Long?
)
