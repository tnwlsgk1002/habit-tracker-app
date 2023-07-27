package com.bibbidi.habittracker.data.model

sealed class DBResult<out T> {
    data class Success<T>(val data: T) : DBResult<T>()

    data class Error(val exception: Throwable) : DBResult<Nothing>()

    object Empty : DBResult<Nothing>()

    object Loading : DBResult<Nothing>()
}
