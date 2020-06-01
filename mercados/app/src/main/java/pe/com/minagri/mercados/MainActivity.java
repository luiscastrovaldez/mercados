package pe.com.minagri.mercados;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pe.com.minagri.mercados.bean.Dato;
import pe.com.minagri.mercados.kml.ParsingStructure;
import pe.com.minagri.mercados.kml.SAXXMLParser;

import pe.com.minagri.mercados.sql.database.ServiceDatabase;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private static MarkerOptions marcadorMercado;
    private LatLng mercadoEjemplo = null;
    private static CameraPosition cameraPosition;

    private FloatingActionButton tipoMapa, actualPosicion,opendashboard;

    private Intent intent;
    private ServiceDatabase serviceDatabase;

    private LocationTrack locationTrack;
    public static List<ParsingStructure> parsingStr = null;
    private static MarkerOptions posicionActualMarker;

    private static List<LatLng> points;

    private double longitude;
    private double latitude;

    private boolean existeUbicacion;

    private AssetManager assetManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        existeUbicacion = false;
        assetManager = getBaseContext().getAssets();
        /*
        if (!hasPermissions(this, Constant.PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, Constant.PERMISSIONS, Constant.PERMISSION_ALL);
        }
        */
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            componentes();

            GenerandoMapa generandoMapa = new GenerandoMapa();
            generandoMapa.execute();
        }





    }


    private void crearFolder(){
        File mediaStorageDir = new File(Constant.PATH_MERCADOS);
        String csvFile = mediaStorageDir.getAbsolutePath() + File.separator;

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(getApplicationContext(), "Error al crear la carpeta del MERCADOS ", Toast.LENGTH_LONG).show();
                return;
            }
        }

        mediaStorageDir = new File(Constant.PATH_MERCADOS_DB);
        csvFile = mediaStorageDir.getAbsolutePath() + File.separator;

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(getApplicationContext(), "Error al crear la carpeta db ", Toast.LENGTH_LONG).show();
                return;
            }
        }

        /*
        mediaStorageDir = new File(Constant.PATH_MERCADOS_ARCHIVOS);
        csvFile = mediaStorageDir.getAbsolutePath() + File.separator;

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(getApplicationContext(), "Error al crear la carpeta ARCHIVOS ", Toast.LENGTH_LONG).show();
                return;
            }
        }

        mediaStorageDir = new File(Constant.PATH_MERCADOS_KML);
        csvFile = mediaStorageDir.getAbsolutePath() + File.separator;

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Toast.makeText(getApplicationContext(), "Error al crear la carpeta kml ", Toast.LENGTH_LONG).show();
                return;
            }
        }
        */
    }

    private void componentes() {

        crearFolder();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tipoMapa = (FloatingActionButton) findViewById(R.id.tipoMapa);
        opendashboard = (FloatingActionButton) findViewById(R.id.opendashboard);
        actualPosicion = (FloatingActionButton) findViewById(R.id.ubicacion);

        actualPosicion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                2);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    ObteniendoCoordenadas load = new ObteniendoCoordenadas();
                    load.execute();
                }


            }
        });

        tipoMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mMap.getMapType() == 1) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        opendashboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivityForResult(intent, 20);
            }
        });

        serviceDatabase = ServiceDatabase.get(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mercadoEjemplo = new LatLng(new Double(-11.994818), new Double(-77.076608));
        //marcadorMercado = new MarkerOptions().position(mercadoEjemplo).title("title");
        //marcadorMercado.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


        //mMap.addMarker(marcadorMercado);
        //cameraProperties(mercadoEjemplo);

        mMap.setOnMarkerClickListener(this);


    }


    private void cameraProperties(LatLng latLng) {

        if (latLng != null) {
            cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
        }

    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Title...");
        WebView webview = (WebView) dialog.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        String html = marker.getSnippet();
        final String idMercado = marker.getTitle().split(" ")[0];
        final String titulo = marker.getTitle();
        webview.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.dismiss();

                if (existeUbicacion){
                    intent = new Intent(getApplicationContext(), FichaActivity.class);
                    intent.putExtra("idMercado", idMercado.toString());
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("latitud", latitude);
                    intent.putExtra("longitud", longitude);
                    startActivityForResult(intent, 10);
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Informacion")
                            .setMessage("Debe calcular su ubicacion actual")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }

                //double distancia = distanciaCoord(latitude,longitude,marker.getPosition().latitude,marker.getPosition().longitude);
                //distancia = distancia * 1000;
                //if (distancia < 50){

                /*} else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Informacion")
                            .setMessage("Debe estar a 50 metros del mercado para guardar la informacion")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }

                 */
                //double idstancia1 = distance(latitude,longitude,marker.getPosition().latitude,marker.getPosition().longitude,"K");

            }
        });

        dialog.show();





        return true;
    }

    private class ObteniendoCoordenadas extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        protected void onPreExecute() {

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Obteniendo Ubicacion Actual...");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected Void doInBackground(final String... args) {
            try {
                Thread.sleep(1000);


                runOnUiThread(new Runnable() {
                    public void run() {
                        locationTrack = new LocationTrack(MainActivity.this);
                        if (locationTrack.canGetLocation()) {
                            longitude = locationTrack.getLongitude();
                            latitude = locationTrack.getLatitude();
                            existeUbicacion  = true;
                        } else {
                            locationTrack.showSettingsAlert();
                        }
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final Void unused) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            posicionActualMarker = new MarkerOptions().position(new LatLng(latitude, longitude));
            String title = "Actual Posicion " + " Lat: " + latitude + " Long: " + longitude;
            posicionActualMarker.title(title);
            posicionActualMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

            mMap.addMarker(posicionActualMarker);

            cameraProperties(new LatLng(latitude, longitude));

        }
    }

    public class GenerandoMapa extends AsyncTask<String, Integer, String> {
        private String result;
        private ProgressDialog mProgressDialog;
        private int mProgressStatus = 0;
        private List<Dato> datos;

        public GenerandoMapa() {

        }

        @Override
        public void onPreExecute() {
            mProgressStatus = 0;
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Cargando mercados");
            mProgressDialog.show();
        }



        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;

            try {

                datos = new ArrayList<>();
                    //is = new FileInputStream(Constant.PATH_MERCADOS_KML + File.separator + "mercados_lima_callao.kml");
                    is = assetManager.open("mercados_lima_callao.kml", AssetManager.ACCESS_BUFFER);
                    parsingStr = SAXXMLParser.parse(is);
                    Dato dato;
                    for (ParsingStructure parsingStructure : parsingStr) {
                        points = new ArrayList<LatLng>();
                        String coordenadas = parsingStructure.getCoordinates();
                        final String idMercado =  parsingStructure.getName().split(" ")[0];
                        //Informacion informacion = serviceDatabase.getInformacionByIdMapa(idMercado.toString());
                        String[] puntos = coordenadas.split(",");
                        double latitud = Double.parseDouble(puntos[1]);
                        double longitud = Double.parseDouble(puntos[0]);
                        dato = new Dato(parsingStructure.getDescription(), longitud, latitud, parsingStructure.getName(),idMercado);
                        datos.add(dato);
                        mProgressStatus++;
                        publishProgress(mProgressStatus);
                    }

                is.close();
                result = "in";
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
                result = "out";
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(mProgressStatus);
        }

        @Override
        public void onPostExecute(String result) {

            if (result.equalsIgnoreCase("in")) {

                LatLng latLng = null;
                for (Dato dato: datos) {

                    latLng = new LatLng(dato.getLatitud(), dato.getLongitud());
                    posicionActualMarker = new MarkerOptions().position(latLng)
                            .title(dato.getName())
                            .snippet(dato.getDescripcion());
                    posicionActualMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(posicionActualMarker);

                    /*
                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(latLng)
                            .radius(50)
                            .strokeColor(Color.BLUE)
                            .fillColor(Color.TRANSPARENT));
                    */

                }
                cameraProperties(latLng);
            }


            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    componentes();

                    GenerandoMapa generandoMapa = new GenerandoMapa();
                    generandoMapa.execute();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ObteniendoCoordenadas load = new ObteniendoCoordenadas();
                    load.execute();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        //double radioTierra = 3958.75;//en millas
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
