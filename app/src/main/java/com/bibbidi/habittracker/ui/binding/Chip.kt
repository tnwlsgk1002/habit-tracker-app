package com.bibbidi.habittracker.ui.binding

import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.utils.getStringResource
import com.google.android.material.chip.Chip
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("bind:timeText")
fun setLocalTimeText(chip: Chip, localTime: LocalTime?) {
    chip.text = localTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}

@BindingAdapter("bind:dayOfWeekSetText")
fun setDayOfWeekText(
    chip: Chip,
    dayOfWeeks: Set<DayOfWeek>?
) {
    chip.text = when (dayOfWeeks) {
        null -> "-"
        DayOfWeek.values().toSet() -> chip.context.getString(R.string.everyday)
        setOf(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY) -> chip.context.getString(R.string.weekend)
        setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.FRIDAY
        ) -> chip.context.getString(R.string.everyday)
        else -> dayOfWeeks.sortedBy { it.ordinal }.joinToString(separator = "/") {
            it.getStringResource(chip.context)
        }
    }
}
