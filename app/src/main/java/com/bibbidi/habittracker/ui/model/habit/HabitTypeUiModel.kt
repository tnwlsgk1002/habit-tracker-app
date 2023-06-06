package com.bibbidi.habittracker.ui.model.habit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HabitTypeUiModel : Parcelable {
    CheckType,
    TimeType,
    TrackType
}
