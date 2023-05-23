package com.bibbidi.habittracker.data.entity.converters

import androidx.room.TypeConverter
import org.threeten.bp.Duration

object DurationConverter {

    @TypeConverter
    fun fromDuration(value: Duration?): Long? {
        return value?.toMillis()
    }

    @TypeConverter
    fun toDuration(value: Long?): Duration? {
        return value?.let { Duration.ofMillis(it) }
    }
}
