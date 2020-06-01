package pe.com.minagri.mercados;

import android.Manifest;
import android.os.Environment;

import java.io.File;

public class Constant {
    public static final int PERMISSION_ALL = 1;
    public static final String PATH_MERCADOS = Environment.getExternalStorageDirectory() + File.separator + "mercados";
    public static final String PATH_MERCADOS_KML = Environment.getExternalStorageDirectory() + File.separator + "mercados" + File.separator + "kml";
    public static final String PATH_MERCADOS_ARCHIVOS = Environment.getExternalStorageDirectory() + File.separator + "mercados" + File.separator + "csv";
    public static final String PATH_MERCADOS_DB = Environment.getExternalStorageDirectory() + File.separator + "mercados" + File.separator + "db";


    public static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE

    };

}
