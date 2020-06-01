package pe.com.minagri.mercados.sql;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "INFORMACION")
public class Informacion {

    @PrimaryKey
    @NonNull
    public String uid;

    @NonNull
    @ColumnInfo(name = "idMercado")
    public String idMercado;

    @NonNull
    @ColumnInfo(name = "latitud")
    public Double latitud;

    @NonNull
    @ColumnInfo(name = "longitud")
    public Double longitud;

    @NonNull
    @ColumnInfo(name = "fechaCreacion")
    public String fechaCreacion;

    @NonNull
    @ColumnInfo(name = "fechaActualizacion")
    public String fechaActualizacion;


    @NonNull
    @ColumnInfo(name = "aforo")
    public String aforo;

    @NonNull
    @ColumnInfo(name = "nivel")
    public String nivel;

    @NonNull
    @ColumnInfo(name = "incidente")
    public String incidente;

    public Informacion() {
        uid = UUID.randomUUID().toString();
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @NonNull
    public String getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(@NonNull String idMercado) {
        this.idMercado = idMercado;
    }

    @NonNull
    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(@NonNull Double latitud) {
        this.latitud = latitud;
    }

    @NonNull
    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(@NonNull Double longitud) {
        this.longitud = longitud;
    }

    @NonNull
    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(@NonNull String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @NonNull
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(@NonNull String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @NonNull
    public String getAforo() {
        return aforo;
    }

    public void setAforo(@NonNull String aforo) {
        this.aforo = aforo;
    }

    @NonNull
    public String getNivel() {
        return nivel;
    }

    public void setNivel(@NonNull String nivel) {
        this.nivel = nivel;
    }

    @NonNull
    public String getIncidente() {
        return incidente;
    }

    public void setIncidente(@NonNull String incidente) {
        this.incidente = incidente;
    }
}
