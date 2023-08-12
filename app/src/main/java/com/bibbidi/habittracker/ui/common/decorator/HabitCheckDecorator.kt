package com.bibbidi.habittracker.ui.common.decorator

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class HabitCheckDecorator(context: Context, private val habitWithLogs: HabitWithLogsUiModel) : DayViewDecorator {

    val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.inset_calendar_day)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return habitWithLogs.habitLogs[day?.asLocalDate()]?.isCompleted ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        drawable ?: return
        view?.setBackgroundDrawable(drawable)
    }
}
