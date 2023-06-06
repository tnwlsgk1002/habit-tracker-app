package com.bibbidi.habittracker.ui.model.habit.habitinfo

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
data class CheckHabitInfoUiModel(
    override val habitId: Long?,
    override val childId: Long?,
    override val name: String,
    override val emoji: String,
    @TypeParceler<LocalTime, LocalTimeParceler> override val alarmTime: LocalTime?,
    override val whenRun: String,
    @TypeParceler<Set<DayOfWeek>, DayOfWeekSetParceler> override val repeatsDayOfTheWeeks: Set<DayOfWeek>,
    @TypeParceler<LocalDate, LocalDateParceler> override val startDate: LocalDate
) : HabitInfoUiModel, Parcelable
