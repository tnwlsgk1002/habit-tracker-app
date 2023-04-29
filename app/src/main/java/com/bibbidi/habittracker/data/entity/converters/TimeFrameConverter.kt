package com.bibbidi.habittracker.data.entity.converters

import androidx.room.TypeConverter
import com.bibbidi.habittracker.data.entity.TimeFrame

object TimeFrameConverter {

    @TypeConverter
    fun fromTimeFrameSet(value: Set<TimeFrame>): String {
        return value.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toTimeFrameSet(value: String): Set<TimeFrame> {
        return value.split(",").map { TimeFrame.valueOf(it) }.toSet()
    }
}
