package com.bibbidi.habittracker.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorUiModel(
    val id: Long,
    val hexCode: String
) : Parcelable
