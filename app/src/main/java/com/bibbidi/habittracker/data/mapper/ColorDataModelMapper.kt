package com.bibbidi.habittracker.data.mapper

import com.bibbidi.habittracker.data.model.habit.entity.ColorEntity
import com.bibbidi.habittracker.domain.model.ColorModel
object ColorDataModelMapper : DataModelMapper<ColorEntity, ColorModel> {

    override fun asData(domain: ColorModel) = ColorEntity(
        id = domain.id,
        hexCode = domain.hexCode
    )

    override fun asDomain(data: ColorEntity) = ColorModel(
        id = data.id,
        hexCode = data.hexCode
    )
}

fun ColorEntity.asDomain() = ColorDataModelMapper.asDomain(this)

fun ColorModel.asData() = ColorDataModelMapper.asData(this)
