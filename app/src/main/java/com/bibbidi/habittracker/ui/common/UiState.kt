package com.bibbidi.habittracker.ui.common

sealed class UiState<out Data> {
    data class Success<Data>(val data: Data) : UiState<Data>()

    object Loading : UiState<Nothing>()

    object Empty : UiState<Nothing>()

    fun contentEquals(other: UiState<*>): Boolean {
        return when {
            this is Success && other is Success -> this.data == other.data
            this is Loading && other is Loading -> true
            else -> false
        }
    }
}
