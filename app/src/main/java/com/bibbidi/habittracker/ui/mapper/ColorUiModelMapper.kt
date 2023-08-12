package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.ColorModel
import com.bibbidi.habittracker.ui.model.ColorUiModel

object ColorUiModelMapper : UiModelMapper<ColorUiModel, ColorModel> {
    override fun asDomain(uiModel: ColorUiModel) = ColorModel(
        id = uiModel.id,
        hexCode = uiModel.hexCode
    )

    override fun asUiModel(domain: ColorModel) = ColorUiModel(
        id = domain.id,
        hexCode = domain.hexCode
    )
}

fun ColorUiModel.asDomain() = ColorUiModelMapper.asDomain(this)

fun ColorModel.asUiModel() = ColorUiModelMapper.asUiModel(this)
