package com.bibbidi.habittracker.ui.model.habit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitWithLogUiModel(
    val habit: HabitUiModel,
    val habitLog: HabitLogUiModel
) : Parcelable
