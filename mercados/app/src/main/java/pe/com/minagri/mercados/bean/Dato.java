package pe.com.minagri.mercados.bean;

public class Dato {

    private String descripcion;
    private double longitud;
    private double latitud;
    private String name;
    private String idMercado;

    public Dato(){

    }

    public Dato(String descripcion, double longitud, double latitud, String name, String idMercado) {
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.name = name;
        this.idMercado = idMercado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(String idMercado) {
        this.idMercado = idMercado;
    }
}
