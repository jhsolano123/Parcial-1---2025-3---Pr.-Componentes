package com.f1pitstop.app.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.f1pitstop.app.data.model.Escuderia;
import com.f1pitstop.app.data.model.EstadoPitStop;
import com.f1pitstop.app.data.model.PitStop;
import com.f1pitstop.app.data.model.TipoNeumatico;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PitStopDao_Impl implements PitStopDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PitStop> __insertionAdapterOfPitStop;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<PitStop> __deletionAdapterOfPitStop;

  private final EntityDeletionOrUpdateAdapter<PitStop> __updateAdapterOfPitStop;

  private final SharedSQLiteStatement __preparedStmtOfDeletePitStopById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllPitStops;

  public PitStopDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPitStop = new EntityInsertionAdapter<PitStop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `pit_stops` (`id`,`piloto`,`escuderia`,`tiempo_total`,`cambio_neumaticos`,`numero_neumaticos_cambiados`,`estado`,`motivo_fallo`,`mecanico_principal`,`fecha_hora`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PitStop entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPiloto());
        final String _tmp = __converters.fromEscuderia(entity.getEscuderia());
        statement.bindString(3, _tmp);
        statement.bindDouble(4, entity.getTiempoTotal());
        final String _tmp_1 = __converters.fromTipoNeumatico(entity.getCambioNeumaticos());
        statement.bindString(5, _tmp_1);
        statement.bindLong(6, entity.getNumeroNeumaticosCambiados());
        final String _tmp_2 = __converters.fromEstadoPitStop(entity.getEstado());
        statement.bindString(7, _tmp_2);
        if (entity.getMotivoFallo() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMotivoFallo());
        }
        statement.bindString(9, entity.getMecanicoPrincipal());
        statement.bindLong(10, entity.getFechaHora());
      }
    };
    this.__deletionAdapterOfPitStop = new EntityDeletionOrUpdateAdapter<PitStop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pit_stops` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PitStop entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPitStop = new EntityDeletionOrUpdateAdapter<PitStop>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pit_stops` SET `id` = ?,`piloto` = ?,`escuderia` = ?,`tiempo_total` = ?,`cambio_neumaticos` = ?,`numero_neumaticos_cambiados` = ?,`estado` = ?,`motivo_fallo` = ?,`mecanico_principal` = ?,`fecha_hora` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PitStop entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPiloto());
        final String _tmp = __converters.fromEscuderia(entity.getEscuderia());
        statement.bindString(3, _tmp);
        statement.bindDouble(4, entity.getTiempoTotal());
        final String _tmp_1 = __converters.fromTipoNeumatico(entity.getCambioNeumaticos());
        statement.bindString(5, _tmp_1);
        statement.bindLong(6, entity.getNumeroNeumaticosCambiados());
        final String _tmp_2 = __converters.fromEstadoPitStop(entity.getEstado());
        statement.bindString(7, _tmp_2);
        if (entity.getMotivoFallo() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMotivoFallo());
        }
        statement.bindString(9, entity.getMecanicoPrincipal());
        statement.bindLong(10, entity.getFechaHora());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeletePitStopById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pit_stops WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllPitStops = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pit_stops";
        return _query;
      }
    };
  }

  @Override
  public Object insertPitStop(final PitStop pitStop, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPitStop.insertAndReturnId(pitStop);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePitStop(final PitStop pitStop, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPitStop.handle(pitStop);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePitStop(final PitStop pitStop, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPitStop.handle(pitStop);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePitStopById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePitStopById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePitStopById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllPitStops(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllPitStops.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllPitStops.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PitStop>> getAllPitStops() {
    final String _sql = "SELECT * FROM pit_stops ORDER BY fecha_hora DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pit_stops"}, new Callable<List<PitStop>>() {
      @Override
      @NonNull
      public List<PitStop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final List<PitStop> _result = new ArrayList<PitStop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PitStop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _item = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPitStopById(final long id, final Continuation<? super PitStop> $completion) {
    final String _sql = "SELECT * FROM pit_stops WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PitStop>() {
      @Override
      @Nullable
      public PitStop call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final PitStop _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _result = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PitStop>> searchPitStops(final String searchQuery) {
    final String _sql = "\n"
            + "        SELECT * FROM pit_stops \n"
            + "        WHERE piloto LIKE '%' || ? || '%' \n"
            + "        OR escuderia LIKE '%' || ? || '%'\n"
            + "        ORDER BY fecha_hora DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, searchQuery);
    _argIndex = 2;
    _statement.bindString(_argIndex, searchQuery);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pit_stops"}, new Callable<List<PitStop>>() {
      @Override
      @NonNull
      public List<PitStop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final List<PitStop> _result = new ArrayList<PitStop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PitStop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _item = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getFastestTime(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT MIN(tiempo_total) FROM pit_stops WHERE estado = 'OK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageTime(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT AVG(tiempo_total) FROM pit_stops WHERE estado = 'OK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM pit_stops";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLastFivePitStops(final Continuation<? super List<PitStop>> $completion) {
    final String _sql = "SELECT * FROM pit_stops ORDER BY fecha_hora DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PitStop>>() {
      @Override
      @NonNull
      public List<PitStop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final List<PitStop> _result = new ArrayList<PitStop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PitStop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _item = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PitStop>> getPitStopsByEscuderia(final String escuderia) {
    final String _sql = "SELECT * FROM pit_stops WHERE escuderia = ? ORDER BY fecha_hora DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, escuderia);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pit_stops"}, new Callable<List<PitStop>>() {
      @Override
      @NonNull
      public List<PitStop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final List<PitStop> _result = new ArrayList<PitStop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PitStop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _item = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PitStop>> getPitStopsByEstado(final String estado) {
    final String _sql = "SELECT * FROM pit_stops WHERE estado = ? ORDER BY fecha_hora DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, estado);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pit_stops"}, new Callable<List<PitStop>>() {
      @Override
      @NonNull
      public List<PitStop> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPiloto = CursorUtil.getColumnIndexOrThrow(_cursor, "piloto");
          final int _cursorIndexOfEscuderia = CursorUtil.getColumnIndexOrThrow(_cursor, "escuderia");
          final int _cursorIndexOfTiempoTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "tiempo_total");
          final int _cursorIndexOfCambioNeumaticos = CursorUtil.getColumnIndexOrThrow(_cursor, "cambio_neumaticos");
          final int _cursorIndexOfNumeroNeumaticosCambiados = CursorUtil.getColumnIndexOrThrow(_cursor, "numero_neumaticos_cambiados");
          final int _cursorIndexOfEstado = CursorUtil.getColumnIndexOrThrow(_cursor, "estado");
          final int _cursorIndexOfMotivoFallo = CursorUtil.getColumnIndexOrThrow(_cursor, "motivo_fallo");
          final int _cursorIndexOfMecanicoPrincipal = CursorUtil.getColumnIndexOrThrow(_cursor, "mecanico_principal");
          final int _cursorIndexOfFechaHora = CursorUtil.getColumnIndexOrThrow(_cursor, "fecha_hora");
          final List<PitStop> _result = new ArrayList<PitStop>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PitStop _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPiloto;
            _tmpPiloto = _cursor.getString(_cursorIndexOfPiloto);
            final Escuderia _tmpEscuderia;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfEscuderia);
            _tmpEscuderia = __converters.toEscuderia(_tmp);
            final double _tmpTiempoTotal;
            _tmpTiempoTotal = _cursor.getDouble(_cursorIndexOfTiempoTotal);
            final TipoNeumatico _tmpCambioNeumaticos;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfCambioNeumaticos);
            _tmpCambioNeumaticos = __converters.toTipoNeumatico(_tmp_1);
            final int _tmpNumeroNeumaticosCambiados;
            _tmpNumeroNeumaticosCambiados = _cursor.getInt(_cursorIndexOfNumeroNeumaticosCambiados);
            final EstadoPitStop _tmpEstado;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfEstado);
            _tmpEstado = __converters.toEstadoPitStop(_tmp_2);
            final String _tmpMotivoFallo;
            if (_cursor.isNull(_cursorIndexOfMotivoFallo)) {
              _tmpMotivoFallo = null;
            } else {
              _tmpMotivoFallo = _cursor.getString(_cursorIndexOfMotivoFallo);
            }
            final String _tmpMecanicoPrincipal;
            _tmpMecanicoPrincipal = _cursor.getString(_cursorIndexOfMecanicoPrincipal);
            final long _tmpFechaHora;
            _tmpFechaHora = _cursor.getLong(_cursorIndexOfFechaHora);
            _item = new PitStop(_tmpId,_tmpPiloto,_tmpEscuderia,_tmpTiempoTotal,_tmpCambioNeumaticos,_tmpNumeroNeumaticosCambiados,_tmpEstado,_tmpMotivoFallo,_tmpMecanicoPrincipal,_tmpFechaHora);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
