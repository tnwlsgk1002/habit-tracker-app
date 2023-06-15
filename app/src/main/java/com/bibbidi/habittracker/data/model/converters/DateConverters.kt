package com.bibbidi.habittracker.data.model.converters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

object DateConverters {

    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val localTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.format(localDateFormatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        val value = value ?: return null
        return localDateFormatter.parse(value, LocalDate::from)
    }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.format(localTimeFormatter)
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        val value = value ?: return null
        return localTimeFormatter.parse(value, LocalTime::from)
    }
}
