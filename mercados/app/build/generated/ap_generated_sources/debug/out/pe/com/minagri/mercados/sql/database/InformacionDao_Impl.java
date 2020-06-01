package pe.com.minagri.mercados.sql.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import pe.com.minagri.mercados.sql.Informacion;

@SuppressWarnings("unchecked")
public final class InformacionDao_Impl implements InformacionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfInformacion;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfInformacion;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfInformacion;

  public InformacionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInformacion = new EntityInsertionAdapter<Informacion>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `INFORMACION`(`uid`,`idMercado`,`latitud`,`longitud`,`fechaCreacion`,`fechaActualizacion`,`aforo`,`nivel`,`incidente`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Informacion value) {
        if (value.uid == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.uid);
        }
        if (value.idMercado == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.idMercado);
        }
        if (value.latitud == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindDouble(3, value.latitud);
        }
        if (value.longitud == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.longitud);
        }
        if (value.fechaCreacion == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.fechaCreacion);
        }
        if (value.fechaActualizacion == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.fechaActualizacion);
        }
        if (value.aforo == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.aforo);
        }
        if (value.nivel == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.nivel);
        }
        if (value.incidente == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.incidente);
        }
      }
    };
    this.__deletionAdapterOfInformacion = new EntityDeletionOrUpdateAdapter<Informacion>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `INFORMACION` WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Informacion value) {
        if (value.uid == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.uid);
        }
      }
    };
    this.__updateAdapterOfInformacion = new EntityDeletionOrUpdateAdapter<Informacion>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `INFORMACION` SET `uid` = ?,`idMercado` = ?,`latitud` = ?,`longitud` = ?,`fechaCreacion` = ?,`fechaActualizacion` = ?,`aforo` = ?,`nivel` = ?,`incidente` = ? WHERE `uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Informacion value) {
        if (value.uid == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.uid);
        }
        if (value.idMercado == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.idMercado);
        }
        if (value.latitud == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindDouble(3, value.latitud);
        }
        if (value.longitud == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.longitud);
        }
        if (value.fechaCreacion == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.fechaCreacion);
        }
        if (value.fechaActualizacion == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.fechaActualizacion);
        }
        if (value.aforo == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.aforo);
        }
        if (value.nivel == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.nivel);
        }
        if (value.incidente == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.incidente);
        }
        if (value.uid == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.uid);
        }
      }
    };
  }

  @Override
  public void addInformacion(Informacion informacion) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfInformacion.insert(informacion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteInformacion(Informacion informacion) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfInformacion.handle(informacion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateInformacion(Informacion informacion) {
    __db.beginTransaction();
    try {
      __updateAdapterOfInformacion.handle(informacion);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Informacion> getInformacion() {
    final String _sql = "SELECT * FROM INFORMACION";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfIdMercado = _cursor.getColumnIndexOrThrow("idMercado");
      final int _cursorIndexOfLatitud = _cursor.getColumnIndexOrThrow("latitud");
      final int _cursorIndexOfLongitud = _cursor.getColumnIndexOrThrow("longitud");
      final int _cursorIndexOfFechaCreacion = _cursor.getColumnIndexOrThrow("fechaCreacion");
      final int _cursorIndexOfFechaActualizacion = _cursor.getColumnIndexOrThrow("fechaActualizacion");
      final int _cursorIndexOfAforo = _cursor.getColumnIndexOrThrow("aforo");
      final int _cursorIndexOfNivel = _cursor.getColumnIndexOrThrow("nivel");
      final int _cursorIndexOfIncidente = _cursor.getColumnIndexOrThrow("incidente");
      final List<Informacion> _result = new ArrayList<Informacion>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Informacion _item;
        _item = new Informacion();
        _item.uid = _cursor.getString(_cursorIndexOfUid);
        _item.idMercado = _cursor.getString(_cursorIndexOfIdMercado);
        if (_cursor.isNull(_cursorIndexOfLatitud)) {
          _item.latitud = null;
        } else {
          _item.latitud = _cursor.getDouble(_cursorIndexOfLatitud);
        }
        if (_cursor.isNull(_cursorIndexOfLongitud)) {
          _item.longitud = null;
        } else {
          _item.longitud = _cursor.getDouble(_cursorIndexOfLongitud);
        }
        _item.fechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
        _item.fechaActualizacion = _cursor.getString(_cursorIndexOfFechaActualizacion);
        _item.aforo = _cursor.getString(_cursorIndexOfAforo);
        _item.nivel = _cursor.getString(_cursorIndexOfNivel);
        _item.incidente = _cursor.getString(_cursorIndexOfIncidente);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Informacion getInformacionById(String uid) {
    final String _sql = "SELECT * FROM INFORMACION WHERE uid LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfIdMercado = _cursor.getColumnIndexOrThrow("idMercado");
      final int _cursorIndexOfLatitud = _cursor.getColumnIndexOrThrow("latitud");
      final int _cursorIndexOfLongitud = _cursor.getColumnIndexOrThrow("longitud");
      final int _cursorIndexOfFechaCreacion = _cursor.getColumnIndexOrThrow("fechaCreacion");
      final int _cursorIndexOfFechaActualizacion = _cursor.getColumnIndexOrThrow("fechaActualizacion");
      final int _cursorIndexOfAforo = _cursor.getColumnIndexOrThrow("aforo");
      final int _cursorIndexOfNivel = _cursor.getColumnIndexOrThrow("nivel");
      final int _cursorIndexOfIncidente = _cursor.getColumnIndexOrThrow("incidente");
      final Informacion _result;
      if(_cursor.moveToFirst()) {
        _result = new Informacion();
        _result.uid = _cursor.getString(_cursorIndexOfUid);
        _result.idMercado = _cursor.getString(_cursorIndexOfIdMercado);
        if (_cursor.isNull(_cursorIndexOfLatitud)) {
          _result.latitud = null;
        } else {
          _result.latitud = _cursor.getDouble(_cursorIndexOfLatitud);
        }
        if (_cursor.isNull(_cursorIndexOfLongitud)) {
          _result.longitud = null;
        } else {
          _result.longitud = _cursor.getDouble(_cursorIndexOfLongitud);
        }
        _result.fechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
        _result.fechaActualizacion = _cursor.getString(_cursorIndexOfFechaActualizacion);
        _result.aforo = _cursor.getString(_cursorIndexOfAforo);
        _result.nivel = _cursor.getString(_cursorIndexOfNivel);
        _result.incidente = _cursor.getString(_cursorIndexOfIncidente);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Informacion getInformacionByIdMapa(String idMercado) {
    final String _sql = "SELECT * FROM INFORMACION WHERE idMercado LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (idMercado == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, idMercado);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUid = _cursor.getColumnIndexOrThrow("uid");
      final int _cursorIndexOfIdMercado = _cursor.getColumnIndexOrThrow("idMercado");
      final int _cursorIndexOfLatitud = _cursor.getColumnIndexOrThrow("latitud");
      final int _cursorIndexOfLongitud = _cursor.getColumnIndexOrThrow("longitud");
      final int _cursorIndexOfFechaCreacion = _cursor.getColumnIndexOrThrow("fechaCreacion");
      final int _cursorIndexOfFechaActualizacion = _cursor.getColumnIndexOrThrow("fechaActualizacion");
      final int _cursorIndexOfAforo = _cursor.getColumnIndexOrThrow("aforo");
      final int _cursorIndexOfNivel = _cursor.getColumnIndexOrThrow("nivel");
      final int _cursorIndexOfIncidente = _cursor.getColumnIndexOrThrow("incidente");
      final Informacion _result;
      if(_cursor.moveToFirst()) {
        _result = new Informacion();
        _result.uid = _cursor.getString(_cursorIndexOfUid);
        _result.idMercado = _cursor.getString(_cursorIndexOfIdMercado);
        if (_cursor.isNull(_cursorIndexOfLatitud)) {
          _result.latitud = null;
        } else {
          _result.latitud = _cursor.getDouble(_cursorIndexOfLatitud);
        }
        if (_cursor.isNull(_cursorIndexOfLongitud)) {
          _result.longitud = null;
        } else {
          _result.longitud = _cursor.getDouble(_cursorIndexOfLongitud);
        }
        _result.fechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
        _result.fechaActualizacion = _cursor.getString(_cursorIndexOfFechaActualizacion);
        _result.aforo = _cursor.getString(_cursorIndexOfAforo);
        _result.nivel = _cursor.getString(_cursorIndexOfNivel);
        _result.incidente = _cursor.getString(_cursorIndexOfIncidente);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
