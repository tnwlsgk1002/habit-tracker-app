package com.bibbidi.habittracker.ui.model.habit.log

import android.os.Parcelable
import com.bibbidi.habittracker.ui.common.parcer.LocalDateParceler
import com.bibbidi.habittracker.ui.common.parcer.LocalTimeParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Parcelize
data class TrackHabitLogUiModel(
    override val logId: Long?,
    override val habitId: Long?,
    override val habitTypeId: Long?,
    override val emoji: String,
    override val name: String,
    @TypeParceler<LocalDate, LocalDateParceler>
    override val date: LocalDate,
    @TypeParceler<LocalTime, LocalTimeParceler>
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val value: Long?
) : HabitLogUiModel, Parcelable
