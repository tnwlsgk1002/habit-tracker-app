package com.bibbidi.habittracker.data.model.habit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(
    tableName = "habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["fk_habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_log_id")
    val id: Long? = null,
    @ColumnInfo(name = "fk_habit_id")
    val habitId: Long? = null,
    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false,
    val memo: String? = null
)
