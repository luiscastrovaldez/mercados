package pe.com.minagri.mercados.sql.database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import java.util.List;

import pe.com.minagri.mercados.sql.Informacion;


public class ServiceDatabase {

    @SuppressLint("StaticFieldLeak")
    private static ServiceDatabase serviceDatabase;

    private InformacionDao informacionDao;


    private static final String DB_NAME = "/storage/emulated/0/mercados/db/mercados.db";
    //private static final String DB_NAME = "mercados.db";

    private ServiceDatabase(Context context) {
        Context appContext = context.getApplicationContext();
        MapaDataBase database = Room.databaseBuilder(appContext, MapaDataBase.class, DB_NAME)
                .allowMainThreadQueries().build();
        informacionDao = database.getInformacionDao();


    }

    public static ServiceDatabase get(Context context) {
        if (serviceDatabase == null) {
            serviceDatabase = new ServiceDatabase(context);
        }
        return serviceDatabase;
    }

   public List<Informacion> getInformacion() {
        return informacionDao.getInformacion();
    }

    public Informacion getInformacionById(String uid) {
        return informacionDao.getInformacionById(uid);
    }

    public Informacion getInformacionByIdMapa(String idMapa) {
        return informacionDao.getInformacionByIdMapa(idMapa);
    }


    public void addInformacion(Informacion informacion) {
         informacionDao.addInformacion(informacion);
    }

    public void updateInformacion(Informacion informacion) {
         informacionDao.updateInformacion(informacion);
    }

    public void deleteInformacion(Informacion informacion) {
        informacionDao.deleteInformacion(informacion);
    }

}
