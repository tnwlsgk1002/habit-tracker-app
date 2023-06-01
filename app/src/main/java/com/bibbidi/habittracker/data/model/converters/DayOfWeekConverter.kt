package com.bibbidi.habittracker.data.model.converters

import androidx.room.TypeConverter
import org.threeten.bp.DayOfWeek

object DayOfWeekConverter {

    @TypeConverter
    fun fromDayOfTheWeekSet(value: Set<DayOfWeek>): String {
        return value.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toDayOfTheWeekSet(value: String): Set<DayOfWeek> {
        return value.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    }
}
