package com.bibbidi.habittracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bibbidi.habittracker.databinding.BottomSheetSelectHabitTypeBinding
import com.bibbidi.habittracker.ui.model.habit.HabitType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectHabitTypeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSelectHabitTypeBinding? = null

    private val binding get() = _binding!!

    interface OnHabitTypeButtonClickListener {
        fun onHabitTypeButtonClick(type: HabitType)
    }

    private var listener: OnHabitTypeButtonClickListener? = null

    fun setOnHabitTypeButtonClickListener(listener: OnHabitTypeButtonClickListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSelectHabitTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.btnTodoType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitType.CheckType)
            dismiss()
        }
        binding.btnTimeType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitType.TimeType)
            dismiss()
        }
        binding.btnTrackType.setOnClickListener {
            listener?.onHabitTypeButtonClick(HabitType.TrackType)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
