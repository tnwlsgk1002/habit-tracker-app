package com.bibbidi.habittracker.ui.common.dialog.memo

sealed class MemoEvent {

    object CloseEvent : MemoEvent()

    object DeleteEvent : MemoEvent()

    data class SaveEvent(val memo: String?) : MemoEvent()
}
