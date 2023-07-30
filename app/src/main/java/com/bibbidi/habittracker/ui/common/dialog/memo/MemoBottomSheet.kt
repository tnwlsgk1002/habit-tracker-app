package com.bibbidi.habittracker.ui.common.dialog.memo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputMemoBinding
import com.bibbidi.habittracker.ui.common.Constants.MEMO_KEY
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemoBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_input_memo) {

    companion object {

        fun newInstance(
            memo: String?,
            onSaveListener: (String?) -> Unit,
            onDeleteListener: () -> Unit
        ): MemoBottomSheet {
            val args = Bundle().apply {
                putString(MEMO_KEY, memo)
            }

            return MemoBottomSheet().apply {
                arguments = args
                setOnSaveListener(onSaveListener)
                setOnDeleteListener(onDeleteListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetInputMemoBinding::bind)

    private val viewModel: MemoViewModel by viewModels()

    private var onSaveListener: ((String?) -> Unit)? = null
    private var onDeleteListener: (() -> Unit)? = null

    fun setOnSaveListener(listener: ((String?) -> Unit)?) {
        onSaveListener = listener
    }

    fun setOnDeleteListener(listener: (() -> Unit)?) {
        onDeleteListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.memoFlow.value = viewModel.memoFlow.value ?: arguments?.getString(MEMO_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
        collectEvent()
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collect() { event ->
                when (event) {
                    is MemoEvent.SaveEvent -> {
                        onSaveListener?.invoke(event.memo)
                        dismiss()
                    }
                    MemoEvent.CloseEvent -> dismiss()
                    MemoEvent.DeleteEvent -> {
                        onDeleteListener?.invoke()
                        dismiss()
                    }
                }
            }
        }
    }
}
