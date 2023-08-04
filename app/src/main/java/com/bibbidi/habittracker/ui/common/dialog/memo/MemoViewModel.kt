package com.bibbidi.habittracker.ui.common.dialog.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor() : ViewModel() {

    val memoFlow = MutableStateFlow<String?>(null)

    private val _event = MutableEventFlow<MemoEvent>()
    val event = _event.asEventFlow()

    fun deleteMemo() {
        viewModelScope.launch {
            _event.emit(MemoEvent.DeleteEvent)
        }
    }

    fun saveMemo() {
        viewModelScope.launch {
            _event.emit(MemoEvent.SaveEvent(memoFlow.value))
        }
    }

    fun close() {
        viewModelScope.launch {
            _event.emit(MemoEvent.CloseEvent)
        }
    }
}
