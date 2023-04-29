package com.bibbidi.habittracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LongMemoInstance(
    @PrimaryKey(autoGenerate = true)
    val longMemoInstanceId: Long,
    val habitId: Int,
    val content: String
)
