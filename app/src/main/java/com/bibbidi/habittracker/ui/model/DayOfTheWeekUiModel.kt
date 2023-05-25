package com.bibbidi.habittracker.ui.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.bibbidi.habittracker.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class DayOfTheWeekUiModel(@StringRes val resId: Int) : Parcelable {
    SUN(R.string.sunday),
    MON(R.string.monday),
    TUE(R.string.tuesday),
    WED(R.string.wednesday),
    THU(R.string.thursday),
    FRI(R.string.friday),
    SAT(R.string.saturday)
}
