package com.bibbidi.myrootineclone.ui.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bibbidi.myrootineclone.R

class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _isToday: Boolean = false
        set(value) {
            field = value

            ivToday.visibility = when (value) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }

    private var _checked: Boolean = false
        set(value) {
            field = value

            cvBackground.setCardBackgroundColor(
                when (value) {
                    true -> ContextCompat.getColor(context, R.color.gray_07)
                    false -> Color.TRANSPARENT
                }
            )

            tvDayOfTheWeek.setTextColor(
                when (value) {
                    true -> ContextCompat.getColor(context, R.color.white)
                    false -> Color.BLACK
                }
            )
        }

    private var _dayOfTheWeek: DayOfTheWeek = DayOfTheWeek.SUN
        set(value) {
            field = value

            tvDayOfTheWeek.text = when (value) {
                DayOfTheWeek.SUN -> context.getString(R.string.sunday)
                DayOfTheWeek.MON -> context.getString(R.string.monday)
                DayOfTheWeek.TUE -> context.getString(R.string.tuesday)
                DayOfTheWeek.WED -> context.getString(R.string.wednesday)
                DayOfTheWeek.THU -> context.getString(R.string.thursday)
                DayOfTheWeek.FRI -> context.getString(R.string.friday)
                DayOfTheWeek.SAT -> context.getString(R.string.saturday)
            }
        }

    private var _dayOfTheMonth: Int = 1
        set(value) {
            field = value
            tvDayOfTheMonth.text = value.toString()
        }

    private var _dateState: DateState = DateState.GRAY
        set(value) {
            field = value

            val color = ContextCompat.getColor(
                context,
                when (value) {
                    DateState.GRAY -> R.color.gray_02
                    DateState.RED -> R.color.red_03
                    DateState.GREEN -> R.color.green_03
                    DateState.YELLOW -> R.color.yellow_03
                }
            )

            bgToday.setColor(color)
            bgDayOfTheMonth.setColor(color)
        }

    var isToday: Boolean
        get() = _isToday
        set(value) {
            _isToday = value
        }

    var checked: Boolean
        get() = _checked
        set(value) {
            _checked = value
        }

    var dayOfTheWeek: DayOfTheWeek
        get() = _dayOfTheWeek
        set(value) {
            _dayOfTheWeek = value
        }

    var dayOfTheMonth: Int
        get() = _dayOfTheMonth
        set(value) {
            _dayOfTheMonth = value
        }

    var dateState: DateState
        get() = _dateState
        set(value) {
            _dateState = value
        }

    private val cvBackground: CardView
    private val tvDayOfTheWeek: TextView
    private val ivToday: ImageView
    private val bgToday: GradientDrawable
    private val bgDayOfTheMonth: GradientDrawable
    private val tvDayOfTheMonth: TextView

    init {
        val view = inflate(context, R.layout.customview_date_view, this)

        cvBackground = view.findViewById(R.id.cv_background)
        tvDayOfTheWeek = view.findViewById(R.id.tv_day_of_week)
        ivToday = view.findViewById(R.id.iv_today)
        bgToday = ivToday.background as GradientDrawable
        bgDayOfTheMonth = view.findViewById<View?>(R.id.iv_day_of_month).background as GradientDrawable
        tvDayOfTheMonth = view.findViewById(R.id.tv_day_of_month)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.DateView,
            defStyleAttr,
            0
        )

        _isToday = typedArray.getBoolean(R.styleable.DateView_isToday, false)
        _checked = typedArray.getBoolean(R.styleable.DateView_checked, false)
        _dayOfTheWeek = DayOfTheWeek.values()[
            typedArray.getInt(
                R.styleable.DateView_dayOfTheWeek,
                DayOfTheWeek.SUN.ordinal
            )
        ]
        _dayOfTheMonth = typedArray.getInt(R.styleable.DateView_dayOfTheMonth, 1)
        typedArray.recycle()
    }
}
