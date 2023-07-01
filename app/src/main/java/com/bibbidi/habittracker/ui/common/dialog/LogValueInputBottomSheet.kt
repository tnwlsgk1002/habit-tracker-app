package com.bibbidi.habittracker.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputTrackValueBinding
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.common.viewBinding
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogValueInputBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_input_track_value) {

    companion object {

        fun newInstance(
            log: TrackHabitLogUiModel,
            onSavedListener: ((value: Long?) -> Unit)?
        ): LogValueInputBottomSheet {
            val args = Bundle().apply {
                putParcelable(Constants.HABIT_LOG_KEY, log)
            }
            return LogValueInputBottomSheet().apply {
                arguments = args
                setOnSavedListener(onSavedListener)
            }
        }
    }

    private var onSavedListener: ((Long?) -> Unit)? = null

    fun setOnSavedListener(listener: ((value: Long?) -> Unit)?) {
        onSavedListener = listener
    }

    private val binding by viewBinding(BottomSheetInputTrackValueBinding::bind)

    private lateinit var log: TrackHabitLogUiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log = arguments?.getParcelable(Constants.HABIT_LOG_KEY)
            ?: error("LogValueInputBottomSheet argument is invalid.")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.log = log
        binding.lifecycleOwner = viewLifecycleOwner
        binding.btnSave.setOnClickListener {
            onSavedListener?.invoke(binding.etInput.text.toString().toLongOrNull())
            dismiss()
        }
    }
}
