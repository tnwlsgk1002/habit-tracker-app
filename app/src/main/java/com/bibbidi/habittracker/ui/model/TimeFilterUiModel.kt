package com.bibbidi.habittracker.ui.model

import com.vanniktech.ui.Parcelable
import com.vanniktech.ui.Parcelize

@Parcelize
enum class TimeFilterUiModel : Parcelable {
    MORNING,
    AFTERNOON,
    EVENING
}
