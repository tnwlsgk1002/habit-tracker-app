package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputWhenDataBinding
import com.bibbidi.habittracker.ui.common.Constants.WHEN_RUN_KEY
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class WhenRunInputBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_input_when_data) {

    companion object {

        fun newInstance(
            whenRun: String,
            onCancelListener: (String) -> Unit
        ): WhenRunInputBottomSheet {
            val args = Bundle().apply {
                putString(WHEN_RUN_KEY, whenRun)
            }

            return WhenRunInputBottomSheet().apply {
                arguments = args
                setOnCancelListener(onCancelListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetInputWhenDataBinding::bind)

    private var whenRun: String = ""

    private var onCancelListener: ((String) -> Unit)? = null

    fun setOnCancelListener(listener: ((String) -> Unit)?) {
        this.onCancelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whenRun = arguments?.getString(WHEN_RUN_KEY) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewData()
        setUpClickListener()
    }

    private fun setUpViewData() {
        binding.etInput.setText(whenRun)
    }

    private fun setUpClickListener() {
        binding.chipGroupExample.children.forEach { chipView ->
            val chip = chipView as? Chip
            chip?.setOnClickListener {
                binding.etInput.setText(chip.text)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.invoke(binding.etInput.text.toString())
    }
}
