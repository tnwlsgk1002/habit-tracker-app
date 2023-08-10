package com.bibbidi.habittracker.ui.common.dialog.colorpicker

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetColorPickerBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.ColorUiModel
import com.bibbidi.habittracker.utils.Constants.COLOR_KEY
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ColorPickerBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_color_picker) {

    companion object {

        fun newInstance(
            color: ColorUiModel?,
            onSaveListener: (ColorUiModel?) -> Unit
        ): ColorPickerBottomSheet {
            val args = Bundle().apply {
                putParcelable(COLOR_KEY, color)
            }

            return ColorPickerBottomSheet().apply {
                arguments = args
                setOnSaveListener(onSaveListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetColorPickerBinding::bind)

    private val initColor
        get() = arguments?.getParcelable<ColorUiModel>(COLOR_KEY)

    @Inject
    lateinit var colorViewModelFactory: ColorPickerViewModel.ColorAssistedFactory

    private val viewModel by viewModels<ColorPickerViewModel> {
        ColorPickerViewModel.provideFactory(colorViewModelFactory, initColor)
    }

    private var onSaveListener: (((ColorUiModel?) -> Unit)?) = null

    fun setOnSaveListener(listener: ((ColorUiModel?) -> Unit)?) {
        onSaveListener = listener
    }

    private val colorsAdapter by lazy {
        ColorsAdapter { item ->
            viewModel.setColor(item.colorUiModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
        collectEvent()
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        binding.colorsAdapter = colorsAdapter
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is ColorPickerEvent.SaveEvent -> {
                        onSaveListener?.invoke(event.color)
                        dismiss()
                    }
                }
            }
        }
    }
}
