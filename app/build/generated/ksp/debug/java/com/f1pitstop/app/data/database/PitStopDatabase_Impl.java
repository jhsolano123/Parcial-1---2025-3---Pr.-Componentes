package com.f1pitstop.app.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PitStopDatabase_Impl extends PitStopDatabase {
  private volatile PitStopDao _pitStopDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `pit_stops` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `piloto` TEXT NOT NULL, `escuderia` TEXT NOT NULL, `tiempo_total` REAL NOT NULL, `cambio_neumaticos` TEXT NOT NULL, `numero_neumaticos_cambiados` INTEGER NOT NULL, `estado` TEXT NOT NULL, `motivo_fallo` TEXT, `mecanico_principal` TEXT NOT NULL, `fecha_hora` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b0f64ec6806f4e67efb4f8e6467cf565')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `pit_stops`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPitStops = new HashMap<String, TableInfo.Column>(10);
        _columnsPitStops.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("piloto", new TableInfo.Column("piloto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("escuderia", new TableInfo.Column("escuderia", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("tiempo_total", new TableInfo.Column("tiempo_total", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("cambio_neumaticos", new TableInfo.Column("cambio_neumaticos", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("numero_neumaticos_cambiados", new TableInfo.Column("numero_neumaticos_cambiados", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("estado", new TableInfo.Column("estado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("motivo_fallo", new TableInfo.Column("motivo_fallo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("mecanico_principal", new TableInfo.Column("mecanico_principal", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPitStops.put("fecha_hora", new TableInfo.Column("fecha_hora", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPitStops = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPitStops = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPitStops = new TableInfo("pit_stops", _columnsPitStops, _foreignKeysPitStops, _indicesPitStops);
        final TableInfo _existingPitStops = TableInfo.read(db, "pit_stops");
        if (!_infoPitStops.equals(_existingPitStops)) {
          return new RoomOpenHelper.ValidationResult(false, "pit_stops(com.f1pitstop.app.data.model.PitStop).\n"
                  + " Expected:\n" + _infoPitStops + "\n"
                  + " Found:\n" + _existingPitStops);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b0f64ec6806f4e67efb4f8e6467cf565", "be630d9144f13cd3c176254ecdc4f1d8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "pit_stops");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `pit_stops`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PitStopDao.class, PitStopDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PitStopDao pitStopDao() {
    if (_pitStopDao != null) {
      return _pitStopDao;
    } else {
      synchronized(this) {
        if(_pitStopDao == null) {
          _pitStopDao = new PitStopDao_Impl(this);
        }
        return _pitStopDao;
      }
    }
  }
}
