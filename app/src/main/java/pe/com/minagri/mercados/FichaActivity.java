package pe.com.minagri.mercados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import pe.com.minagri.mercados.bean.Combo;
import pe.com.minagri.mercados.sql.Informacion;
import pe.com.minagri.mercados.sql.database.ServiceDatabase;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public class FichaActivity extends AppCompatActivity {


    private Intent intent;
    private Serializable idMercado;
    private Serializable titulo;
    private Serializable latitud;
    private Serializable longitud;
    private TextView tituloView;
    private Spinner niveles, aforos, incidentes;
    private Button btnGuardar;

    private ServiceDatabase serviceDatabase;
    private Informacion informacion = null;

    String server = "200.48.31.125";
    int port = 21;
    String user = "FTP_ENCUESTAS";
    String pass = "S2Dcwtr$15$";

    private File[] listOfFiles;

    private static final String SEPARATOR = ",";

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;

    //private final static String URL = "http://winappdes.minagri.gob.pe/wsmercadoseguro/api/MercadoSeguro/InsertarAforo/";
    private final static String URL = "http://mercadoseguro.minagri.gob.pe/api/MercadoSeguro/InsertarAforo/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);
        intent =  getIntent();
        idMercado = intent.getSerializableExtra("idMercado");
        titulo = intent.getSerializableExtra("titulo");

        latitud = intent.getSerializableExtra("latitud");
        longitud = intent.getSerializableExtra("longitud");

        serviceDatabase = ServiceDatabase.get(this);
        tituloView = (TextView) findViewById(R.id.titulo);
        niveles = (Spinner) findViewById(R.id.niveles);
        aforos = (Spinner) findViewById(R.id.aforo);
        incidentes= (Spinner) findViewById(R.id.incidentes);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        //btnEnviar = (Button) findViewById(R.id.btnEnviar);
        informacion = serviceDatabase.getInformacionByIdMapa(idMercado.toString());
        tituloView.setText(titulo.toString());

        addAforos();
        addIncidentes();
        addNiveles();

        if (informacion != null) {
            niveles.setSelection(new Integer(informacion.getNivel()).intValue() - 1);

            aforos.setSelection(new Integer(informacion.getAforo()).intValue() - 1);

            incidentes.setSelection(new Integer(informacion.getIncidente()).intValue() - 1);
        }

/*
        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(FichaActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Enviando Informacion")
                        .setMessage("¿Desea enviar la informacion?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    File mediaStorageDir =  new File (Constant.PATH_MERCADOS_ARCHIVOS + File.separator);
                                    String csvFile = mediaStorageDir.getAbsolutePath() + File.separator;
                                    informacion = serviceDatabase.getInformacionByIdMapa(idMercado.toString());

                                    csvFile = csvFile + informacion.getIdMercado().trim() + ".xls";
                                    File file = new File(csvFile);

                                    WorkbookSettings wbSettings = new WorkbookSettings();
                                    wbSettings.setLocale(new Locale("en", "EN"));
                                    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                                    workbook.createSheet("MercadoInformacion", 0);
                                    WritableSheet excelSheet = workbook.getSheet(0);

                                    createLabel(excelSheet,0,0, "IdMercado");
                                    createLabel(excelSheet,1,0, "Latitud");
                                    createLabel(excelSheet,2,0, "Longitud");
                                    createLabel(excelSheet,3,0, "Aforo");
                                    createLabel(excelSheet,4,0, "Nivel");
                                    createLabel(excelSheet,5,0, "FechaCreacion");
                                    createLabel(excelSheet,6,0, "FechaActualizacion");

                                    createLabel(excelSheet,0,1, informacion.getIdMercado().toString());
                                    createLabel(excelSheet,1,1, informacion.getLatitud().toString());
                                    createLabel(excelSheet,2,1, informacion.getLongitud().toString());
                                    createLabel(excelSheet,3,1, informacion.getAforo());
                                    createLabel(excelSheet,4,1, informacion.getNivel());
                                    createLabel(excelSheet,5,1, informacion.getFechaCreacion());
                                    createLabel(excelSheet,6,1, informacion.getFechaActualizacion());


                                    workbook.write();
                                    workbook.close();

                                    LoadAssync load = new LoadAssync(null);
                                    load.execute();

                                } catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Error exportando la informacion " + e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"Cancelado",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });
        */



        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (String.valueOf(niveles.getSelectedItem()).equals("Nivel de Abastecimiento") || String.valueOf(aforos.getSelectedItem()).equals("Nivel de Aforo")) {
                    Toast.makeText(FichaActivity.this,
                            "Debe seleccionar el Nivel de Abastecimiento o Nivel de Aforo",
                            Toast.LENGTH_SHORT).show();
                    return ;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(FichaActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmacion")
                        .setMessage("¿ Desea enviar la Informacion ?")

                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                               try {
                                   if (informacion != null){
                                       informacion.setAforo(String.valueOf(aforos.getSelectedItemId() + 1));
                                       informacion.setNivel(String.valueOf(niveles.getSelectedItemId() + 1));
                                       informacion.setIncidente(String.valueOf(incidentes.getSelectedItemId() + 1));
                                       informacion.setFechaActualizacion(new Date().toString());
                                       informacion.setLatitud(new Double(latitud.toString()));
                                       informacion.setLongitud(new Double(longitud.toString()));
                                       informacion.setIdMercado(idMercado.toString());
                                       serviceDatabase.updateInformacion(informacion);
                                   } else {
                                       informacion = new Informacion();
                                       informacion.setAforo(String.valueOf(aforos.getSelectedItemId() + 1));
                                       informacion.setNivel(String.valueOf(niveles.getSelectedItemId() + 1));
                                       informacion.setIncidente(String.valueOf(incidentes.getSelectedItemId() + 1));
                                       informacion.setFechaActualizacion(new Date().toString());
                                       informacion.setFechaCreacion(new Date().toString());
                                       informacion.setLatitud(new Double(latitud.toString()));
                                       informacion.setLongitud(new Double(longitud.toString()));
                                       informacion.setIdMercado(idMercado.toString());
                                       serviceDatabase.addInformacion(informacion);
                                   }

                                   LoadAssync load = new LoadAssync(informacion);
                                   load.execute();



                               } catch ( Exception e ){
                                   e.printStackTrace();
                                   Toast.makeText(FichaActivity.this,
                                           "Error al enviar la informacion " + e.getMessage(),
                                           Toast.LENGTH_SHORT).show();
                               }


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();







            }

        });


    }

    private class LoadAssync extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;
        double longitude;
        double latitude;
        private Informacion info;

        public LoadAssync(Informacion info1){
            info = info1;
        }


        protected void onPreExecute() {

            dialog = new ProgressDialog(FichaActivity.this);
            dialog.setMessage("Enviando Informacion...");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected Void doInBackground(final String... args) {
            // you can do the code here
            try {
                Thread.sleep(1000);

                //FTPClient ftpClient = new FTPClient();
                try {

                    /*
                    File folder = new File(Constant.PATH_MERCADOS_ARCHIVOS + File.separator);
                    listOfFiles = folder.listFiles();

                    if (listOfFiles != null) {
                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                ftpClient.connect(server, port);
                                ftpClient.login(user, pass);
                                // ftpClient.enterLocalPassiveMode();

                                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                                String firstRemoteFile = "MERCADOS\\" + file.getName();
                                InputStream inputStream = new FileInputStream(file);

                                boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
                                inputStream.close();







                            }
                        }
                    }

                    ftpClient.logout();
                    ftpClient.disconnect();

                    */

                    HttpClient client = new HttpClient();
                    PostMethod httpPost = new PostMethod(URL);

                    StringBuilder json = new StringBuilder();
                    System.out.println(info);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String date = sdf.format(new Date());
                    System.out.println(date); //15/10/2013

                    sdf = new SimpleDateFormat("hh:mm");
                    String hora = sdf.format(new Date());
                    System.out.println(hora); //15/10/2013

                    json.append("{" +
                            "  \"codMercado\": \""+ info.getIdMercado() +"\",\n" +
                            "  \"codAbast\": \""+info.getNivel()+"\",\n" +
                            "  \"codAforo\": \""+info.getAforo()+"\",\n" +
                            "  \"codIncid\": \""+info.getIncidente()+"\",\n" +
                            "  \"fecha\": \""+date+"\",\n" +
                            "  \"Hora\": \""+hora+"\",\n" +
                            "  \"claveWS\":\"Mercado@Seguro@2020\"\n" +
                            "}");

                    StringRequestEntity requestEntity = new StringRequestEntity(
                            json.toString(),
                            "application/json",
                            "UTF-8");

                    httpPost.setRequestEntity(requestEntity);

                    int statusCode = client.executeMethod(httpPost);
                    String response = httpPost.getResponseBodyAsString();
                    JSONObject jsonObj = new JSONObject(response);
                    String code = jsonObj.getString("estado");
                    final String mensaje = jsonObj.getString("mensaje");
                    if (statusCode == HttpStatus.SC_OK && code.equals("OK")){
                        runOnUiThread(new Runnable() {
                            public void run() {
                        Toast.makeText(FichaActivity.this,
                                "Se Envio La informacion Correctamente",
                                Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                        Toast.makeText(FichaActivity.this,
                                mensaje,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                    }




                } catch (Exception e){
                    e.printStackTrace();
                    //
                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(getApplicationContext(),"Error enviando la informacion ",Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;


        }



        protected void onPostExecute(final Void unused) {




            if (dialog.isShowing()) {
                dialog.dismiss();
            }



        }
    }
    private void createLabel(WritableSheet sheet, int column, int row, String value)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);


        addCaption(sheet, column, row,value );

        //addCaption(sheet, 1, 0, "This is another header");


    }

    private void createContent(WritableSheet sheet) throws WriteException,
            RowsExceededException {
        // Write a few number
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10);
            // Second column
            addNumber(sheet, 1, i, i * i);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

       /*
        // now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i);
            // Second column
            addLabel(sheet, 1, i, "Another text");
        }*/
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
                           Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

    public void addNiveles() {

        List<Combo> list = new ArrayList<>();

        list.add(new Combo("1","Alto"));
        list.add(new Combo("2","Normal"));
        list.add(new Combo("3","Bajo"));


        ArrayAdapter<Combo> dataAdapter = new ArrayAdapter<Combo>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        niveles.setAdapter(dataAdapter);
    }

    public void addAforos() {

        List<Combo> list = new ArrayList<>();

        list.add(new Combo("1","Alto"));
        list.add(new Combo("2","Normal"));
        list.add(new Combo("3","Bajo"));


        ArrayAdapter<Combo> dataAdapter = new ArrayAdapter<Combo>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aforos.setAdapter(dataAdapter);
    }

    public void addIncidentes() {

        List<Combo> list = new ArrayList<>();
        list.add(new Combo("0","Ninguna"));
        list.add(new Combo("1","Robo"));
        list.add(new Combo("2","Asalto"));
        list.add(new Combo("3","Intento de Saqueo"));
        list.add(new Combo("4","Saqueo"));
        list.add(new Combo("5","Cierre Parcial"));
        list.add(new Combo("6","Cierre Total"));

        ArrayAdapter<Combo> dataAdapter = new ArrayAdapter<Combo>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incidentes.setAdapter(dataAdapter);
    }
}
