package com.bibbidi.habittracker.ui.binding

import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.customview.DateView
import org.threeten.bp.LocalDate

@BindingAdapter("bind:date")
fun setDate(dateView: DateView, date: LocalDate) {
    dateView.run {
        dayOfTheMonth = date.dayOfMonth
        dayOfWeek = date.dayOfWeek
    }
}
