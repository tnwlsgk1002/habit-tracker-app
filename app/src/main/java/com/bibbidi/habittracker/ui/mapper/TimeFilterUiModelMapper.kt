package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.TimeFilter
import com.bibbidi.habittracker.ui.model.TimeFilterUiModel

object TimeFilterUiModelMapper : UiModelMapper<TimeFilterUiModel, TimeFilter> {

    override fun asDomain(uiModel: TimeFilterUiModel): TimeFilter {
        return when (uiModel) {
            TimeFilterUiModel.MORNING -> TimeFilter.MORNING
            TimeFilterUiModel.AFTERNOON -> TimeFilter.AFTERNOON
            TimeFilterUiModel.EVENING -> TimeFilter.EVENING
        }
    }

    override fun asUiModel(domain: TimeFilter): TimeFilterUiModel {
        return when (domain) {
            TimeFilter.MORNING -> TimeFilterUiModel.MORNING
            TimeFilter.AFTERNOON -> TimeFilterUiModel.AFTERNOON
            TimeFilter.EVENING -> TimeFilterUiModel.EVENING
        }
    }
}

fun TimeFilter.asUiModel() = TimeFilterUiModelMapper.asUiModel(this)

fun TimeFilterUiModel.asDomain() = TimeFilterUiModelMapper.asDomain(this)
