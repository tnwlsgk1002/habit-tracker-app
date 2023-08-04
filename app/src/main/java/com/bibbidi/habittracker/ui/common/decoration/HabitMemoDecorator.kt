package com.bibbidi.habittracker.ui.common.decoration

import android.content.Context
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.getPrimaryColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class HabitMemoDecorator(private val context: Context, private val habitWithLogs: HabitWithLogsUiModel) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return habitWithLogs.habitLogs[day?.asLocalDate()]?.memo?.isNotBlank() ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(6F, getPrimaryColor(context)))
    }
}
