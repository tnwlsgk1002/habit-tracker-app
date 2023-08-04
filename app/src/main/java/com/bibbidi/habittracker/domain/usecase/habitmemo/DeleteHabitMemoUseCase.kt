package com.bibbidi.habittracker.domain.usecase.habitmemo

import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.domain.model.HabitMemo
import com.bibbidi.habittracker.domain.repository.HabitMemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteHabitMemoUseCase @Inject constructor(private val habitMemoRepository: HabitMemoRepository) {

    suspend operator fun invoke(memoItem: HabitMemo) {
        habitMemoRepository.deleteHabitMemo(memoItem.logId)
    }

    suspend operator fun invoke(habitLog: HabitLog) {
        habitMemoRepository.deleteHabitMemo(habitLog.id)
    }
}
