package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bibbidi.habittracker.databinding.BottomSheetInputGoalTimeBinding
import com.bibbidi.habittracker.utils.toFixToTwoDigits
import com.bibbidi.habittracker.utils.toTwoDigits
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GoalTimePickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val HOUR_KEY = "HOUR"
        private const val MINUTE_KEY = "MINUTE"

        fun newInstance(
            hour: Int,
            minute: Int,
            onCancelListener: (Int, Int) -> Unit
        ): GoalTimePickerBottomSheetDialogFragment {
            val args = Bundle().apply {
                putInt(HOUR_KEY, hour)
                putInt(MINUTE_KEY, minute)
            }
            return GoalTimePickerBottomSheetDialogFragment().apply {
                arguments = args
                setOnCancelListener(onCancelListener)
            }
        }
    }

    private var _binding: BottomSheetInputGoalTimeBinding? = null

    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInputGoalTimeBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
