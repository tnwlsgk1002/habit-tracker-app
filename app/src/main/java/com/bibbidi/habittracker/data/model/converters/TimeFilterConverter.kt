package com.bibbidi.habittracker.data.model.converters

import androidx.room.TypeConverter
import com.bibbidi.habittracker.domain.model.TimeFilter

object TimeFilterConverter {

    @TypeConverter
    fun fromTimeFilterSet(value: Set<TimeFilter>): String {
        return value.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toTimeFilterSet(value: String): Set<TimeFilter> {
        return value.split(",").map { TimeFilter.valueOf(it) }.toSet()
    }
}
