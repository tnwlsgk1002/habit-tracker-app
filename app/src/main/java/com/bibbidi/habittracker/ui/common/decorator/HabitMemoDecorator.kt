package com.bibbidi.habittracker.ui.common.decorator

import android.content.Context
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.getPrimaryColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class HabitMemoDecorator(
    private val context: Context,
    private val habitWithLogs: HabitWithLogsUiModel
) : DayViewDecorator {

    companion object {
        private const val SPAN_RADIUS = 6F
    }

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return habitWithLogs.habitLogs[day?.asLocalDate()]?.memo?.isNotBlank() ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(SPAN_RADIUS, getPrimaryColor(context)))
    }
}
