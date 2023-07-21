package com.bibbidi.habittracker.ui.common.dialog

import android.os.Bundle
import android.view.View
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputEmojiBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.utils.getBackgroundColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.listeners.OnEmojiClickListener

class EmojiPickerBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_input_emoji) {

    companion object {

        fun newInstance(
            onEmojiClickListener: OnEmojiClickListener
        ): EmojiPickerBottomSheet {
            return EmojiPickerBottomSheet().apply {
                setOnEmojiClickListener(onEmojiClickListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetInputEmojiBinding::bind)

    private var onEmojiClickListener: OnEmojiClickListener? = null

    fun setOnEmojiClickListener(listener: OnEmojiClickListener) {
        this.onEmojiClickListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEmojiView()
    }

    private fun setUpEmojiView() {
        val theming = dialog?.window?.decorView?.getBackgroundColor()?.let {
            EmojiTheming.from(requireContext()).copy(backgroundColor = it)
        } ?: EmojiTheming.from(requireContext())
        binding.emojiView.setUp(
            rootView = binding.root,
            onEmojiClickListener = onEmojiClickListener,
            onEmojiBackspaceClickListener = null,
            editText = null,
            theming = theming
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.emojiView.tearDown()
    }
}
