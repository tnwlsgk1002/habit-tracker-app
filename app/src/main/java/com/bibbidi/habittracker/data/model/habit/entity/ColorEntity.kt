package com.bibbidi.habittracker.data.model.habit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("color_id")
    val id: Long,
    @ColumnInfo("hex_code")
    val hexCode: String
)
