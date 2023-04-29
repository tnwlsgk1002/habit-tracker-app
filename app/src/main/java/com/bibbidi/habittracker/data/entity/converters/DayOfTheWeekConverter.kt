package com.bibbidi.habittracker.data.entity.converters

import androidx.room.TypeConverter
import com.bibbidi.habittracker.data.entity.DayOfTheWeek

object DayOfTheWeekConverter {

    @TypeConverter
    fun fromDayOfTheWeekSet(value: Set<DayOfTheWeek>): String {
        return value.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toDayOfTheWeekSet(value: String): Set<DayOfTheWeek> {
        return value.split(",").map { DayOfTheWeek.valueOf(it) }.toSet()
    }
}
