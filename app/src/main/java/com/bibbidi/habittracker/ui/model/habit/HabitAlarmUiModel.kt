package com.bibbidi.habittracker.ui.model.habit

import android.os.Parcelable
import com.bibbidi.habittracker.ui.common.parcer.DayOfWeekSetParceler
import com.bibbidi.habittracker.ui.common.parcer.LocalDateParceler
import com.bibbidi.habittracker.ui.common.parcer.LocalTimeParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Parcelize
data class HabitAlarmUiModel(
    val habitId: Long?,
    val name: String,
    val emoji: String,
    @TypeParceler<LocalTime, LocalTimeParceler>
    val alarmTime: LocalTime?,
    @TypeParceler<Set<DayOfWeek>, DayOfWeekSetParceler>
    val repeatsDayOfTheWeeks: Set<DayOfWeek>,
    @TypeParceler<LocalDate, LocalDateParceler>
    val startDate: LocalDate
) : Parcelable
