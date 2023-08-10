package com.bibbidi.habittracker.ui.common.dialog.colorpicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.usecase.GetAllColorsUseCase
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.asUiModel
import com.bibbidi.habittracker.ui.model.ColorItem
import com.bibbidi.habittracker.ui.model.ColorUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ColorPickerViewModel @AssistedInject constructor(
    @Assisted color: ColorUiModel?,
    getAllColorUseCase: GetAllColorsUseCase
) : ViewModel() {

    @AssistedFactory
    interface ColorAssistedFactory {
        fun create(color: ColorUiModel?): ColorPickerViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: ColorAssistedFactory,
            color: ColorUiModel?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return assistedFactory.create(color) as T
            }
        }
    }

    private val colors = getAllColorUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, DBResult.Loading)

    private val _checkedColor: MutableStateFlow<ColorUiModel?> = MutableStateFlow(color)
    val checkedColor = _checkedColor.asStateFlow()

    private val _event = MutableEventFlow<ColorPickerEvent>()
    val event = _event.asEventFlow()

    val colorItems = combine(
        colors,
        checkedColor
    ) { colorsResult, checkedColor ->
        when (colorsResult) {
            is DBResult.Success -> colorsResult.data.map { ColorItem(it.asUiModel(), checked = it.id == checkedColor?.id) }
            else -> emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setColor(color: ColorUiModel?) {
        _checkedColor.value = color
    }

    fun deleteColor() {
        setColor(null)
    }

    fun saveColor() {
        viewModelScope.launch {
            _event.emit(ColorPickerEvent.SaveEvent(checkedColor.value))
        }
    }
}
