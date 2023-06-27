package com.bibbidi.habittracker.ui.model.habit.log

import android.os.Parcelable
import com.bibbidi.habittracker.ui.common.parcer.LocalDateParceler
import com.bibbidi.habittracker.ui.common.parcer.LocalTimeParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Parcelize
sealed interface HabitLogUiModel : Parcelable {
    val logId: Long?
    val habitId: Long?
    val habitTypeId: Long?
    val emoji: String
    val name: String

    @TypeParceler<LocalDate, LocalDateParceler>
    val date: LocalDate

    @TypeParceler<LocalTime, LocalTimeParceler>
    val alarmTime: LocalTime?
    val whenRun: String
}
