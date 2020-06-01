package pe.com.minagri.mercados.sql.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MapaDataBase_Impl extends MapaDataBase {
  private volatile InformacionDao _informacionDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `INFORMACION` (`uid` TEXT NOT NULL, `idMercado` TEXT NOT NULL, `latitud` REAL NOT NULL, `longitud` REAL NOT NULL, `fechaCreacion` TEXT NOT NULL, `fechaActualizacion` TEXT NOT NULL, `aforo` TEXT NOT NULL, `nivel` TEXT NOT NULL, `incidente` TEXT NOT NULL, PRIMARY KEY(`uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"fea6c47c5b44cd8a2aef54fb6796fd9e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `INFORMACION`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsINFORMACION = new HashMap<String, TableInfo.Column>(9);
        _columnsINFORMACION.put("uid", new TableInfo.Column("uid", "TEXT", true, 1));
        _columnsINFORMACION.put("idMercado", new TableInfo.Column("idMercado", "TEXT", true, 0));
        _columnsINFORMACION.put("latitud", new TableInfo.Column("latitud", "REAL", true, 0));
        _columnsINFORMACION.put("longitud", new TableInfo.Column("longitud", "REAL", true, 0));
        _columnsINFORMACION.put("fechaCreacion", new TableInfo.Column("fechaCreacion", "TEXT", true, 0));
        _columnsINFORMACION.put("fechaActualizacion", new TableInfo.Column("fechaActualizacion", "TEXT", true, 0));
        _columnsINFORMACION.put("aforo", new TableInfo.Column("aforo", "TEXT", true, 0));
        _columnsINFORMACION.put("nivel", new TableInfo.Column("nivel", "TEXT", true, 0));
        _columnsINFORMACION.put("incidente", new TableInfo.Column("incidente", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysINFORMACION = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesINFORMACION = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoINFORMACION = new TableInfo("INFORMACION", _columnsINFORMACION, _foreignKeysINFORMACION, _indicesINFORMACION);
        final TableInfo _existingINFORMACION = TableInfo.read(_db, "INFORMACION");
        if (! _infoINFORMACION.equals(_existingINFORMACION)) {
          throw new IllegalStateException("Migration didn't properly handle INFORMACION(pe.com.minagri.mercados.sql.Informacion).\n"
                  + " Expected:\n" + _infoINFORMACION + "\n"
                  + " Found:\n" + _existingINFORMACION);
        }
      }
    }, "fea6c47c5b44cd8a2aef54fb6796fd9e", "6ee88f90639f270c655e6ebcf9bc4c93");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "INFORMACION");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `INFORMACION`");
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
  public InformacionDao getInformacionDao() {
    if (_informacionDao != null) {
      return _informacionDao;
    } else {
      synchronized(this) {
        if(_informacionDao == null) {
          _informacionDao = new InformacionDao_Impl(this);
        }
        return _informacionDao;
      }
    }
  }
}
