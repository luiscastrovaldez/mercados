package pe.com.minagri.mercados.sql.database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pe.com.minagri.mercados.sql.Informacion;


@Dao
public interface InformacionDao {

    @Query("SELECT * FROM INFORMACION")
    List<Informacion> getInformacion();

    @Query("SELECT * FROM INFORMACION WHERE uid LIKE :uid")
    Informacion getInformacionById(String uid);

    @Query("SELECT * FROM INFORMACION WHERE idMercado LIKE :idMercado")
    Informacion getInformacionByIdMapa(String idMercado);

    @Insert
    void addInformacion(Informacion informacion);

    @Delete
    void deleteInformacion(Informacion informacion);

    @Update
    void updateInformacion(Informacion informacion);

}
