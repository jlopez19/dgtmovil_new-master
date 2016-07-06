package general.me.edu.dgtmovil.dgtmovil;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.objetos.ReporteEstudiantes;
import general.me.edu.dgtmovil.objetos.RespuestaReporte;


public class ReportesActivity extends Activity {
    String grados[] = new String[]{"Primero", "Segundo", "Tercero", "Cuarto", "Quinto"};
    Spinner spGrados;
    Button btnReportar;
    ImageView imgPrueba;
    ArrayAdapter<String> adaptador;
    Toolbar toolbar;
    Bundle savedInstanceState1;

    //BASE DE DATOS
    RespuestaReporte resRepo[] = null;

    //PDF
    private static final String NOMBRE_CARPETA_APP = "general.me.edu.dgtmovil";
    private static final String GENERADOS = "dgtArchivos";


    Persistencia mypersistencia;
    GestionDatos gestionDatos;
    Sentencias sentencias;
    String seleccion = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        savedInstanceState1 = savedInstanceState;
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("CGT - REPORTES ESTUDIANTES POR CURSO");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        spGrados = (Spinner) findViewById(R.id.sp_cursos);
        btnReportar = (Button) findViewById(R.id.btn_reportar);
        adaptador = new ArrayAdapter<String>(ReportesActivity.this, R.layout.spinner_item, grados);
        spGrados.setAdapter(adaptador);
        spGrados.setOnItemSelectedListener(seleccionarCurso);

        gestionDatos = new GestionDatos(getApplicationContext());
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;

        resRepo = gestionDatos.listarRespuestas();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void crearBaseDatos() {
        sentencias = new Sentencias(this, "DBDGT", null, 2);

    }

    AdapterView.OnItemSelectedListener seleccionarCurso = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            seleccion = grados[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void generarPDFOnClic(View view) {
        Document document = new Document(PageSize.LETTER);
        String NOMBRE_ARCHIVO = "Estudiantes.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);

        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }
        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
        if (!pdfSubDir.exists()) {
            pdfSubDir.mkdir();
        }

        String nombre_completo = Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_APP +
                File.separator + GENERADOS + File.separator + NOMBRE_ARCHIVO;
        File outputfile = new File(nombre_completo);
        if (!outputfile.exists()) {
            outputfile.delete();
        }
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));
            //Crear el documento para escribirlo
            document.open();
            document.addAuthor("REPORTE DE ESTUDIANTES");
            document.addCreator("DGT MOVIL");
            document.addSubject("GENERACION DE REPORTES EN PDF");
            document.addCreationDate();
            document.addTitle("ESTUDIANTES POR CURSO");

            StringBuffer aux = new StringBuffer();

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            String htmlToPDF = "<html><head></head><body><h1 ALIGN=center >ESTUDIANTES INSCRITOS</h1><p>Estos son los estudiantes inscritos</p></body></html>";
            aux.append("<html><head></head><body><h1>ESTUDIANTES INSCRITOS</h1><p>Estos son los estudiantes inscritos en " + seleccion + "</p>");
            //aux.append("<BR>");
            int cont = 0;
            Toast.makeText(ReportesActivity.this, "Tengo: " + resRepo.length, Toast.LENGTH_LONG).show();
            ArrayList<ReporteEstudiantes> res = formatearRespuestas();
            aux.append("<table>");
            aux.append("<tr>\n" +
                    "  <td><strong>Documento</strong></td>\n" +
                    "  <td><strong>Nombres</strong></td>\n" +
                    "  <td><strong>Apellidos</strong></td>\n" +
                    "  <td><strong>Curso</strong></td>\n" +
                    "  <td><strong>Firma</strong></td>\n" +
                    "</tr>");

            for (int i = 0; i < res.size(); i++) {
                if (seleccion.equals("Primero") && res.get(i).getCurso().equals("1")) {
                    String firma = (res.get(i).getFirma());
                    String imagenGuardada="";
                    Bitmap imagen;
                    if(firma.equals("null")) {
                         imagen  = BitmapFactory.decodeResource(getResources(), R.drawable.btn_guardar);

                      // imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                       // imgPrueba.setImageBitmap(imagen);

                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Primero" + "</td>\n" +
                                "  <td>SIN FIRMA</td>\n" +
                                "</tr>");


                    }
                    else{
                        imagen = decodificarImagen(firma);
                        imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Primero" + "</td>\n" +
                                "  <td><img src=\"" +imagenGuardada + "\" width=\"100\" height=\"100\"/></td>\n" +
                                "</tr>");
                    }

                } else if (seleccion.equals("Segundo") && res.get(i).getCurso().equals("2")) {
                    String firma = (res.get(i).getFirma());
                    String imagenGuardada="";
                    Bitmap imagen;
                    if(firma.equals("null")) {
                        imagen  = BitmapFactory.decodeResource(getResources(), R.drawable.btn_guardar);

                        // imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        // imgPrueba.setImageBitmap(imagen);

                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Segundo" + "</td>\n" +
                                "  <td>SIN FIRMA</td>\n" +
                                "</tr>");


                    }
                    else{

                        imagen= BitmapFactory.decodeResource(getResources(), R.drawable.btn_cargar_preguntas);
                        imagen = decodificarImagen(firma);
                        imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        imgPrueba.setImageBitmap(imagen);
                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Segundo" + "</td>\n" +
                                "  <td><img src=\"" +imagenGuardada + "\" width=\"100\" height=\"100\"/></td>\n" +
                                "</tr>");
                    }
                } else if (seleccion.equals("Tercero") && res.get(i).getCurso().equals("3")) {
                    String firma = (res.get(i).getFirma());
                    String imagenGuardada="";
                    Bitmap imagen;
                    if(firma.equals("null")) {
                        imagen  = BitmapFactory.decodeResource(getResources(), R.drawable.btn_guardar);

                        // imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        // imgPrueba.setImageBitmap(imagen);

                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Tercero" + "</td>\n" +
                                "  <td>SIN FIRMA</td>\n" +
                                "</tr>");


                    }
                    else{

                        imagen= BitmapFactory.decodeResource(getResources(), R.drawable.btn_cargar_preguntas);
                        imagen = decodificarImagen(firma);
                        imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        imgPrueba.setImageBitmap(imagen);
                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Tercero" + "</td>\n" +
                                "  <td><img src=\"" +imagenGuardada + "\" width=\"100\" height=\"100\"/></td>\n" +
                                "</tr>");
                    }
                } else if (seleccion.equals("Cuarto") && res.get(i).getCurso().equals("4")) {
                    String firma = (res.get(i).getFirma());
                    String imagenGuardada="";
                    Bitmap imagen;
                    if(firma.equals("null")) {
                        imagen  = BitmapFactory.decodeResource(getResources(), R.drawable.btn_guardar);

                        // imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        // imgPrueba.setImageBitmap(imagen);

                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Cuarto" + "</td>\n" +
                                "  <td>SIN FIRMA</td>\n" +
                                "</tr>");


                    }
                    else{

                        imagen= BitmapFactory.decodeResource(getResources(), R.drawable.btn_cargar_preguntas);
                        imagen = decodificarImagen(firma);
                        imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        imgPrueba.setImageBitmap(imagen);
                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Cuarto" + "</td>\n" +
                                "  <td><img src=\"" +imagenGuardada + "\" width=\"100\" height=\"100\"/></td>\n" +
                                "</tr>");
                    }
                } else if (seleccion.equals("Quinto") && res.get(i).getCurso().equals("5")) {
                    String firma = (res.get(i).getFirma());
                    String imagenGuardada="";
                    Bitmap imagen;
                    if(firma.equals("null")) {
                        imagen  = BitmapFactory.decodeResource(getResources(), R.drawable.btn_guardar);

                        // imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        // imgPrueba.setImageBitmap(imagen);

                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Quinto" + "</td>\n" +
                                "  <td>SIN FIRMA</td>\n" +
                                "</tr>");


                    }
                    else{

                        imagen= BitmapFactory.decodeResource(getResources(), R.drawable.btn_cargar_preguntas);
                        imagen = decodificarImagen(firma);
                        imagenGuardada = guardarImagen(ReportesActivity.this, res.get(i).getDocumento(),imagen);
                        imgPrueba.setImageBitmap(imagen);
                        aux.append("<tr>\n" +
                                "  <td>" + res.get(i).getDocumento() + "</td>\n" +
                                "  <td>" + res.get(i).getNombres() + "</td>\n" +
                                "  <td>" + res.get(i).getApellidos() + "</td>\n" +
                                "  <td>" + "Quinto" + "</td>\n" +
                                "  <td><img src=\"" +imagenGuardada + "\" width=\"100\" height=\"100\"/></td>\n" +
                                "</tr>");
                    }
                }
            }
            aux.append("</table>");
            aux.append("<br></br>");
            aux.append("<br></br>");
            aux.append("<br></br>");
            aux.append("<br></br>");
            aux.append("<br></br>");

            aux.append("<p>DGT@2016 - Todos los Derechos Reservados</p>");
            aux.append("</body></html>");


            worker.parseXHtml(pdfWriter, document, new StringReader(aux.toString()));
            document.close();
            Toast.makeText(this, "REPORTE GENERADO", Toast.LENGTH_SHORT).show();
            muestraPDF(nombre_completo, this);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String guardarImagen (Context context, String nombre, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre + ".png");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

    public ArrayList<ReporteEstudiantes> formatearRespuestas() {

        ArrayList<ReporteEstudiantes> datos = new ArrayList<ReporteEstudiantes>();
        String[] formularios = gestionDatos.listarFormulariosRespuestaPorFormulario("98");
        ReporteEstudiantes estuTemp = new ReporteEstudiantes();

        for (int i = 0; i < formularios.length; i++) {
            estuTemp.setApellidos(gestionDatos.listarDatoTextoRespuestaPorPregunta(formularios[i], "684"));
            estuTemp.setNombres(gestionDatos.listarDatoTextoRespuestaPorPregunta(formularios[i], "683"));
            estuTemp.setDocumento(gestionDatos.listarDatoTextoRespuestaPorPregunta(formularios[i], "686"));
            estuTemp.setCurso(gestionDatos.listarDatoTextoRespuestaPorPregunta(formularios[i], "690"));
            estuTemp.setFirma(gestionDatos.listarDatoBinarioRespuestaPorPregunta(formularios[i], "696"));
            datos.add(estuTemp);
            estuTemp = new ReporteEstudiantes();
        }

        return datos;
    }

    public Bitmap decodificarImagen(String imagen) {
   //     byte[] decodedString = Base64.decode(imagen, Base64.URL_SAFE);
   //     Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
   //     return decodedByte;

        final String pureBase64Encoded = imagen.substring(imagen.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, android.util.Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        Log.v("Ben", "Image Converted");
        return decodedBitmap;
    }

    /*
    public Bitmap ConvertToImage(String image){
    try{
        InputStream stream = new ByteArrayInputStream(image.getBytes());
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        Log.v("Ben", "Image Converted");
        return bitmap;
    }
    catch (Exception e) {
        return null;
    }
}
     */

    public void muestraPDF(String archivo, Context context) {
        Toast.makeText(context, "Leyendo el archivo", Toast.LENGTH_SHORT).show();
        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No tiene una app para abrir este tipo de archivos", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Reportes Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
