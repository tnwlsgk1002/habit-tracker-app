package com.bibbidi.habittracker.data.source.database

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bibbidi.habittracker.data.model.ColorAssets
import com.bibbidi.habittracker.data.model.converters.DateConverters
import com.bibbidi.habittracker.data.model.converters.DayOfWeekConverter
import com.bibbidi.habittracker.data.model.converters.DurationConverter
import com.bibbidi.habittracker.data.model.converters.TimeFilterConverter
import com.bibbidi.habittracker.data.model.habit.entity.ColorEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity
import com.bibbidi.habittracker.utils.Constants
import com.bibbidi.habittracker.utils.Constants.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Database(
    entities = [
        HabitEntity::class,
        HabitLogEntity::class,
        ColorEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverters::class,
    DayOfWeekConverter::class,
    DurationConverter::class,
    TimeFilterConverter::class
)
abstract class HabitsTrackerDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitsDao

    private class HabitTrackerDatabaseCallback(
        private val scope: CoroutineScope,
        private val assetManager: AssetManager
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch {
                    val dao = database.habitDao()
                    val data = readJsonFromAsset()
                    dao.insertColors(data.map { ColorEntity(id = it.id, hexCode = it.hexCode) })
                }
            }
        }

        private fun readJsonFromAsset(): List<ColorAssets> {
            val jsonStr = assetManager.open(Constants.COLOR_ASSETS_FILE_NAME).bufferedReader()
                .use { it.readText() }
            return Json.decodeFromString(jsonStr)
        }
    }

    companion object {

        @Volatile
        private var instance: HabitsTrackerDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): HabitsTrackerDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, scope).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): HabitsTrackerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                HabitsTrackerDatabase::class.java,
                DATABASE_NAME
            ).addCallback(HabitTrackerDatabaseCallback(scope, context.assets))
                .build()
        }
    }
}
