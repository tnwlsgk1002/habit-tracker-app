package com.bibbidi.habittracker.ui.binding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.model.ProgressUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitResultUiModel
import com.bibbidi.habittracker.utils.getStringResource
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("bind:timeText")
fun setLocalTimeText(textView: TextView, localTime: LocalTime?) {
    textView.text = localTime?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: ""
}

@BindingAdapter("bind:dateText")
fun setLocalDateText(
    textView: TextView,
    localDate: LocalDate
) {
    textView.text = localDate.toString()
}

@BindingAdapter("bind:dayOfWeekSetText")
fun setDayOfWeekText(
    textView: TextView,
    dayOfWeeks: Set<DayOfWeek>
) {
    textView.text = dayOfWeeks.sortedBy { it.ordinal }.joinToString(separator = "/") {
        it.getStringResource(textView.context, true)
    }
}

@BindingAdapter("bind:dayOfWeekShortText")
fun setDayOfWeekShortText(
    textView: TextView,
    dayOfWeek: DayOfWeek
) {
    textView.text = dayOfWeek.getStringResource(textView.context, true)
}

@BindingAdapter("bind:dayOfWeekText")
fun setDayOfWeekText(
    textView: TextView,
    dayOfWeek: DayOfWeek
) {
    textView.text = dayOfWeek.getStringResource(textView.context, false)
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

@BindingAdapter("bind:progressText")
fun setProgressText(
    textView: TextView,
    progressUiModel: ProgressUiModel
) {
    textView.text = textView.context.getString(
        R.string.progress,
        progressUiModel.finishCount,
        progressUiModel.total
    )
}

@BindingAdapter("bind:startDateText")
fun setStartDate(
    textView: TextView,
    date: LocalDate?
) {
    textView.text = date?.let {
        textView.context.getString(
            R.string.start_date_message,
            it.year,
            it.monthValue,
            it.dayOfMonth
        )
    } ?: "-"
}

@BindingAdapter("bind:cntNumberText")
fun setCntNumber(
    textView: TextView,
    result: UiState<HabitResultUiModel>?
) {
    textView.text = when (result) {
        is UiState.Success -> result.data.cntNumber.toString()
        else -> "-"
    }
}

@BindingAdapter("bind:bestNumberText")
fun setBestNumber(
    textView: TextView,
    result: UiState<HabitResultUiModel>?
) {
    textView.text = when (result) {
        is UiState.Success -> result.data.bestNumber.toString()
        else -> "-"
    }
}
