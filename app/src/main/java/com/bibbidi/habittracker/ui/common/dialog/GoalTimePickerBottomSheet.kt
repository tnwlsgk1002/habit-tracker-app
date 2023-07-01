package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputGoalTimeBinding
import com.bibbidi.habittracker.ui.common.Constants.HOUR_KEY
import com.bibbidi.habittracker.ui.common.Constants.MINUTE_KEY
import com.bibbidi.habittracker.ui.common.viewBinding
import com.bibbidi.habittracker.utils.toFixToTwoDigits
import com.bibbidi.habittracker.utils.toTwoDigits
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GoalTimePickerBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_input_goal_time) {

    companion object {

        fun newInstance(
            hour: Int,
            minute: Int,
            onCancelListener: (Int, Int) -> Unit
        ): GoalTimePickerBottomSheet {
            val args = Bundle().apply {
                putInt(HOUR_KEY, hour)
                putInt(MINUTE_KEY, minute)
            }
            return GoalTimePickerBottomSheet().apply {
                arguments = args
                setOnCancelListener(onCancelListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetInputGoalTimeBinding::bind)

    private var hour: Int = 0
    private var minute: Int = 0

    private var onCancelListener: ((Int, Int) -> Unit)? = null

    fun setOnCancelListener(listener: ((Int, Int) -> Unit)?) {
        this.onCancelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hour = arguments?.getInt(HOUR_KEY) ?: hour
        minute = arguments?.getInt(MINUTE_KEY) ?: minute
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        with(binding.editTextHour) {
            setText(hour.toTwoDigits())
            toFixToTwoDigits()
        }
        with(binding.editTextMinute) {
            setText(minute.toTwoDigits())
            toFixToTwoDigits()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.invoke(
            binding.editTextHour.text.toString().toInt(),
            binding.editTextMinute.text.toString().toInt()
        )
    }
}
