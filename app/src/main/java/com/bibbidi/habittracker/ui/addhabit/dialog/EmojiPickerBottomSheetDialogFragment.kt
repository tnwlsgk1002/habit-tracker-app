package com.bibbidi.habittracker.ui.addhabit.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bibbidi.habittracker.databinding.BottomSheetInputEmojiBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vanniktech.emoji.listeners.OnEmojiClickListener

class EmojiPickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {

        fun newInstance(
            onEmojiClickListener: OnEmojiClickListener
        ): EmojiPickerBottomSheetDialogFragment {
            return EmojiPickerBottomSheetDialogFragment().apply {
                setOnEmojiClickListener(onEmojiClickListener)
            }
        }
    }

    private var _binding: BottomSheetInputEmojiBinding? = null

    private val binding get() = _binding!!

    private var onEmojiClickListener: OnEmojiClickListener? = null

    fun setOnEmojiClickListener(listener: OnEmojiClickListener) {
        this.onEmojiClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInputEmojiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpEmojiView()
    }

    private fun setUpEmojiView() {
        binding.emojiView.setUp(
            rootView = binding.root,
            onEmojiClickListener = onEmojiClickListener,
            onEmojiBackspaceClickListener = null,
            editText = null
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.emojiView.tearDown()
        _binding = null
    }
}
