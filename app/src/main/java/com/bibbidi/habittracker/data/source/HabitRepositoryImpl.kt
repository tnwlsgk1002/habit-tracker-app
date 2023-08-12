package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.model.Habit
import com.bibbidi.habittracker.domain.repository.HabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor(
    private val dao: HabitsDao
) : HabitRepository {

    override suspend fun getHabitById(id: Long?): Habit {
        return dao.getHabitById(id).asDomain()
    }

    override suspend fun getHabits(): List<Habit> {
        return dao.getHabits().map {
            it.asDomain()
        }
    }

    override suspend fun insertHabit(habit: Habit): Habit {
        val id = dao.insertHabit(habit.asData())
        return dao.getHabitById(id).asDomain()
    }

    override suspend fun deleteHabitById(id: Long?) {
        dao.deleteHabitById(id)
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit.asData())
    }
}
