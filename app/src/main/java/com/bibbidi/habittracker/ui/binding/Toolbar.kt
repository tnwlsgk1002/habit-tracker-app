package com.bibbidi.habittracker.ui.binding

import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.R
import com.google.android.material.appbar.MaterialToolbar
import org.threeten.bp.LocalDate

@BindingAdapter("bind:title")
fun setTitle(view: MaterialToolbar, date: LocalDate) {
    view.title = view.context.getString(R.string.date, date.year, date.month.value) + if (LocalDate.now().isEqual(date)) {
        "(" + view.context.getString(R.string.today) + ")"
    } else {
        ""
    }
}
