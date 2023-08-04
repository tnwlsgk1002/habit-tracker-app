package com.bibbidi.habittracker.ui.binding

import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.decoration.HabitCheckDecorator
import com.bibbidi.habittracker.ui.common.decoration.HabitDisableDecorator
import com.bibbidi.habittracker.ui.common.decoration.HabitMemoDecorator
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import org.threeten.bp.LocalDate

@BindingAdapter("bind:logs")
fun setLogs(calendarView: MaterialCalendarView, logs: UiState<HabitWithLogsUiModel>) {
    calendarView.removeDecorators()
    when (logs) {
        is UiState.Success -> {
            calendarView.addDecorators(
                HabitCheckDecorator(calendarView.context, logs.data),
                HabitMemoDecorator(calendarView.context, logs.data),
                HabitDisableDecorator(logs.data)
            )
        }
        else -> {}
    }
}

@BindingAdapter("bind:is_localization_format")
fun setLocalizationFormat(
    calendarView: MaterialCalendarView,
    isFormatter: Boolean = false
) {
    if (isFormatter) {
        calendarView.context.resources.run {
            calendarView.setTitleFormatter(MonthArrayTitleFormatter(getTextArray(R.array.custom_month)))
            calendarView.setWeekDayFormatter(ArrayWeekDayFormatter(getTextArray(R.array.short_weekdays)))
        }
    }
}

@BindingAdapter("bind:mcv_selectionMode")
fun setSelectionMode(
    calendarView: MaterialCalendarView,
    mode: Int
) {
    calendarView.selectionMode = mode
}

@BindingAdapter("bind:onMonthChange")
fun setMonthChange(
    calendarView: MaterialCalendarView,
    listener: (LocalDate) -> Unit
) {
    calendarView.setOnMonthChangedListener { _, date ->
        listener.invoke(date.asLocalDate())
    }
}
