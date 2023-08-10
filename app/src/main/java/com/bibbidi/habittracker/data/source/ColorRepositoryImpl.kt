package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.model.ColorModel
import com.bibbidi.habittracker.domain.repository.ColorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorRepositoryImpl @Inject constructor(
    private val dao: HabitsDao
) : ColorRepository {

    override fun getColors(): Flow<DBResult<List<ColorModel>>> = flow {
        emit(DBResult.Loading)
        dao.getAllColors().collect { colors ->
            emit(DBResult.Success(colors.map { it.asDomain() }))
        }
    }
}
