package com.bibbidi.habittracker.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ColorAssets(
    @SerialName("id")
    val id: Long,
    @SerialName("hexCode")
    val hexCode: String
)
