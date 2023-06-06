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
sealed interface HabitInfoUiModel : Parcelable {
    val habitId: Long?
    val childId: Long?
    val name: String
    val emoji: String

    @TypeParceler<LocalTime, LocalTimeParceler>
    val alarmTime: LocalTime?
    val whenRun: String

    @TypeParceler<Set<DayOfWeek>, DayOfWeekSetParceler>
    val repeatsDayOfTheWeeks: Set<DayOfWeek>

    @TypeParceler<LocalDate, LocalDateParceler>
    val startDate: LocalDate
}
