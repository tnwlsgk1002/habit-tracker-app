package com.bibbidi.habittracker.domain.usecase.habitmemo

import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.domain.model.HabitMemo
import com.bibbidi.habittracker.domain.repository.HabitMemoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveHabitMemoUseCase @Inject constructor(private val habitMemoRepository: HabitMemoRepository) {

    suspend operator fun invoke(habitMemo: HabitMemo, memo: String?) {
        habitMemoRepository.saveHabitMemo(habitMemo, memo)
    }

    suspend operator fun invoke(habitLog: HabitLog, memo: String?) {
        habitMemoRepository.saveHabitMemo(habitLog, memo)
    }
}
