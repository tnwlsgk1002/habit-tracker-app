package com.bibbidi.habittracker.data.source.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HabitsTrackerDatabase_Impl extends HabitsTrackerDatabase {
  private volatile HabitsDao _habitsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `habits` (`habit_id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT NOT NULL, `start_date` TEXT NOT NULL, `emoji` TEXT NOT NULL, `alarmTime` TEXT, `whenRun` TEXT NOT NULL, `repeatDayOfTheWeeks` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LongMemoInstanceEntity` (`long_memo_instance_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, `content` TEXT NOT NULL, FOREIGN KEY(`long_memo_instance_id`) REFERENCES `habits`(`habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `check_habits` (`check_habit_id` INTEGER PRIMARY KEY AUTOINCREMENT, `habit_parent_id` INTEGER, FOREIGN KEY(`habit_parent_id`) REFERENCES `habits`(`habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `check_habit_logs` (`check_habit_log_id` INTEGER PRIMARY KEY AUTOINCREMENT, `check_habit_parent_id` INTEGER, `date` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, FOREIGN KEY(`check_habit_parent_id`) REFERENCES `check_habits`(`check_habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `time_habits` (`time_habit_id` INTEGER PRIMARY KEY AUTOINCREMENT, `habit_parent_id` INTEGER, `goalDuration` INTEGER NOT NULL, FOREIGN KEY(`habit_parent_id`) REFERENCES `habits`(`habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `time_habit_logs` (`time_habit_log_id` INTEGER PRIMARY KEY AUTOINCREMENT, `time_habit_parent_id` INTEGER, `date` TEXT NOT NULL, `duration` INTEGER NOT NULL, FOREIGN KEY(`time_habit_parent_id`) REFERENCES `time_habits`(`time_habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `track_habits` (`track_habit_id` INTEGER PRIMARY KEY AUTOINCREMENT, `habit_parent_id` INTEGER, FOREIGN KEY(`habit_parent_id`) REFERENCES `habits`(`habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `track_habit_logs` (`track_habit_log_id` INTEGER PRIMARY KEY AUTOINCREMENT, `track_habit_parent_id` INTEGER, `date` TEXT NOT NULL, `value` INTEGER, FOREIGN KEY(`track_habit_parent_id`) REFERENCES `track_habits`(`track_habit_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '391e41281d4d545674791c4dc10f08a2')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `habits`");
        _db.execSQL("DROP TABLE IF EXISTS `LongMemoInstanceEntity`");
        _db.execSQL("DROP TABLE IF EXISTS `check_habits`");
        _db.execSQL("DROP TABLE IF EXISTS `check_habit_logs`");
        _db.execSQL("DROP TABLE IF EXISTS `time_habits`");
        _db.execSQL("DROP TABLE IF EXISTS `time_habit_logs`");
        _db.execSQL("DROP TABLE IF EXISTS `track_habits`");
        _db.execSQL("DROP TABLE IF EXISTS `track_habit_logs`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsHabits = new HashMap<String, TableInfo.Column>(7);
        _columnsHabits.put("habit_id", new TableInfo.Column("habit_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("start_date", new TableInfo.Column("start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("emoji", new TableInfo.Column("emoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("alarmTime", new TableInfo.Column("alarmTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("whenRun", new TableInfo.Column("whenRun", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabits.put("repeatDayOfTheWeeks", new TableInfo.Column("repeatDayOfTheWeeks", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHabits = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHabits = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHabits = new TableInfo("habits", _columnsHabits, _foreignKeysHabits, _indicesHabits);
        final TableInfo _existingHabits = TableInfo.read(_db, "habits");
        if (! _infoHabits.equals(_existingHabits)) {
          return new RoomOpenHelper.ValidationResult(false, "habits(com.bibbidi.habittracker.data.model.entity.HabitEntity).\n"
                  + " Expected:\n" + _infoHabits + "\n"
                  + " Found:\n" + _existingHabits);
        }
        final HashMap<String, TableInfo.Column> _columnsLongMemoInstanceEntity = new HashMap<String, TableInfo.Column>(3);
        _columnsLongMemoInstanceEntity.put("long_memo_instance_id", new TableInfo.Column("long_memo_instance_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLongMemoInstanceEntity.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLongMemoInstanceEntity.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLongMemoInstanceEntity = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLongMemoInstanceEntity.add(new TableInfo.ForeignKey("habits", "CASCADE", "NO ACTION",Arrays.asList("long_memo_instance_id"), Arrays.asList("habit_id")));
        final HashSet<TableInfo.Index> _indicesLongMemoInstanceEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLongMemoInstanceEntity = new TableInfo("LongMemoInstanceEntity", _columnsLongMemoInstanceEntity, _foreignKeysLongMemoInstanceEntity, _indicesLongMemoInstanceEntity);
        final TableInfo _existingLongMemoInstanceEntity = TableInfo.read(_db, "LongMemoInstanceEntity");
        if (! _infoLongMemoInstanceEntity.equals(_existingLongMemoInstanceEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "LongMemoInstanceEntity(com.bibbidi.habittracker.data.model.entity.LongMemoInstanceEntity).\n"
                  + " Expected:\n" + _infoLongMemoInstanceEntity + "\n"
                  + " Found:\n" + _existingLongMemoInstanceEntity);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckHabits = new HashMap<String, TableInfo.Column>(2);
        _columnsCheckHabits.put("check_habit_id", new TableInfo.Column("check_habit_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabits.put("habit_parent_id", new TableInfo.Column("habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckHabits = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCheckHabits.add(new TableInfo.ForeignKey("habits", "CASCADE", "NO ACTION",Arrays.asList("habit_parent_id"), Arrays.asList("habit_id")));
        final HashSet<TableInfo.Index> _indicesCheckHabits = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckHabits = new TableInfo("check_habits", _columnsCheckHabits, _foreignKeysCheckHabits, _indicesCheckHabits);
        final TableInfo _existingCheckHabits = TableInfo.read(_db, "check_habits");
        if (! _infoCheckHabits.equals(_existingCheckHabits)) {
          return new RoomOpenHelper.ValidationResult(false, "check_habits(com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity).\n"
                  + " Expected:\n" + _infoCheckHabits + "\n"
                  + " Found:\n" + _existingCheckHabits);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckHabitLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsCheckHabitLogs.put("check_habit_log_id", new TableInfo.Column("check_habit_log_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLogs.put("check_habit_parent_id", new TableInfo.Column("check_habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLogs.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLogs.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckHabitLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCheckHabitLogs.add(new TableInfo.ForeignKey("check_habits", "CASCADE", "NO ACTION",Arrays.asList("check_habit_parent_id"), Arrays.asList("check_habit_id")));
        final HashSet<TableInfo.Index> _indicesCheckHabitLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckHabitLogs = new TableInfo("check_habit_logs", _columnsCheckHabitLogs, _foreignKeysCheckHabitLogs, _indicesCheckHabitLogs);
        final TableInfo _existingCheckHabitLogs = TableInfo.read(_db, "check_habit_logs");
        if (! _infoCheckHabitLogs.equals(_existingCheckHabitLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "check_habit_logs(com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity).\n"
                  + " Expected:\n" + _infoCheckHabitLogs + "\n"
                  + " Found:\n" + _existingCheckHabitLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsTimeHabits = new HashMap<String, TableInfo.Column>(3);
        _columnsTimeHabits.put("time_habit_id", new TableInfo.Column("time_habit_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabits.put("habit_parent_id", new TableInfo.Column("habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabits.put("goalDuration", new TableInfo.Column("goalDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimeHabits = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTimeHabits.add(new TableInfo.ForeignKey("habits", "CASCADE", "NO ACTION",Arrays.asList("habit_parent_id"), Arrays.asList("habit_id")));
        final HashSet<TableInfo.Index> _indicesTimeHabits = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimeHabits = new TableInfo("time_habits", _columnsTimeHabits, _foreignKeysTimeHabits, _indicesTimeHabits);
        final TableInfo _existingTimeHabits = TableInfo.read(_db, "time_habits");
        if (! _infoTimeHabits.equals(_existingTimeHabits)) {
          return new RoomOpenHelper.ValidationResult(false, "time_habits(com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity).\n"
                  + " Expected:\n" + _infoTimeHabits + "\n"
                  + " Found:\n" + _existingTimeHabits);
        }
        final HashMap<String, TableInfo.Column> _columnsTimeHabitLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsTimeHabitLogs.put("time_habit_log_id", new TableInfo.Column("time_habit_log_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLogs.put("time_habit_parent_id", new TableInfo.Column("time_habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLogs.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLogs.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimeHabitLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTimeHabitLogs.add(new TableInfo.ForeignKey("time_habits", "CASCADE", "NO ACTION",Arrays.asList("time_habit_parent_id"), Arrays.asList("time_habit_id")));
        final HashSet<TableInfo.Index> _indicesTimeHabitLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimeHabitLogs = new TableInfo("time_habit_logs", _columnsTimeHabitLogs, _foreignKeysTimeHabitLogs, _indicesTimeHabitLogs);
        final TableInfo _existingTimeHabitLogs = TableInfo.read(_db, "time_habit_logs");
        if (! _infoTimeHabitLogs.equals(_existingTimeHabitLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "time_habit_logs(com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity).\n"
                  + " Expected:\n" + _infoTimeHabitLogs + "\n"
                  + " Found:\n" + _existingTimeHabitLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackHabits = new HashMap<String, TableInfo.Column>(2);
        _columnsTrackHabits.put("track_habit_id", new TableInfo.Column("track_habit_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabits.put("habit_parent_id", new TableInfo.Column("habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackHabits = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackHabits.add(new TableInfo.ForeignKey("habits", "CASCADE", "NO ACTION",Arrays.asList("habit_parent_id"), Arrays.asList("habit_id")));
        final HashSet<TableInfo.Index> _indicesTrackHabits = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrackHabits = new TableInfo("track_habits", _columnsTrackHabits, _foreignKeysTrackHabits, _indicesTrackHabits);
        final TableInfo _existingTrackHabits = TableInfo.read(_db, "track_habits");
        if (! _infoTrackHabits.equals(_existingTrackHabits)) {
          return new RoomOpenHelper.ValidationResult(false, "track_habits(com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity).\n"
                  + " Expected:\n" + _infoTrackHabits + "\n"
                  + " Found:\n" + _existingTrackHabits);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackHabitLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsTrackHabitLogs.put("track_habit_log_id", new TableInfo.Column("track_habit_log_id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabitLogs.put("track_habit_parent_id", new TableInfo.Column("track_habit_parent_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabitLogs.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabitLogs.put("value", new TableInfo.Column("value", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackHabitLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackHabitLogs.add(new TableInfo.ForeignKey("track_habits", "CASCADE", "NO ACTION",Arrays.asList("track_habit_parent_id"), Arrays.asList("track_habit_id")));
        final HashSet<TableInfo.Index> _indicesTrackHabitLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrackHabitLogs = new TableInfo("track_habit_logs", _columnsTrackHabitLogs, _foreignKeysTrackHabitLogs, _indicesTrackHabitLogs);
        final TableInfo _existingTrackHabitLogs = TableInfo.read(_db, "track_habit_logs");
        if (! _infoTrackHabitLogs.equals(_existingTrackHabitLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "track_habit_logs(com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity).\n"
                  + " Expected:\n" + _infoTrackHabitLogs + "\n"
                  + " Found:\n" + _existingTrackHabitLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "391e41281d4d545674791c4dc10f08a2", "3ee469f4b54f71d32ead62f3468ed5ad");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "habits","LongMemoInstanceEntity","check_habits","check_habit_logs","time_habits","time_habit_logs","track_habits","track_habit_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `habits`");
      _db.execSQL("DELETE FROM `LongMemoInstanceEntity`");
      _db.execSQL("DELETE FROM `check_habits`");
      _db.execSQL("DELETE FROM `check_habit_logs`");
      _db.execSQL("DELETE FROM `time_habits`");
      _db.execSQL("DELETE FROM `time_habit_logs`");
      _db.execSQL("DELETE FROM `track_habits`");
      _db.execSQL("DELETE FROM `track_habit_logs`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(HabitsDao.class, HabitsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public HabitsDao habitDao() {
    if (_habitsDao != null) {
      return _habitsDao;
    } else {
      synchronized(this) {
        if(_habitsDao == null) {
          _habitsDao = new HabitsDao_Impl(this);
        }
        return _habitsDao;
      }
    }
  }
}
