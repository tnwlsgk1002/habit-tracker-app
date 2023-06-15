package com.bibbidi.habittracker.ui.binding

import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.customview.InputDataFromDialogView
import com.bibbidi.habittracker.utils.getStringResource
import com.bibbidi.habittracker.utils.toGoalTimeString
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("bind:bind_contentText")
fun setContentText(
    inputDataFromDialogView: InputDataFromDialogView,
    text: String?
) {
    inputDataFromDialogView.contentText = text ?: ""
}

@BindingAdapter("bind:bind_contentText")
fun setContentText(
    inputDataFromDialogView: InputDataFromDialogView,
    dayOfWeeks: Set<DayOfWeek>
) {
    inputDataFromDialogView.contentText =
        dayOfWeeks.sortedBy { it.ordinal }.joinToString(separator = ",") {
            it.getStringResource(inputDataFromDialogView.context)
        }
}

@BindingAdapter("bind:bind_contentText")
fun setContentText(
    inputDataFromDialogView: InputDataFromDialogView,
    duration: Duration
) {
    inputDataFromDialogView.contentText = duration.toGoalTimeString()
}

@BindingAdapter("bind:bind_contentText")
fun setContentText(
    inputDataFromDialogView: InputDataFromDialogView,
    localDate: LocalDate
) {
    inputDataFromDialogView.contentText = localDate.toString()
}

@BindingAdapter("bind:bind_contentText")
fun setContentText(
    inputDataFromDialogView: InputDataFromDialogView,
    localTime: LocalTime?
) {
    inputDataFromDialogView.contentText =
        localTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}
