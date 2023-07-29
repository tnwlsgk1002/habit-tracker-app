package com.bibbidi.habittracker.ui.model.habit

import android.os.Parcelable
import com.bibbidi.habittracker.ui.common.parcer.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.LocalDate

@Parcelize
data class HabitLogUiModel(
    val id: Long?,
    val habitId: Long?,
    @TypeParceler<LocalDate, LocalDateParceler>
    val date: LocalDate,
    val isCompleted: Boolean,
    val memo: String?
) : Parcelable
