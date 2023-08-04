package com.bibbidi.habittracker.domain.usecase.habitmemo

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitMemo
import com.bibbidi.habittracker.domain.repository.HabitMemoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitMemosUseCase @Inject constructor(private val habitMemoRepository: HabitMemoRepository) {

    suspend operator fun invoke(habitId: Long?, reverse: Boolean = false): Flow<DBResult<List<HabitMemo>>> {
        return habitMemoRepository.getHabitMemos(habitId, reverse)
    }
}
