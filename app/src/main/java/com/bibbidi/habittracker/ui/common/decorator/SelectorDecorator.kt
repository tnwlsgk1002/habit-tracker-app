package com.bibbidi.habittracker.ui.common.decorator

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bibbidi.habittracker.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectorDecorator(context: Context, private val selectedDay: CalendarDay) : DayViewDecorator {

    private val drawable: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.inset_calendar_day
    )

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(selectedDay) ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        drawable ?: return
        view?.setSelectionDrawable(drawable)
    }
}
