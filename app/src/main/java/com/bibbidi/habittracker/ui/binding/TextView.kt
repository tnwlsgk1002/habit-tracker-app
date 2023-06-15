package com.bibbidi.habittracker.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("bind:bind_contentText")
fun setLocalTimeText(textView: TextView, localTime: LocalTime?) {
    textView.text = localTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}
