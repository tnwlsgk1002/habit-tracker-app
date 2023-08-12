package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetDatePickerBinding
import com.bibbidi.habittracker.ui.common.decorator.SelectorDecorator
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.parcer.LocalDateParceler
import com.bibbidi.habittracker.utils.Constants.DATE_PICKER_PARAM_KEY
import com.bibbidi.habittracker.utils.asCalendarDay
import com.bibbidi.habittracker.utils.asLocalDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class DatePickerBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_date_picker) {

    companion object {

        @Parcelize
        private data class DatePickerParam(
            @TypeParceler<LocalDate, LocalDateParceler>
            val date: LocalDate,
            @TypeParceler<LocalDate, LocalDateParceler>
            val minDate: LocalDate?,
            @TypeParceler<LocalDate, LocalDateParceler>
            val maxDate: LocalDate?
        ) : Parcelable

        fun newInstance(
            date: LocalDate,
            minDate: LocalDate? = null,
            maxDate: LocalDate? = null,
            onNegativeListener: (() -> Unit)? = null,
            onPositiveListener: ((LocalDate) -> Unit)? = null
        ): DatePickerBottomSheet {
            val args = Bundle().apply {
                putParcelable(DATE_PICKER_PARAM_KEY, DatePickerParam(date, minDate, maxDate))
            }

            return DatePickerBottomSheet().apply {
                arguments = args
                setOnNegativeListener(onNegativeListener)
                setOnPositiveListener(onPositiveListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetDatePickerBinding::bind)

    private var initLocalDate: LocalDate = LocalDate.now()
    private var minDate: LocalDate? = null
    private var maxDate: LocalDate? = null

    private val selectedLocalDate: LocalDate
        get() = binding.calendarView.selectedDate.asLocalDate()

    private var onNegativeListener: (() -> Unit)? = null
    private var onPositiveListener: ((LocalDate) -> Unit)? = null

    fun setOnPositiveListener(listener: ((LocalDate) -> Unit)?) {
        onPositiveListener = listener
    }

    fun setOnNegativeListener(listener: (() -> Unit)?) {
        onNegativeListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments?.getParcelable<DatePickerParam>(DATE_PICKER_PARAM_KEY)
        initLocalDate = args?.date ?: initLocalDate
        minDate = args?.minDate
        maxDate = args?.maxDate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMaterialCalendarView()
        setUpListener()
    }

    private fun initMaterialCalendarView() {
        binding.calendarView.run {
            state().edit()
                .setMinimumDate(minDate?.asCalendarDay())
                .setMaximumDate(maxDate?.asCalendarDay())
                .commit()

            setOnDateChangedListener { _, date, _ ->
                removeDecorators()
                addDecorator(SelectorDecorator(context, date))
            }
            selectedDate = initLocalDate.asCalendarDay()
            addDecorator(SelectorDecorator(context, selectedDate))
        }
    }

    private fun setUpListener() {
        binding.run {
            btnCancel.setOnClickListener {
                onNegativeListener?.invoke()
                dismiss()
            }
            btnOk.setOnClickListener {
                onPositiveListener?.invoke(selectedLocalDate)
                dismiss()
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }
}
