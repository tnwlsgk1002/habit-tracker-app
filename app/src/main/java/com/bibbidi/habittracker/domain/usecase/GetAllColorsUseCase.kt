package com.bibbidi.habittracker.domain.usecase

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.ColorModel
import com.bibbidi.habittracker.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllColorsUseCase @Inject constructor(private val colorRepository: ColorRepository) {

    operator fun invoke(): Flow<DBResult<List<ColorModel>>> {
        return colorRepository.getColors()
    }
}
