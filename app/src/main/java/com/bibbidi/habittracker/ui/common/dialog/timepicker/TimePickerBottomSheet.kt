package com.bibbidi.habittracker.ui.common.dialog.timepicker

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetTimePickerBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.utils.Constants.HOUR_KEY
import com.bibbidi.habittracker.utils.Constants.MINUTE_KEY
import com.bibbidi.habittracker.utils.animateChange
import com.bibbidi.habittracker.utils.convertTo24HourFormat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalTime

@AndroidEntryPoint
class TimePickerBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_time_picker) {

    companion object {

        private const val MIN_HOUR = 1
        private const val MAX_HOUR = 12
        private const val MIN_MINUTE = 0
        private const val MAX_MINUTE = 59
        private const val AM = 0
        private const val PM = 1

        fun newInstance(
            time: LocalTime,
            onSaveListener: (LocalTime) -> Unit
        ): TimePickerBottomSheet {
            val args = Bundle().apply {
                putInt(HOUR_KEY, time.hour)
                putInt(MINUTE_KEY, time.minute)
            }

            return TimePickerBottomSheet().apply {
                arguments = args
                setOnSaveListener(onSaveListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetTimePickerBinding::bind)

    private var initLocalTime: LocalTime = LocalTime.now()

    private val localTime: LocalTime
        get() = convertTo24HourFormat(
            binding.npHour.value,
            binding.npMinute.value,
            binding.npAmpm.value == PM
        )

    private var onSaveListener: ((LocalTime) -> Unit)? = null
    fun setOnSaveListener(listener: ((LocalTime) -> Unit)?) {
        onSaveListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hour = arguments?.getInt(HOUR_KEY) ?: initLocalTime.hour
        val minute = arguments?.getInt(MINUTE_KEY) ?: initLocalTime.minute
        initLocalTime = LocalTime.of(hour, minute)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHour()
        initMinute()
        initAmPm()
        setUpListener()
    }

    private fun initHour() {
        binding.npHour.run {
            minValue = MIN_HOUR
            maxValue = MAX_HOUR
            value = initLocalTime.hour
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            val isChangeToMaxHour = { oldVal: Int, newVal: Int ->
                (oldVal == MAX_HOUR - 1 && newVal == MAX_HOUR)
            }

            val isChangeFromMaxHour = { oldVal: Int, newVal: Int ->
                (oldVal == MAX_HOUR && newVal == MAX_HOUR - 1)
            }

            setOnValueChangedListener { _, oldVal, newVal ->
                if (isChangeToMaxHour(oldVal, newVal) && isChangeFromMaxHour(oldVal, newVal)) {
                    binding.npAmpm.animateChange(binding.npAmpm.value == AM)
                }
            }
        }
    }

    private fun initMinute() {
        binding.npMinute.run {
            minValue = MIN_MINUTE
            maxValue = MAX_MINUTE
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            value = initLocalTime.minute
        }
    }

    private fun initAmPm() {
        binding.npAmpm.run {
            minValue = AM
            maxValue = PM
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            displayedValues = arrayOf(getString(R.string.am), getString(R.string.pm))
        }
    }

    private fun setUpListener() {
        binding.run {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnSave.setOnClickListener {
                onSaveListener?.invoke(localTime)
                dismiss()
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }
}
