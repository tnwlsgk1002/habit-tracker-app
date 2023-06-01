package com.bibbidi.habittracker.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bibbidi.habittracker.R

class InputDataFromDialogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val tvTitle: TextView
    private val tvContent: TextView

    private var _titleText: CharSequence = ""
        set(value) {
            field = value
            tvTitle.text = value
            invalidate()
        }

    private var _contentText: CharSequence? = null
        set(value) {
            field = value
            tvContent.text = value ?: ""
            invalidate()
        }

    private var _background: Int = R.color.black
        set(value) {
            field = value
            setBackgroundResource(value)
            invalidate()
        }

    var contentText: CharSequence?
        get() = _contentText
        set(value) {
            _contentText = value
        }

    init {
        val view = inflate(context, R.layout.customview_input_data_from_dialog, this)
        tvTitle = view.findViewById(R.id.tv_title)
        tvContent = view.findViewById(R.id.tv_content)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InputDataFromDialogView,
            defStyleAttr,
            0
        )

        _titleText =
            typedArray.getText(R.styleable.InputDataFromDialogView_inputDataFromDialogView_titleText)
        _contentText =
            typedArray.getText(R.styleable.InputDataFromDialogView_inputDataFromDialogView_contentText)
        _background =
            typedArray.getResourceId(
                R.styleable.InputDataFromDialogView_inputDataFromDialogView_background,
                _background
            )
        typedArray.recycle()

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }
}
