package com.bibbidi.habittracker.ui.binding

import android.content.res.ColorStateList
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.model.ColorUiModel
import com.bibbidi.habittracker.utils.asStateColor
import com.bibbidi.habittracker.utils.getColorSurfaceVariant
import com.google.android.material.card.MaterialCardView

@BindingAdapter("bind:cardBackgroundColor")
fun setCardBackgroundColor(cardView: MaterialCardView, color: ColorUiModel?) {
    val stateColor = color?.asStateColor() ?: ColorStateList.valueOf(getColorSurfaceVariant(cardView))
    cardView.setCardBackgroundColor(stateColor)
}
