package com.bibbidi.habittracker.ui.common.dialog.colorpicker

import com.bibbidi.habittracker.ui.model.ColorUiModel

sealed class ColorPickerEvent {

    data class SaveEvent(val color: ColorUiModel?) : ColorPickerEvent()
}
