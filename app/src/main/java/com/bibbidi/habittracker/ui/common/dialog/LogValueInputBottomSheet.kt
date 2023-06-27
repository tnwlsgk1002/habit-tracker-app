package com.bibbidi.habittracker.ui.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bibbidi.habittracker.databinding.BottomSheetInputTrackValueBinding
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogValueInputBottomSheet : BottomSheetDialogFragment() {

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

    private var _binding: BottomSheetInputTrackValueBinding? = null

    private val binding get() = _binding!!

    private lateinit var log: TrackHabitLogUiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        log = arguments?.getParcelable(Constants.HABIT_LOG_KEY)
            ?: error("LogValueInputBottomSheet argument is invalid.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInputTrackValueBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
