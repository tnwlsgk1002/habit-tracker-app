package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.domain.model.Progress
import com.bibbidi.habittracker.ui.model.ProgressUiModel

fun Progress.asUiModel() = ProgressUiModel(finishCount, total)
