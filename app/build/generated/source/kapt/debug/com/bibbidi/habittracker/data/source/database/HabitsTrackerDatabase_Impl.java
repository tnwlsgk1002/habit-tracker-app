package com.bibbidi.habittracker.data.source.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

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
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Habit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `order` INTEGER NOT NULL, `name` TEXT NOT NULL, `startDate` TEXT NOT NULL, `emoji` TEXT NOT NULL, `color` TEXT NOT NULL, `alarmTime` TEXT, `whenRun` TEXT NOT NULL, `repeatDayOfTheWeeks` TEXT NOT NULL, `timeFrame` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LongMemoInstance` (`longMemoInstanceId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, `content` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ChallengeHabit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, `endDate` TEXT NOT NULL, `goalCount` INTEGER NOT NULL, FOREIGN KEY(`habitId`) REFERENCES `Habit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ChallengeHabitLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `challengeHabitId` INTEGER NOT NULL, `date` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, FOREIGN KEY(`challengeHabitId`) REFERENCES `ChallengeHabit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CheckHabit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, FOREIGN KEY(`habitId`) REFERENCES `Habit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CheckHabitLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `checkHabitId` INTEGER NOT NULL, `date` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, FOREIGN KEY(`checkHabitId`) REFERENCES `CheckHabit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TimeHabit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, `goalTime` TEXT NOT NULL, FOREIGN KEY(`habitId`) REFERENCES `Habit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TimeHabitLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timeHabitId` INTEGER NOT NULL, `date` TEXT NOT NULL, `time` TEXT NOT NULL, FOREIGN KEY(`timeHabitId`) REFERENCES `TimeHabit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TrackHabit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, FOREIGN KEY(`habitId`) REFERENCES `Habit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `TrackHabitLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trackHabitId` INTEGER NOT NULL, `value` INTEGER NOT NULL, FOREIGN KEY(`trackHabitId`) REFERENCES `TrackHabit`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'abd29bd39272fac7ca28fb5453ba1463')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Habit`");
        _db.execSQL("DROP TABLE IF EXISTS `LongMemoInstance`");
        _db.execSQL("DROP TABLE IF EXISTS `ChallengeHabit`");
        _db.execSQL("DROP TABLE IF EXISTS `ChallengeHabitLog`");
        _db.execSQL("DROP TABLE IF EXISTS `CheckHabit`");
        _db.execSQL("DROP TABLE IF EXISTS `CheckHabitLog`");
        _db.execSQL("DROP TABLE IF EXISTS `TimeHabit`");
        _db.execSQL("DROP TABLE IF EXISTS `TimeHabitLog`");
        _db.execSQL("DROP TABLE IF EXISTS `TrackHabit`");
        _db.execSQL("DROP TABLE IF EXISTS `TrackHabitLog`");
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
        final HashMap<String, TableInfo.Column> _columnsHabit = new HashMap<String, TableInfo.Column>(10);
        _columnsHabit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("order", new TableInfo.Column("order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("startDate", new TableInfo.Column("startDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("emoji", new TableInfo.Column("emoji", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("alarmTime", new TableInfo.Column("alarmTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("whenRun", new TableInfo.Column("whenRun", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("repeatDayOfTheWeeks", new TableInfo.Column("repeatDayOfTheWeeks", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabit.put("timeFrame", new TableInfo.Column("timeFrame", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHabit = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHabit = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHabit = new TableInfo("Habit", _columnsHabit, _foreignKeysHabit, _indicesHabit);
        final TableInfo _existingHabit = TableInfo.read(_db, "Habit");
        if (! _infoHabit.equals(_existingHabit)) {
          return new RoomOpenHelper.ValidationResult(false, "Habit(com.bibbidi.habittracker.data.entity.Habit).\n"
                  + " Expected:\n" + _infoHabit + "\n"
                  + " Found:\n" + _existingHabit);
        }
        final HashMap<String, TableInfo.Column> _columnsLongMemoInstance = new HashMap<String, TableInfo.Column>(3);
        _columnsLongMemoInstance.put("longMemoInstanceId", new TableInfo.Column("longMemoInstanceId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLongMemoInstance.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLongMemoInstance.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLongMemoInstance = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLongMemoInstance = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLongMemoInstance = new TableInfo("LongMemoInstance", _columnsLongMemoInstance, _foreignKeysLongMemoInstance, _indicesLongMemoInstance);
        final TableInfo _existingLongMemoInstance = TableInfo.read(_db, "LongMemoInstance");
        if (! _infoLongMemoInstance.equals(_existingLongMemoInstance)) {
          return new RoomOpenHelper.ValidationResult(false, "LongMemoInstance(com.bibbidi.habittracker.data.entity.LongMemoInstance).\n"
                  + " Expected:\n" + _infoLongMemoInstance + "\n"
                  + " Found:\n" + _existingLongMemoInstance);
        }
        final HashMap<String, TableInfo.Column> _columnsChallengeHabit = new HashMap<String, TableInfo.Column>(4);
        _columnsChallengeHabit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabit.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabit.put("endDate", new TableInfo.Column("endDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabit.put("goalCount", new TableInfo.Column("goalCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChallengeHabit = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChallengeHabit.add(new TableInfo.ForeignKey("Habit", "CASCADE", "NO ACTION",Arrays.asList("habitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesChallengeHabit = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChallengeHabit = new TableInfo("ChallengeHabit", _columnsChallengeHabit, _foreignKeysChallengeHabit, _indicesChallengeHabit);
        final TableInfo _existingChallengeHabit = TableInfo.read(_db, "ChallengeHabit");
        if (! _infoChallengeHabit.equals(_existingChallengeHabit)) {
          return new RoomOpenHelper.ValidationResult(false, "ChallengeHabit(com.bibbidi.habittracker.data.entity.challenge.ChallengeHabit).\n"
                  + " Expected:\n" + _infoChallengeHabit + "\n"
                  + " Found:\n" + _existingChallengeHabit);
        }
        final HashMap<String, TableInfo.Column> _columnsChallengeHabitLog = new HashMap<String, TableInfo.Column>(4);
        _columnsChallengeHabitLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabitLog.put("challengeHabitId", new TableInfo.Column("challengeHabitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabitLog.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallengeHabitLog.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChallengeHabitLog = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChallengeHabitLog.add(new TableInfo.ForeignKey("ChallengeHabit", "CASCADE", "NO ACTION",Arrays.asList("challengeHabitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesChallengeHabitLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChallengeHabitLog = new TableInfo("ChallengeHabitLog", _columnsChallengeHabitLog, _foreignKeysChallengeHabitLog, _indicesChallengeHabitLog);
        final TableInfo _existingChallengeHabitLog = TableInfo.read(_db, "ChallengeHabitLog");
        if (! _infoChallengeHabitLog.equals(_existingChallengeHabitLog)) {
          return new RoomOpenHelper.ValidationResult(false, "ChallengeHabitLog(com.bibbidi.habittracker.data.entity.challenge.ChallengeHabitLog).\n"
                  + " Expected:\n" + _infoChallengeHabitLog + "\n"
                  + " Found:\n" + _existingChallengeHabitLog);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckHabit = new HashMap<String, TableInfo.Column>(2);
        _columnsCheckHabit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabit.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckHabit = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCheckHabit.add(new TableInfo.ForeignKey("Habit", "CASCADE", "NO ACTION",Arrays.asList("habitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCheckHabit = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckHabit = new TableInfo("CheckHabit", _columnsCheckHabit, _foreignKeysCheckHabit, _indicesCheckHabit);
        final TableInfo _existingCheckHabit = TableInfo.read(_db, "CheckHabit");
        if (! _infoCheckHabit.equals(_existingCheckHabit)) {
          return new RoomOpenHelper.ValidationResult(false, "CheckHabit(com.bibbidi.habittracker.data.entity.check.CheckHabit).\n"
                  + " Expected:\n" + _infoCheckHabit + "\n"
                  + " Found:\n" + _existingCheckHabit);
        }
        final HashMap<String, TableInfo.Column> _columnsCheckHabitLog = new HashMap<String, TableInfo.Column>(4);
        _columnsCheckHabitLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLog.put("checkHabitId", new TableInfo.Column("checkHabitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLog.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCheckHabitLog.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCheckHabitLog = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCheckHabitLog.add(new TableInfo.ForeignKey("CheckHabit", "CASCADE", "NO ACTION",Arrays.asList("checkHabitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCheckHabitLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCheckHabitLog = new TableInfo("CheckHabitLog", _columnsCheckHabitLog, _foreignKeysCheckHabitLog, _indicesCheckHabitLog);
        final TableInfo _existingCheckHabitLog = TableInfo.read(_db, "CheckHabitLog");
        if (! _infoCheckHabitLog.equals(_existingCheckHabitLog)) {
          return new RoomOpenHelper.ValidationResult(false, "CheckHabitLog(com.bibbidi.habittracker.data.entity.check.CheckHabitLog).\n"
                  + " Expected:\n" + _infoCheckHabitLog + "\n"
                  + " Found:\n" + _existingCheckHabitLog);
        }
        final HashMap<String, TableInfo.Column> _columnsTimeHabit = new HashMap<String, TableInfo.Column>(3);
        _columnsTimeHabit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabit.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabit.put("goalTime", new TableInfo.Column("goalTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimeHabit = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTimeHabit.add(new TableInfo.ForeignKey("Habit", "CASCADE", "NO ACTION",Arrays.asList("habitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTimeHabit = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimeHabit = new TableInfo("TimeHabit", _columnsTimeHabit, _foreignKeysTimeHabit, _indicesTimeHabit);
        final TableInfo _existingTimeHabit = TableInfo.read(_db, "TimeHabit");
        if (! _infoTimeHabit.equals(_existingTimeHabit)) {
          return new RoomOpenHelper.ValidationResult(false, "TimeHabit(com.bibbidi.habittracker.data.entity.time.TimeHabit).\n"
                  + " Expected:\n" + _infoTimeHabit + "\n"
                  + " Found:\n" + _existingTimeHabit);
        }
        final HashMap<String, TableInfo.Column> _columnsTimeHabitLog = new HashMap<String, TableInfo.Column>(4);
        _columnsTimeHabitLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLog.put("timeHabitId", new TableInfo.Column("timeHabitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLog.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimeHabitLog.put("time", new TableInfo.Column("time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimeHabitLog = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTimeHabitLog.add(new TableInfo.ForeignKey("TimeHabit", "CASCADE", "NO ACTION",Arrays.asList("timeHabitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTimeHabitLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTimeHabitLog = new TableInfo("TimeHabitLog", _columnsTimeHabitLog, _foreignKeysTimeHabitLog, _indicesTimeHabitLog);
        final TableInfo _existingTimeHabitLog = TableInfo.read(_db, "TimeHabitLog");
        if (! _infoTimeHabitLog.equals(_existingTimeHabitLog)) {
          return new RoomOpenHelper.ValidationResult(false, "TimeHabitLog(com.bibbidi.habittracker.data.entity.time.TimeHabitLog).\n"
                  + " Expected:\n" + _infoTimeHabitLog + "\n"
                  + " Found:\n" + _existingTimeHabitLog);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackHabit = new HashMap<String, TableInfo.Column>(2);
        _columnsTrackHabit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabit.put("habitId", new TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackHabit = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackHabit.add(new TableInfo.ForeignKey("Habit", "CASCADE", "NO ACTION",Arrays.asList("habitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTrackHabit = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrackHabit = new TableInfo("TrackHabit", _columnsTrackHabit, _foreignKeysTrackHabit, _indicesTrackHabit);
        final TableInfo _existingTrackHabit = TableInfo.read(_db, "TrackHabit");
        if (! _infoTrackHabit.equals(_existingTrackHabit)) {
          return new RoomOpenHelper.ValidationResult(false, "TrackHabit(com.bibbidi.habittracker.data.entity.track.TrackHabit).\n"
                  + " Expected:\n" + _infoTrackHabit + "\n"
                  + " Found:\n" + _existingTrackHabit);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackHabitLog = new HashMap<String, TableInfo.Column>(3);
        _columnsTrackHabitLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabitLog.put("trackHabitId", new TableInfo.Column("trackHabitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackHabitLog.put("value", new TableInfo.Column("value", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackHabitLog = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackHabitLog.add(new TableInfo.ForeignKey("TrackHabit", "CASCADE", "NO ACTION",Arrays.asList("trackHabitId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTrackHabitLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrackHabitLog = new TableInfo("TrackHabitLog", _columnsTrackHabitLog, _foreignKeysTrackHabitLog, _indicesTrackHabitLog);
        final TableInfo _existingTrackHabitLog = TableInfo.read(_db, "TrackHabitLog");
        if (! _infoTrackHabitLog.equals(_existingTrackHabitLog)) {
          return new RoomOpenHelper.ValidationResult(false, "TrackHabitLog(com.bibbidi.habittracker.data.entity.track.TrackHabitLog).\n"
                  + " Expected:\n" + _infoTrackHabitLog + "\n"
                  + " Found:\n" + _existingTrackHabitLog);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "abd29bd39272fac7ca28fb5453ba1463", "c6c9bee0cc3df768aa1bd7a9cc953384");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Habit","LongMemoInstance","ChallengeHabit","ChallengeHabitLog","CheckHabit","CheckHabitLog","TimeHabit","TimeHabitLog","TrackHabit","TrackHabitLog");
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
      _db.execSQL("DELETE FROM `Habit`");
      _db.execSQL("DELETE FROM `LongMemoInstance`");
      _db.execSQL("DELETE FROM `ChallengeHabit`");
      _db.execSQL("DELETE FROM `ChallengeHabitLog`");
      _db.execSQL("DELETE FROM `CheckHabit`");
      _db.execSQL("DELETE FROM `CheckHabitLog`");
      _db.execSQL("DELETE FROM `TimeHabit`");
      _db.execSQL("DELETE FROM `TimeHabitLog`");
      _db.execSQL("DELETE FROM `TrackHabit`");
      _db.execSQL("DELETE FROM `TrackHabitLog`");
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
