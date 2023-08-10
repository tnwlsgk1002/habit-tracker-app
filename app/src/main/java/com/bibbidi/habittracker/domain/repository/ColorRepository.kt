package com.bibbidi.habittracker.domain.repository

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.ColorModel
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    fun getColors(): Flow<DBResult<List<ColorModel>>>
}
