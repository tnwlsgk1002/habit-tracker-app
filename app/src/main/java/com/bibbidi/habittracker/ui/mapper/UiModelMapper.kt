package com.bibbidi.habittracker.ui.mapper

interface UiModelMapper<UiModel, Domain> {

    fun asUiModel(domain: Domain): UiModel

    fun asDomain(uiModel: UiModel): Domain
}
