package com.bibbidi.habittracker.ui.binding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.utils.getStringResource
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("bind:text")
fun setLocalTimeText(textView: TextView, localTime: LocalTime?) {
    textView.text = localTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}

@BindingAdapter("bind:text")
fun setLocalDateText(
    textView: TextView,
    localDate: LocalDate
) {
    textView.text = localDate.toString()
}

@BindingAdapter("bind:text")
fun setDayOfWeekText(
    textView: TextView,
    dayOfWeeks: Set<DayOfWeek>
) {
    textView.text = dayOfWeeks.sortedBy { it.ordinal }.joinToString(separator = "/") {
        it.getStringResource(textView.context)
    }
}

@BindingAdapter("bind:strikethrough")
fun setStrikethrough(
    textView: TextView,
    isStrikeThrough: Boolean
) {
    textView.paintFlags = if (isStrikeThrough) {
        textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
