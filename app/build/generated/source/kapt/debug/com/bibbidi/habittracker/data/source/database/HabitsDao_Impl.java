package com.bibbidi.habittracker.data.source.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bibbidi.habittracker.data.model.converters.DateConverters;
import com.bibbidi.habittracker.data.model.converters.DayOfWeekConverter;
import com.bibbidi.habittracker.data.model.converters.DurationConverter;
import com.bibbidi.habittracker.data.model.entity.HabitEntity;
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity;
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity;
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity;
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity;
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity;
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity;
import com.bibbidi.habittracker.data.model.joined.HabitAndChildren;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HabitsDao_Impl implements HabitsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HabitEntity> __insertionAdapterOfHabitEntity;

  private final EntityInsertionAdapter<CheckHabitEntity> __insertionAdapterOfCheckHabitEntity;

  private final EntityInsertionAdapter<TimeHabitEntity> __insertionAdapterOfTimeHabitEntity;

  private final EntityInsertionAdapter<TrackHabitEntity> __insertionAdapterOfTrackHabitEntity;

  private final EntityInsertionAdapter<CheckHabitLogEntity> __insertionAdapterOfCheckHabitLogEntity;

  private final EntityInsertionAdapter<TimeHabitLogEntity> __insertionAdapterOfTimeHabitLogEntity;

  private final EntityInsertionAdapter<TrackHabitLogEntity> __insertionAdapterOfTrackHabitLogEntity;

  private final EntityDeletionOrUpdateAdapter<HabitEntity> __updateAdapterOfHabitEntity;

  private final EntityDeletionOrUpdateAdapter<CheckHabitLogEntity> __updateAdapterOfCheckHabitLogEntity;

  private final EntityDeletionOrUpdateAdapter<TimeHabitLogEntity> __updateAdapterOfTimeHabitLogEntity;

  private final EntityDeletionOrUpdateAdapter<TrackHabitLogEntity> __updateAdapterOfTrackHabitLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteHabitById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public HabitsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHabitEntity = new EntityInsertionAdapter<HabitEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `habits` (`habit_id`,`name`,`start_date`,`emoji`,`alarmTime`,`whenRun`,`repeatDayOfTheWeeks`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HabitEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getEmoji() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmoji());
        }
        final String _tmp_1 = DateConverters.INSTANCE.fromLocalTime(value.getAlarmTime());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
        if (value.getWhenRun() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getWhenRun());
        }
        final String _tmp_2 = DayOfWeekConverter.INSTANCE.fromDayOfTheWeekSet(value.getRepeatDayOfTheWeeks());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_2);
        }
      }
    };
    this.__insertionAdapterOfCheckHabitEntity = new EntityInsertionAdapter<CheckHabitEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `check_habits` (`check_habit_id`,`habit_parent_id`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckHabitEntity value) {
        if (value.getCheckHabitId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getCheckHabitId());
        }
        if (value.getHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getHabitId());
        }
      }
    };
    this.__insertionAdapterOfTimeHabitEntity = new EntityInsertionAdapter<TimeHabitEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `time_habits` (`time_habit_id`,`habit_parent_id`,`goalDuration`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TimeHabitEntity value) {
        if (value.getTimeHabitId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTimeHabitId());
        }
        if (value.getHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getHabitId());
        }
        final Long _tmp = DurationConverter.INSTANCE.fromDuration(value.getGoalDuration());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
      }
    };
    this.__insertionAdapterOfTrackHabitEntity = new EntityInsertionAdapter<TrackHabitEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `track_habits` (`track_habit_id`,`habit_parent_id`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TrackHabitEntity value) {
        if (value.getTrackHabitId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTrackHabitId());
        }
        if (value.getHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getHabitId());
        }
      }
    };
    this.__insertionAdapterOfCheckHabitLogEntity = new EntityInsertionAdapter<CheckHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `check_habit_logs` (`check_habit_log_id`,`check_habit_parent_id`,`date`,`isCompleted`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckHabitLogEntity value) {
        if (value.getCheckHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getCheckHabitLogId());
        }
        if (value.getCheckHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getCheckHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final int _tmp_1 = value.isCompleted() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
      }
    };
    this.__insertionAdapterOfTimeHabitLogEntity = new EntityInsertionAdapter<TimeHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `time_habit_logs` (`time_habit_log_id`,`time_habit_parent_id`,`date`,`duration`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TimeHabitLogEntity value) {
        if (value.getTimeHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTimeHabitLogId());
        }
        if (value.getTimeHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getTimeHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final Long _tmp_1 = DurationConverter.INSTANCE.fromDuration(value.getDuration());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
      }
    };
    this.__insertionAdapterOfTrackHabitLogEntity = new EntityInsertionAdapter<TrackHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `track_habit_logs` (`track_habit_log_id`,`track_habit_parent_id`,`date`,`value`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TrackHabitLogEntity value) {
        if (value.getTrackHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTrackHabitLogId());
        }
        if (value.getTrackHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getTrackHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getValue() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getValue());
        }
      }
    };
    this.__updateAdapterOfHabitEntity = new EntityDeletionOrUpdateAdapter<HabitEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `habits` SET `habit_id` = ?,`name` = ?,`start_date` = ?,`emoji` = ?,`alarmTime` = ?,`whenRun` = ?,`repeatDayOfTheWeeks` = ? WHERE `habit_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HabitEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getStartDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getEmoji() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmoji());
        }
        final String _tmp_1 = DateConverters.INSTANCE.fromLocalTime(value.getAlarmTime());
        if (_tmp_1 == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp_1);
        }
        if (value.getWhenRun() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getWhenRun());
        }
        final String _tmp_2 = DayOfWeekConverter.INSTANCE.fromDayOfTheWeekSet(value.getRepeatDayOfTheWeeks());
        if (_tmp_2 == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp_2);
        }
        if (value.getId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getId());
        }
      }
    };
    this.__updateAdapterOfCheckHabitLogEntity = new EntityDeletionOrUpdateAdapter<CheckHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `check_habit_logs` SET `check_habit_log_id` = ?,`check_habit_parent_id` = ?,`date` = ?,`isCompleted` = ? WHERE `check_habit_log_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CheckHabitLogEntity value) {
        if (value.getCheckHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getCheckHabitLogId());
        }
        if (value.getCheckHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getCheckHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final int _tmp_1 = value.isCompleted() ? 1 : 0;
        stmt.bindLong(4, _tmp_1);
        if (value.getCheckHabitLogId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getCheckHabitLogId());
        }
      }
    };
    this.__updateAdapterOfTimeHabitLogEntity = new EntityDeletionOrUpdateAdapter<TimeHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `time_habit_logs` SET `time_habit_log_id` = ?,`time_habit_parent_id` = ?,`date` = ?,`duration` = ? WHERE `time_habit_log_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TimeHabitLogEntity value) {
        if (value.getTimeHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTimeHabitLogId());
        }
        if (value.getTimeHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getTimeHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final Long _tmp_1 = DurationConverter.INSTANCE.fromDuration(value.getDuration());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp_1);
        }
        if (value.getTimeHabitLogId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getTimeHabitLogId());
        }
      }
    };
    this.__updateAdapterOfTrackHabitLogEntity = new EntityDeletionOrUpdateAdapter<TrackHabitLogEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `track_habit_logs` SET `track_habit_log_id` = ?,`track_habit_parent_id` = ?,`date` = ?,`value` = ? WHERE `track_habit_log_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TrackHabitLogEntity value) {
        if (value.getTrackHabitLogId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getTrackHabitLogId());
        }
        if (value.getTrackHabitId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, value.getTrackHabitId());
        }
        final String _tmp = DateConverters.INSTANCE.fromLocalDate(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getValue() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getValue());
        }
        if (value.getTrackHabitLogId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getTrackHabitLogId());
        }
      }
    };
    this.__preparedStmtOfDeleteHabitById = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM habits WHERE habit_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM habits";
        return _query;
      }
    };
  }

  @Override
  public Object insertHabit(final HabitEntity habit,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfHabitEntity.insertAndReturnId(habit);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertCheckHabit(final CheckHabitEntity checkHabit,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfCheckHabitEntity.insertAndReturnId(checkHabit);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertTimeHabit(final TimeHabitEntity timeHabitEntity,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTimeHabitEntity.insert(timeHabitEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertTrackHabit(final TrackHabitEntity trackHabitEntity,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrackHabitEntity.insert(trackHabitEntity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertCheckHabitLog(final CheckHabitLogEntity log,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCheckHabitLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertTimeHabitLog(final TimeHabitLogEntity log,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTimeHabitLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertTrackHabitLog(final TrackHabitLogEntity log,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrackHabitLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateHabits(final HabitEntity[] habits,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfHabitEntity.handleMultiple(habits);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateCheckHabitLog(final CheckHabitLogEntity habitLog,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCheckHabitLogEntity.handle(habitLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateTimeHabitLog(final TimeHabitLogEntity habitLog,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTimeHabitLogEntity.handle(habitLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateTrackHabitLog(final TrackHabitLogEntity habitLog,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTrackHabitLogEntity.handle(habitLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object insertHabitAndCheckHabit(final HabitEntity habit, final CheckHabitEntity checkHabit,
      final Continuation<? super Unit> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.insertHabitAndCheckHabit(HabitsDao_Impl.this, habit, checkHabit, __cont), continuation);
  }

  @Override
  public Object insertHabitAndTimeHabit(final HabitEntity habit, final TimeHabitEntity timeHabit,
      final Continuation<? super Unit> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.insertHabitAndTimeHabit(HabitsDao_Impl.this, habit, timeHabit, __cont), continuation);
  }

  @Override
  public Object insertHabitAndTrackHabit(final HabitEntity habit, final TrackHabitEntity trackHabit,
      final Continuation<? super Unit> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.insertHabitAndTrackHabit(HabitsDao_Impl.this, habit, trackHabit, __cont), continuation);
  }

  @Override
  public Object getCheckLogByCheckHabitIdInDateTransaction(final Long id, final LocalDate date,
      final Continuation<? super CheckHabitLogEntity> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.getCheckLogByCheckHabitIdInDateTransaction(HabitsDao_Impl.this, id, date, __cont), continuation);
  }

  @Override
  public Object getTimeLogByTimeHabitIdInDateTransaction(final Long id, final LocalDate date,
      final Continuation<? super TimeHabitLogEntity> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.getTimeLogByTimeHabitIdInDateTransaction(HabitsDao_Impl.this, id, date, __cont), continuation);
  }

  @Override
  public Object getTrackLogByTrackHabitIdInDateTransaction(final Long id, final LocalDate date,
      final Continuation<? super TrackHabitLogEntity> continuation) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HabitsDao.DefaultImpls.getTrackLogByTrackHabitIdInDateTransaction(HabitsDao_Impl.this, id, date, __cont), continuation);
  }

  @Override
  public Object deleteHabitById(final long id, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteHabitById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteHabitById.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<HabitAndChildren>> getHabitAndChildren(final LocalDate date) {
    final String _sql = "SELECT * FROM habits WHERE start_date <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = DateConverters.INSTANCE.fromLocalDate(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, true, new String[]{"check_habits","track_habits","time_habits","habits"}, new Callable<List<HabitAndChildren>>() {
      @Override
      public List<HabitAndChildren> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "habit_id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
            final int _cursorIndexOfEmoji = CursorUtil.getColumnIndexOrThrow(_cursor, "emoji");
            final int _cursorIndexOfAlarmTime = CursorUtil.getColumnIndexOrThrow(_cursor, "alarmTime");
            final int _cursorIndexOfWhenRun = CursorUtil.getColumnIndexOrThrow(_cursor, "whenRun");
            final int _cursorIndexOfRepeatDayOfTheWeeks = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDayOfTheWeeks");
            final LongSparseArray<CheckHabitEntity> _collectionCheckHabit = new LongSparseArray<CheckHabitEntity>();
            final LongSparseArray<TrackHabitEntity> _collectionTrackHabit = new LongSparseArray<TrackHabitEntity>();
            final LongSparseArray<TimeHabitEntity> _collectionTimeHabit = new LongSparseArray<TimeHabitEntity>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
                _collectionCheckHabit.put(_tmpKey, null);
              }
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
                _collectionTrackHabit.put(_tmpKey_1, null);
              }
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
                _collectionTimeHabit.put(_tmpKey_2, null);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcheckHabitsAscomBibbidiHabittrackerDataModelEntityCheckCheckHabitEntity(_collectionCheckHabit);
            __fetchRelationshiptrackHabitsAscomBibbidiHabittrackerDataModelEntityTrackTrackHabitEntity(_collectionTrackHabit);
            __fetchRelationshiptimeHabitsAscomBibbidiHabittrackerDataModelEntityTimeTimeHabitEntity(_collectionTimeHabit);
            final List<HabitAndChildren> _result = new ArrayList<HabitAndChildren>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final HabitAndChildren _item;
              final HabitEntity _tmpHabit;
              final Long _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getLong(_cursorIndexOfId);
              }
              final String _tmpName;
              if (_cursor.isNull(_cursorIndexOfName)) {
                _tmpName = null;
              } else {
                _tmpName = _cursor.getString(_cursorIndexOfName);
              }
              final LocalDate _tmpStartDate;
              final String _tmp_1;
              if (_cursor.isNull(_cursorIndexOfStartDate)) {
                _tmp_1 = null;
              } else {
                _tmp_1 = _cursor.getString(_cursorIndexOfStartDate);
              }
              _tmpStartDate = DateConverters.INSTANCE.toLocalDate(_tmp_1);
              final String _tmpEmoji;
              if (_cursor.isNull(_cursorIndexOfEmoji)) {
                _tmpEmoji = null;
              } else {
                _tmpEmoji = _cursor.getString(_cursorIndexOfEmoji);
              }
              final LocalTime _tmpAlarmTime;
              final String _tmp_2;
              if (_cursor.isNull(_cursorIndexOfAlarmTime)) {
                _tmp_2 = null;
              } else {
                _tmp_2 = _cursor.getString(_cursorIndexOfAlarmTime);
              }
              _tmpAlarmTime = DateConverters.INSTANCE.toLocalTime(_tmp_2);
              final String _tmpWhenRun;
              if (_cursor.isNull(_cursorIndexOfWhenRun)) {
                _tmpWhenRun = null;
              } else {
                _tmpWhenRun = _cursor.getString(_cursorIndexOfWhenRun);
              }
              final Set<DayOfWeek> _tmpRepeatDayOfTheWeeks;
              final String _tmp_3;
              if (_cursor.isNull(_cursorIndexOfRepeatDayOfTheWeeks)) {
                _tmp_3 = null;
              } else {
                _tmp_3 = _cursor.getString(_cursorIndexOfRepeatDayOfTheWeeks);
              }
              _tmpRepeatDayOfTheWeeks = DayOfWeekConverter.INSTANCE.toDayOfTheWeekSet(_tmp_3);
              _tmpHabit = new HabitEntity(_tmpId,_tmpName,_tmpStartDate,_tmpEmoji,_tmpAlarmTime,_tmpWhenRun,_tmpRepeatDayOfTheWeeks);
              CheckHabitEntity _tmpCheckHabit = null;
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
                _tmpCheckHabit = _collectionCheckHabit.get(_tmpKey_3);
              }
              TrackHabitEntity _tmpTrackHabit = null;
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
                _tmpTrackHabit = _collectionTrackHabit.get(_tmpKey_4);
              }
              TimeHabitEntity _tmpTimeHabit = null;
              if (!_cursor.isNull(_cursorIndexOfId)) {
                final long _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
                _tmpTimeHabit = _collectionTimeHabit.get(_tmpKey_5);
              }
              _item = new HabitAndChildren(_tmpHabit,_tmpCheckHabit,_tmpTrackHabit,_tmpTimeHabit);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCheckLogByCheckHabitIdInDate(final Long id, final LocalDate date,
      final Continuation<? super CheckHabitLogEntity> continuation) {
    final String _sql = "SELECT * FROM check_habit_logs WHERE date = ? AND check_habit_parent_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = DateConverters.INSTANCE.fromLocalDate(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CheckHabitLogEntity>() {
      @Override
      public CheckHabitLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCheckHabitLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "check_habit_log_id");
          final int _cursorIndexOfCheckHabitId = CursorUtil.getColumnIndexOrThrow(_cursor, "check_habit_parent_id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final CheckHabitLogEntity _result;
          if(_cursor.moveToFirst()) {
            final Long _tmpCheckHabitLogId;
            if (_cursor.isNull(_cursorIndexOfCheckHabitLogId)) {
              _tmpCheckHabitLogId = null;
            } else {
              _tmpCheckHabitLogId = _cursor.getLong(_cursorIndexOfCheckHabitLogId);
            }
            final Long _tmpCheckHabitId;
            if (_cursor.isNull(_cursorIndexOfCheckHabitId)) {
              _tmpCheckHabitId = null;
            } else {
              _tmpCheckHabitId = _cursor.getLong(_cursorIndexOfCheckHabitId);
            }
            final LocalDate _tmpDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDate);
            }
            _tmpDate = DateConverters.INSTANCE.toLocalDate(_tmp_1);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            _result = new CheckHabitLogEntity(_tmpCheckHabitLogId,_tmpCheckHabitId,_tmpDate,_tmpIsCompleted);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getTimeLogByTimeHabitIdInDate(final Long id, final LocalDate date,
      final Continuation<? super TimeHabitLogEntity> continuation) {
    final String _sql = "SELECT * FROM time_habit_logs WHERE date = ? AND time_habit_parent_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = DateConverters.INSTANCE.fromLocalDate(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TimeHabitLogEntity>() {
      @Override
      public TimeHabitLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTimeHabitLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "time_habit_log_id");
          final int _cursorIndexOfTimeHabitId = CursorUtil.getColumnIndexOrThrow(_cursor, "time_habit_parent_id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final TimeHabitLogEntity _result;
          if(_cursor.moveToFirst()) {
            final Long _tmpTimeHabitLogId;
            if (_cursor.isNull(_cursorIndexOfTimeHabitLogId)) {
              _tmpTimeHabitLogId = null;
            } else {
              _tmpTimeHabitLogId = _cursor.getLong(_cursorIndexOfTimeHabitLogId);
            }
            final Long _tmpTimeHabitId;
            if (_cursor.isNull(_cursorIndexOfTimeHabitId)) {
              _tmpTimeHabitId = null;
            } else {
              _tmpTimeHabitId = _cursor.getLong(_cursorIndexOfTimeHabitId);
            }
            final LocalDate _tmpDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDate);
            }
            _tmpDate = DateConverters.INSTANCE.toLocalDate(_tmp_1);
            final Duration _tmpDuration;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDuration);
            }
            _tmpDuration = DurationConverter.INSTANCE.toDuration(_tmp_2);
            _result = new TimeHabitLogEntity(_tmpTimeHabitLogId,_tmpTimeHabitId,_tmpDate,_tmpDuration);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getTrackLogByTrackHabitIdInDate(final Long id, final LocalDate date,
      final Continuation<? super TrackHabitLogEntity> continuation) {
    final String _sql = "SELECT * FROM track_habit_logs WHERE date = ? AND track_habit_parent_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = DateConverters.INSTANCE.fromLocalDate(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TrackHabitLogEntity>() {
      @Override
      public TrackHabitLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTrackHabitLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "track_habit_log_id");
          final int _cursorIndexOfTrackHabitId = CursorUtil.getColumnIndexOrThrow(_cursor, "track_habit_parent_id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final TrackHabitLogEntity _result;
          if(_cursor.moveToFirst()) {
            final Long _tmpTrackHabitLogId;
            if (_cursor.isNull(_cursorIndexOfTrackHabitLogId)) {
              _tmpTrackHabitLogId = null;
            } else {
              _tmpTrackHabitLogId = _cursor.getLong(_cursorIndexOfTrackHabitLogId);
            }
            final Long _tmpTrackHabitId;
            if (_cursor.isNull(_cursorIndexOfTrackHabitId)) {
              _tmpTrackHabitId = null;
            } else {
              _tmpTrackHabitId = _cursor.getLong(_cursorIndexOfTrackHabitId);
            }
            final LocalDate _tmpDate;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfDate);
            }
            _tmpDate = DateConverters.INSTANCE.toLocalDate(_tmp_1);
            final Long _tmpValue;
            if (_cursor.isNull(_cursorIndexOfValue)) {
              _tmpValue = null;
            } else {
              _tmpValue = _cursor.getLong(_cursorIndexOfValue);
            }
            _result = new TrackHabitLogEntity(_tmpTrackHabitLogId,_tmpTrackHabitId,_tmpDate,_tmpValue);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcheckHabitsAscomBibbidiHabittrackerDataModelEntityCheckCheckHabitEntity(
      final LongSparseArray<CheckHabitEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<CheckHabitEntity> _tmpInnerMap = new LongSparseArray<CheckHabitEntity>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipcheckHabitsAscomBibbidiHabittrackerDataModelEntityCheckCheckHabitEntity(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<CheckHabitEntity>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipcheckHabitsAscomBibbidiHabittrackerDataModelEntityCheckCheckHabitEntity(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `check_habit_id`,`habit_parent_id` FROM `check_habits` WHERE `habit_parent_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "habit_parent_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfCheckHabitId = 0;
      final int _cursorIndexOfHabitId = 1;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final CheckHabitEntity _item_1;
            final Long _tmpCheckHabitId;
            if (_cursor.isNull(_cursorIndexOfCheckHabitId)) {
              _tmpCheckHabitId = null;
            } else {
              _tmpCheckHabitId = _cursor.getLong(_cursorIndexOfCheckHabitId);
            }
            final Long _tmpHabitId;
            if (_cursor.isNull(_cursorIndexOfHabitId)) {
              _tmpHabitId = null;
            } else {
              _tmpHabitId = _cursor.getLong(_cursorIndexOfHabitId);
            }
            _item_1 = new CheckHabitEntity(_tmpCheckHabitId,_tmpHabitId);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptrackHabitsAscomBibbidiHabittrackerDataModelEntityTrackTrackHabitEntity(
      final LongSparseArray<TrackHabitEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<TrackHabitEntity> _tmpInnerMap = new LongSparseArray<TrackHabitEntity>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptrackHabitsAscomBibbidiHabittrackerDataModelEntityTrackTrackHabitEntity(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<TrackHabitEntity>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptrackHabitsAscomBibbidiHabittrackerDataModelEntityTrackTrackHabitEntity(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `track_habit_id`,`habit_parent_id` FROM `track_habits` WHERE `habit_parent_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "habit_parent_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfTrackHabitId = 0;
      final int _cursorIndexOfHabitId = 1;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final TrackHabitEntity _item_1;
            final Long _tmpTrackHabitId;
            if (_cursor.isNull(_cursorIndexOfTrackHabitId)) {
              _tmpTrackHabitId = null;
            } else {
              _tmpTrackHabitId = _cursor.getLong(_cursorIndexOfTrackHabitId);
            }
            final Long _tmpHabitId;
            if (_cursor.isNull(_cursorIndexOfHabitId)) {
              _tmpHabitId = null;
            } else {
              _tmpHabitId = _cursor.getLong(_cursorIndexOfHabitId);
            }
            _item_1 = new TrackHabitEntity(_tmpTrackHabitId,_tmpHabitId);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptimeHabitsAscomBibbidiHabittrackerDataModelEntityTimeTimeHabitEntity(
      final LongSparseArray<TimeHabitEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<TimeHabitEntity> _tmpInnerMap = new LongSparseArray<TimeHabitEntity>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), null);
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptimeHabitsAscomBibbidiHabittrackerDataModelEntityTimeTimeHabitEntity(_tmpInnerMap);
          _map.putAll(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<TimeHabitEntity>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptimeHabitsAscomBibbidiHabittrackerDataModelEntityTimeTimeHabitEntity(_tmpInnerMap);
        _map.putAll(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `time_habit_id`,`habit_parent_id`,`goalDuration` FROM `time_habits` WHERE `habit_parent_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "habit_parent_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfTimeHabitId = 0;
      final int _cursorIndexOfHabitId = 1;
      final int _cursorIndexOfGoalDuration = 2;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          if (_map.containsKey(_tmpKey)) {
            final TimeHabitEntity _item_1;
            final Long _tmpTimeHabitId;
            if (_cursor.isNull(_cursorIndexOfTimeHabitId)) {
              _tmpTimeHabitId = null;
            } else {
              _tmpTimeHabitId = _cursor.getLong(_cursorIndexOfTimeHabitId);
            }
            final Long _tmpHabitId;
            if (_cursor.isNull(_cursorIndexOfHabitId)) {
              _tmpHabitId = null;
            } else {
              _tmpHabitId = _cursor.getLong(_cursorIndexOfHabitId);
            }
            final Duration _tmpGoalDuration;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalDuration)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfGoalDuration);
            }
            _tmpGoalDuration = DurationConverter.INSTANCE.toDuration(_tmp);
            _item_1 = new TimeHabitEntity(_tmpTimeHabitId,_tmpHabitId,_tmpGoalDuration);
            _map.put(_tmpKey, _item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
