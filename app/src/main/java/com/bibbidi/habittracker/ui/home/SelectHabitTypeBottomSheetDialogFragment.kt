package com.bibbidi.habittracker.ui.home

import android.os.Bundle
import android.view.View
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetSelectHabitTypeBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitTypeUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectHabitTypeBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.bottom_sheet_select_habit_type) {

    private val binding by viewBinding(BottomSheetSelectHabitTypeBinding::bind)

    interface OnHabitTypeButtonClickListener {
        fun onHabitTypeButtonClick(type: HabitTypeUiModel)
    }

    private var listener: OnHabitTypeButtonClickListener? = null

    fun setOnHabitTypeButtonClickListener(listener: OnHabitTypeButtonClickListener) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.btnTodoType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitTypeUiModel.CheckType)
            dismiss()
        }
        binding.btnTimeType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitTypeUiModel.TimeType)
            dismiss()
        }
        binding.btnTrackType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitTypeUiModel.TrackType)
            dismiss()
        }
    }
}
