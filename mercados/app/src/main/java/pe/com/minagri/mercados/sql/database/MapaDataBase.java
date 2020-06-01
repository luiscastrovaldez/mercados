package pe.com.minagri.mercados.sql.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import pe.com.minagri.mercados.sql.Informacion;

@Database(entities = {Informacion.class}, version = 1)
public abstract class MapaDataBase extends RoomDatabase {

    public abstract InformacionDao getInformacionDao();

}
