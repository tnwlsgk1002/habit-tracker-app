package com.bibbidi.habittracker.ui.common

interface SendEventListener<T : Any?> {

    fun sendEvent(value: T)
}
