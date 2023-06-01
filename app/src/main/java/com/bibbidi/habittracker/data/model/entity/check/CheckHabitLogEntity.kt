package com.bibbidi.habittracker.data.model.entity.check

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(
    tableName = "check_habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = CheckHabitEntity::class,
            parentColumns = ["check_habit_id"],
            childColumns = ["check_habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CheckHabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "check_habit_log_id")
    val checkHabitLogId: Long? = null,
    @ColumnInfo(name = "check_habit_parent_id")
    val checkHabitId: Long?,
    @ColumnInfo(name = "date")
    val date: LocalDate = LocalDate.now(),
    val isCompleted: Boolean = false
)
