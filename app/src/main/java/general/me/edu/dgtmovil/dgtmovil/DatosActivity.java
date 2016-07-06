package general.me.edu.dgtmovil.dgtmovil;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.microblink.activity.Pdf417ScanActivity;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkbarcode.BarcodeType;
import com.microblink.recognizers.blinkbarcode.bardecoder.BarDecoderRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.bardecoder.BarDecoderScanResult;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417RecognizerSettings;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417ScanResult;
import com.microblink.recognizers.blinkbarcode.usdl.USDLRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.usdl.USDLScanResult;
import com.microblink.recognizers.blinkbarcode.zxing.ZXingRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.zxing.ZXingScanResult;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.results.barcode.BarcodeDetailedData;
import com.microblink.results.barcode.BarcodeElement;
import com.microblink.util.Log;
import com.microblink.view.recognition.RecognizerView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import general.me.edu.dgtmovil.DialogFragmentDescrip;
import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;
import general.me.edu.dgtmovil.objetos.Respuesta;

import android.view.View.OnClickListener;
public class DatosActivity extends AppCompatActivity implements OnClickListener, DatePickerDialog.OnDateSetListener{

    public static final int SIGNATURE_ACTIVITY = 1;
    Pdf417ScanResult result;
    EditText p1, p2, p4,p6,p10,p11, et_longitud;
    Spinner p3,p7,p8,p9;
    TextView lp1,lp2 ,lp3,lp4 ,lp5,lp6 ,lp7,lp8 ,lp9,lp10 ,lp11, tv_longitud;
    String []mostrar;
    LectorCedulasDeco deco;
    ImageView firma;
    Button bt_guardar;
    String codigoFormulario;


    //CAMBIOS PARA LA FOTO
    final static int CONS = 1;
    ImageView foto;
    ImageView btnFoto;
    Bitmap bmpFoto;
    String datoBdFoto;
    String datoBdFirma;

  // demo license key for package com.microblink.barcode
  // obtain your licence key at http://microblink.com/login or
  // contact us at http://help.microblink.com
  private static final String LICENSE_KEY = "G5P47G2H-P5EAQC7A-DSQIOY3O-NDL4A3TI-27AG42GX-YBXGRV6A-NZUNPQDO-NDLZCCAJ";

    private static final int MY_REQUEST_CODE = 1337;

    private static final String TAG = "Pdf417MobiDemo";

    Persistencia mypersistencia;
    GestionDatos gestionDatos;
    Sentencias sentencias;
    String idUsuario="";
    String idPerfil="";
    String idEntidad="";
    String idFormulario="";
    Pregunta[] preguntas=null;
    Opcion[] opciones;
    String fecha,hora;
    String lon,lat;
    Bundle savedInstanceState1;
   Toolbar toolbar;
    ImageView addDescUno, addDesDos, addDesTres, addDescuatro, addDesCinco, addDesSeis, addDesSiete, addDesOcho, addDesNueve, addDesDiez;
    TextView obli_uno, obli_dos, obli_tres, obli_cuatro;
    //VARIABLES PRUEBA CODIGO OPCION
    ArrayList<String> codigosOpciones = new ArrayList<String>();
    int idTemp;
    private int year;
    private int month;
    private int day;
    Button btn_fecha;
    int hour;
    int dateType;
    public static final int DATE_INIT =998;
    DatePickerDialog dialog;
    public static TextView p5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
       // TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
        //tvVersion.setText(buildVersionString());
        savedInstanceState1=savedInstanceState;
        deco = new LectorCedulasDeco();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        firma=(ImageView) findViewById(R.id.img_firma);
        obli_uno=(TextView) findViewById(R.id.p3_obligatorio);
        obli_dos=(TextView) findViewById(R.id.p7_obligatorio);
        obli_tres=(TextView) findViewById(R.id.p8_obligatorio);
        obli_cuatro=(TextView) findViewById(R.id.p9_obligatorio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT - Datos Registro");

        addDescUno=(ImageView) findViewById(R.id.btn_addComNombre);
        addDescUno.setOnClickListener(this);
        addDesDos=(ImageView)findViewById(R.id.btn_addComApellido);
        addDesDos.setOnClickListener(this);
        addDesTres=(ImageView)findViewById(R.id.btn_addComDocumento);
        addDesTres.setOnClickListener(this);
        addDescuatro=(ImageView)findViewById(R.id.btn_addComFecha);
        addDescuatro.setOnClickListener(this);
        addDesCinco=(ImageView)findViewById(R.id.btn_addComCoord);
        addDesCinco.setOnClickListener(this);
        addDesSeis=(ImageView)findViewById(R.id.btn_addComEstudio);
        addDesSeis.setOnClickListener(this);
        addDesSiete=(ImageView)findViewById(R.id.btn_addComOpina);
        addDesSiete.setOnClickListener(this);
        addDesOcho=(ImageView)findViewById(R.id.btn_addComCole);
        addDesOcho.setOnClickListener(this);
        addDesNueve=(ImageView)findViewById(R.id.btn_addComEmail);
        addDesNueve.setOnClickListener(this);
        addDesDiez=(ImageView)findViewById(R.id.btn_addComTrama);
        addDesDiez.setOnClickListener(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        p1 = (EditText) findViewById(R.id.p1);
        p2 = (EditText) findViewById(R.id.p2);
        p4 = (EditText) findViewById(R.id.p4);
        p5 = (TextView) findViewById(R.id.p5);
        p6 = (EditText) findViewById(R.id.p6);
        p10 = (EditText) findViewById(R.id.p10);
        p11 = (EditText) findViewById(R.id.p11);
        et_longitud = (EditText) findViewById(R.id.txt_longitud);
        p3=  (Spinner) findViewById(R.id.p3);
        p7=  (Spinner) findViewById(R.id.p7);
        p8=  (Spinner) findViewById(R.id.p8);
        p9=  (Spinner) findViewById(R.id.p9);

        lp1=  (TextView) findViewById(R.id.lp1);
        lp2=  (TextView) findViewById(R.id.lp2);
        lp3=  (TextView) findViewById(R.id.lp3);
        lp4=  (TextView) findViewById(R.id.lp4);
        lp5=  (TextView) findViewById(R.id.lp5);
        lp6=  (TextView) findViewById(R.id.lp6);
        lp7=  (TextView) findViewById(R.id.lp7);
        lp8=  (TextView) findViewById(R.id.lp8);
        lp9=  (TextView) findViewById(R.id.lp9);
        lp10=  (TextView) findViewById(R.id.lp10);
        lp11=  (TextView) findViewById(R.id.lp11);
        tv_longitud=  (TextView) findViewById(R.id.l_longitud);

        bt_guardar=  (Button) findViewById(R.id.buttonGuardar);
        btn_fecha=(Button)findViewById(R.id.btn_fecha);
        btn_fecha.setOnClickListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar

        //CODIGO PARA LA FOTO
        foto = (ImageView) findViewById(R.id.imgfoto);
        btnFoto = (ImageView) findViewById(R.id.btn_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(goCamara, CONS);
            }
        });


        idUsuario=bundle.getString("idUsuario");
        idPerfil=bundle.getString("perfil");
        idEntidad=bundle.getString("idEntidad");
        idFormulario=bundle.getString("idFormulario");
        gestionDatos=new GestionDatos(getApplicationContext());
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;

        firma.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("img", "imgSola");
                Intent intent = new Intent(DatosActivity.this, CaptureSignature.class);
                intent.putExtras(b);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
            }
        });
        preguntas=gestionDatos.listarPreguntas(idFormulario);
        opciones=gestionDatos.listarOpciones(idEntidad);

        if(preguntas!=null && preguntas.length>0) {
            Pregunta pre = getPregunta("1");
            lp1.setText(pre.DESCRIPCION);
            pre = getPregunta("2");
            lp2.setText(pre.DESCRIPCION);
            pre = getPregunta("3");
            lp3.setText(pre.DESCRIPCION);
            pre = getPregunta("4");
            lp4.setText(pre.DESCRIPCION);
            pre = getPregunta("5");
            lp5.setText(pre.DESCRIPCION);
            pre = getPregunta("6");
            lp6.setText(pre.DESCRIPCION);
            pre = getPregunta("7");
            lp7.setText(pre.DESCRIPCION);
            pre = getPregunta("8");
            lp8.setText(pre.DESCRIPCION);
            pre = getPregunta("9");
            lp9.setText(pre.DESCRIPCION);
            pre = getPregunta("10");
            lp10.setText(pre.DESCRIPCION);
            pre = getPregunta("11");
            lp11.setText(pre.DESCRIPCION);


            fijarOpciones("3",p3);
            fijarOpciones("7",p7);
            fijarOpciones("8",p8);
            fijarOpciones("9",p9);

            p6.setText(MenuPrincipal.latitud);
            et_longitud.setText(MenuPrincipal.longitud);
        }

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFormulario();
            }
        });

        }

    public void fijarFechaHora(){
        Calendar cal = new GregorianCalendar();
        Date dt = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat dh = new SimpleDateFormat("HH:mm");
        String formatteDate = df.format(dt.getTime());
        String formatteHora = dh.format(dt.getTime());
        fecha=formatteDate;
        hora=formatteHora;
    }


    public String getValorCombo(Spinner dat){
        if(dat!=null && dat.getSelectedItem()!=null && dat.getSelectedItem().toString().indexOf(".")!=-1){
            return dat.getSelectedItem().toString().substring(0,dat.getSelectedItem().toString().indexOf("."));
        }
        else{
            return "0";
        }
    }

    public void obtenerCodigoFormulario() {
        String result = "";
        Calendar cal = Calendar.getInstance();
        Date datefecha = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        result = formatter.format(datefecha);
        codigoFormulario=idUsuario+result+gestionDatos.getIdMaxFormularioRespuesta();

    }

    public boolean guardarFormulario(){
        fijarFechaHora();
        obtenerCodigoFormulario();
        ArrayList<Respuesta> listaRespuestas=new ArrayList<Respuesta>();



        FormularioRespuesta formulario=new FormularioRespuesta();
        formulario.FECHA=fecha;
        formulario.OBSERVACIONES="";
        formulario.ID_FORMULARIO=idFormulario;
        formulario.LATITUD=MenuPrincipal.latitud;
        formulario.LONGITUD=MenuPrincipal.longitud;
        formulario.ID_USUARIO=idUsuario+"";
        formulario.CODIGO=codigoFormulario;

        Respuesta re=null;
        Pregunta pre = null;

        re=new Respuesta();
        pre = getPregunta("1");
        String preguntaUno=pre.OBLIGATORIA;
        String vacioUno=p1.getText().toString();
        if(TextUtils.isEmpty(vacioUno) && preguntaUno.equals("SI")) {
            p1.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p1.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("2");
        String preguntaDos=pre.OBLIGATORIA;
        String vacioDos=p2.getText().toString();
        if(TextUtils.isEmpty(vacioDos) && preguntaDos.equals("SI")) {
            p2.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p2.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("3");
        String preguntaTres=pre.OBLIGATORIA;
        int vacioTres=p3.getSelectedItemPosition()-1;
        if(vacioTres==-1 && preguntaTres.equals("SI")) {
            obli_uno.setText("*");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(0, p3.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p3);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("4");
        String preguntaCuatro=pre.OBLIGATORIA;
        String vacioCuatro=p4.getText().toString();
        if(TextUtils.isEmpty(vacioCuatro) && preguntaCuatro.equals("SI")) {
            p4.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p4.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("5");
        String preguntaCinco=pre.OBLIGATORIA;
        String vacioCinco=p5.getText().toString();
        if(TextUtils.isEmpty(vacioCinco) && preguntaCinco.equals("SI")) {
            p5.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p5.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("6");
        String preguntaSeis=pre.OBLIGATORIA;
        String vacioSeis=p6.getText().toString();
        if(TextUtils.isEmpty(vacioSeis) && preguntaSeis.equals("SI")) {
            p6.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p6.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("7");
        String preguntaSiete=pre.OBLIGATORIA;
        int vacioSiete=p7.getSelectedItemPosition()-1;
        if(vacioSiete==-1 && preguntaSiete.equals("SI")) {
            obli_dos.setText("*");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(1, p7.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p7);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("8");
        String preguntaOcho=pre.OBLIGATORIA;
        int vacioOcho=p8.getSelectedItemPosition()-1;
        if(vacioOcho==-1 && preguntaOcho.equals("SI")) {
            obli_tres.setText("*");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(2, p8.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p8);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("9");
        String preguntaNueve=pre.OBLIGATORIA;
        int vacioNueve=p9.getSelectedItemPosition()-1;
        if(vacioNueve==-1 && preguntaNueve.equals("SI")) {
            obli_cuatro.setText("*");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(3, p9.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p9);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("10");
        String preguntaDiez=pre.OBLIGATORIA;
        String vacioDiez=p10.getText().toString();
        if(TextUtils.isEmpty(vacioDiez) && preguntaDiez.equals("SI")) {
            p10.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p10.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("11");
        String preguntaOnce=pre.OBLIGATORIA;
        String vacioOnce=p11.getText().toString();
        if(TextUtils.isEmpty(vacioOnce) && preguntaOnce.equals("SI")) {
            p11.setError("campo obligatorio");
        }
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p11.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("12");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFoto;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("14");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirma;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);


        if(validarFormulario(formulario)){
            try {
                if(!gestionDatos.existeFormularioRespuesta(formulario)){
                    gestionDatos.crearFormularioRespuesta(formulario);
                    mostrarMensaje("SE CREO", 3);
                }
                else{
                    mostrarMensaje("SE MODIFICO", 3);
                    gestionDatos.modificarFomularioRespuesta(formulario);
                }

                for(int i=0;i<listaRespuestas.size();i++){
                    Respuesta res=listaRespuestas.get(i);
                    gestionDatos.crearRespuesta(res);
                }


                mostrarMensaje("Datos Guardados", 3);
                mypersistencia = new Persistencia(getApplicationContext(),sentencias);

                mypersistencia.execute("11");

                onCreate(savedInstanceState1);

            }

            catch(Exception ex){
                mostrarMensaje(ex.getMessage(), 3);
            }

            return true;

        }
        else{
            return false;
        }
    }

    public void mostrarMensaje(String mensaje,int tiempo){
        Toast toast4 = Toast.makeText(getApplicationContext(),
                mensaje, tiempo);

        toast4.show();
    }

    public void crearBaseDatos() {
        sentencias = new Sentencias(this, "DBDGT", null, 2);

    }

    public boolean validarFormulario(FormularioRespuesta formulario){
        boolean result = true;
        String mensaje = "";

        if(esVacio(formulario.CODIGO)){
            mensaje += " Codigo Formulario es Obligatorio ";
            result = false;
        }



        if(!mensaje.equals(""))
            mostrarMensaje(mensaje, 3);
        return result;
    }




    public boolean esVacio(String dato){
        try{
            if(dato==null || dato.equals(""))
                return true;
        }
        catch(Exception e){
            return false;
        }
        return false;
    }

    public  boolean esFecha(String fecha)
    {

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date fna=null;
        boolean  respuesta=false;
        if(fecha.indexOf(" ")!=-1){
            fecha=fecha.substring(0,fecha.indexOf(" "));
        }
        try{
            df.setLenient(false);
            fna =df.parse(fecha);
            respuesta= true;
        }
        catch(ParseException e){
            e.printStackTrace();
            respuesta= false;
        }
        return respuesta;

    }

    public void fijarOpciones(String orden,Spinner pre){
        Object[] list=null;
        ArrayAdapter<Object> adapterOp;
        Pregunta pregunta = getPregunta(orden);
        list=getOpciones(pregunta.ID_PREGUNTA);
        if(list!=null && list.length>0){
            adapterOp = new ArrayAdapter<Object>(this,
                    R.layout.spinner_item, list);

            pre.setAdapter(adapterOp);
        }

    }

    public Object[] getOpciones(String codPregunta){
        ArrayList<String> lis=new ArrayList<String>();
        String opcionesCodigos = "";
        lis.add("0....");

        for(int i=0;i<opciones.length;i++){
            Opcion op=opciones[i];

            if(op.COD_PREGUNTA.equals(codPregunta)){
                lis.add(op.CODIGO+". "+op.RESPUESTA);
                idTemp= Integer.parseInt(op.ID);
                opcionesCodigos = opcionesCodigos + op.ID + ";";
            }

           // opcionesCodigos = "";
        }
        codigosOpciones.add(opcionesCodigos);
        return lis.toArray();

    }

    public int getCodigoOpcion(int spiner, int posicion){
        int retorna = 0;
        if (posicion!=-1) {
            String opcionesTemp = codigosOpciones.get(spiner);
            opcionesTemp = opcionesTemp.substring(0, opcionesTemp.length() - 1);
            String opc[] = opcionesTemp.split(";");
            retorna = Integer.parseInt(opc[posicion]);
            //retorna = Integer.parseInt(codigosOpciones.get(posicion).toString());
        }
        return retorna;
    }

    public Pregunta getPregunta(String codPregunta){
        Pregunta pre=null;
        for(int i=0;i<preguntas.length;i++){
            pre=preguntas[i];
            if(pre.ORDEN.equals(codPregunta)){
                return pre;
            }
        }
        return new Pregunta();

    }

    /**
     * Builds string which contains information about application version and library version.
     * @return String which contains information about application version and library version.
     */
    private String buildVersionString() {
        String nativeVersionString = RecognizerView.getNativeLibraryVersionString();
        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String appVersion = pInfo.versionName;
            int appVersionCode = pInfo.versionCode;

            StringBuilder infoStr = new StringBuilder();
            infoStr.append("Application version: ");
            infoStr.append(appVersion);
            infoStr.append(", build ");
            infoStr.append(appVersionCode);
            infoStr.append("\nLibrary version: ");
            infoStr.append(nativeVersionString);
            return infoStr.toString();
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public void btnScan_click(View v) {
        Log.i(TAG, "scan will be performed");
        // Intent for ScanActivity
        Intent intent = new Intent(this, Pdf417ScanActivity.class);

        // If you want sound to be played after the scanning process ends,
        // put here the resource ID of your sound file. (optional)
        intent.putExtra(Pdf417ScanActivity.EXTRAS_BEEP_RESOURCE, R.raw.beep);
/** Set the license key */

        // In order for scanning to work, you must enter a valid licence key. Without licence key,
        // scanning will not work. Licence key is bound the the package name of your app, so when
        // obtaining your licence key from Microblink make sure you give us the correct package name
        // of your app. You can obtain your licence key at http://microblink.com/login or contact us
        // at http://help.microblink.com.
        // Licence key also defines which recognizers are enabled and which are not. Since the licence
        // key validation is performed on image processing thread in native code, all enabled recognizers
        // that are disallowed by licence key will be turned off without any error and information
        // about turning them off will be logged to ADB logcat.
        intent.putExtra(Pdf417ScanActivity.EXTRAS_LICENSE_KEY, LICENSE_KEY);
        // If you want to open front facing camera, uncomment the following line.
        // Note that front facing cameras do not have autofocus support, so it will not
        // be possible to scan denser and smaller codes.
//        intent.putExtra(Pdf417ScanActivity.EXTRAS_CAMERA_TYPE, (Parcelable) CameraType.CAMERA_FRONTFACE);

        // You need to define array of recognizer settings. There are 4 types of recognizers available
        // in PDF417.mobi SDK.
        // Don't enable recognizers and barcode types which you don't actually use because this will
        // significantly decrease the scanning speed.

        // Pdf417RecognizerSettings define the settings for scanning plain PDF417 barcodes.
        Pdf417RecognizerSettings pdf417RecognizerSettings = new Pdf417RecognizerSettings();
        // Set this to true to scan barcodes which don't have quiet zone (white area) around it
        // Use only if necessary because it drastically slows down the recognition process
        pdf417RecognizerSettings.setNullQuietZoneAllowed(true);
        // Set this to true to scan even barcode not compliant with standards
        // For example, malformed PDF417 barcodes which were incorrectly encoded
        // Use only if necessary because it slows down the recognition process
//        pdf417RecognizerSettings.setUncertainScanning(true);

        // BarDecoderRecognizerSettings define settings for scanning 1D barcodes with algorithms
        // implemented by Microblink team.
        BarDecoderRecognizerSettings oneDimensionalRecognizerSettings = new BarDecoderRecognizerSettings();
        // set this to true to enable scanning of Code 39 1D barcodes
        oneDimensionalRecognizerSettings.setScanCode39(true);
        // set this to true to enable scanning of Code 128 1D barcodes
        oneDimensionalRecognizerSettings.setScanCode128(true);
        // set this to true to use heavier algorithms for scanning 1D barcodes
        // those algorithms are slower, but can scan lower resolution barcodes
//        oneDimensionalRecognizerSettings.setTryHarder(true);

        // USDLRecognizerSettings define settings for scanning US Driver's Licence barcodes
        // options available in that settings are similar to those in Pdf417RecognizerSettings
        // if license key does not allow scanning of US Driver's License, this settings will
        // be thrown out from settings array and error will be logged to logcat.
        USDLRecognizerSettings usdlRecognizerSettings = new USDLRecognizerSettings();

        // ZXingRecognizerSettings define settings for scanning barcodes with ZXing library
        // We use modified version of ZXing library to support scanning of barcodes for which
        // we still haven't implemented our own algorithms.
        ZXingRecognizerSettings zXingRecognizerSettings = new ZXingRecognizerSettings();
        // set this to true to enable scanning of QR codes
        zXingRecognizerSettings.setScanQRCode(true);
        zXingRecognizerSettings.setScanITFCode(true);

        // finally, when you have defined settings for each recognizer you want to use,
        // you should put them into array held by global settings object

        RecognitionSettings recognitionSettings = new RecognitionSettings();
        // add settings objects to recognizer settings array
        // Pdf417Recognizer, BarDecoderRecognizer, USDLRecognizer and ZXingRecognizer
        //  will be used in the recognition process
        recognitionSettings.setRecognizerSettingsArray(
                new RecognizerSettings[]{pdf417RecognizerSettings, oneDimensionalRecognizerSettings,
                        usdlRecognizerSettings, zXingRecognizerSettings});

        // additionally, there are generic settings that are used by all recognizers or the
        // whole recognition process

        // by default, this option is true, which means that it is possible to obtain multiple
        // recognition results from the same image.
        // if you want to obtain one result from the first successful recognizer
        // (when first barcode is found, no matter which type) set this option to false
//        recognitionSettings.setAllowMultipleScanResultsOnSingleImage(false);

        // finally send that settings object over intent to scan activity
        // use Pdf417ScanActivity.EXTRAS_RECOGNITION_SETTINGS to set recognizer settings
        intent.putExtra(Pdf417ScanActivity.EXTRAS_RECOGNITION_SETTINGS, recognitionSettings);

        // if you do not want the dialog to be shown when scanning completes, add following extra
        // to intent
//        intent.putExtra(Pdf417ScanActivity.EXTRAS_SHOW_DIALOG_AFTER_SCAN, false);

        // if you want to enable pinch to zoom gesture, add following extra to intent
        intent.putExtra(Pdf417ScanActivity.EXTRAS_ALLOW_PINCH_TO_ZOOM, true);

        // if you want Pdf417ScanActivity to display rectangle where camera is focusing,
        // add following extra to intent
        intent.putExtra(Pdf417ScanActivity.EXTRAS_SHOW_FOCUS_RECTANGLE, true);

        // if you want to use camera fit aspect mode to letterbox the camera preview inside
        // available activity space instead of cropping camera frame (default), add following
        // extra to intent.
        // Camera Fit mode does not look as nice as Camera Fill mode on all devices, especially on
        // devices that have very different aspect ratios of screens and cameras. However, it allows
        // all camera frame pixels to be processed - this is useful when reading very large barcodes.
//        intent.putExtra(Pdf417ScanActivity.EXTRAS_CAMERA_ASPECT_MODE, (Parcelable) CameraAspectMode.ASPECT_FIT);

        // Start Activity
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    /**
     * Checks whether data is URL and in case of URL data creates intent and starts activity.
     * @param data String to check.
     * @return If data is URL returns {@code true}, else returns {@code false}.
     */
    private boolean checkIfDataIsUrlAndCreateIntent(String data) {
        // if barcode contains URL, create intent for browser
        // else, contain intent for message
        boolean barcodeDataIsUrl;
        try {
            @SuppressWarnings("unused")
            URL url = new URL(data);
            barcodeDataIsUrl = true;
        } catch (MalformedURLException exc) {
            barcodeDataIsUrl = false;
        }

        if (barcodeDataIsUrl) {
            // create intent for browser
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(data));
            startActivity(Intent.createChooser(intent, getString(R.string.UseWith)));
        }
        return barcodeDataIsUrl;
    }

    //PARA AGREGAR LA FOTO Y CREAR EL STRING
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }


    public String codificarImagen(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (requestCode == SIGNATURE_ACTIVITY && resultCode == CaptureSignature.RESULT_OK) {

                Bitmap imagenFirma = null;
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("done")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirma = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(DatosActivity.this.openFileInput(datoBdFirma));
                        datoBdFirma = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);
                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                    firma.setImageDrawable(dra);
                    // firma.setBackground(Drawable.createFromPath(String.valueOf(imagenFirma)));
                    Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();

                }
            }}
        catch (Exception e){

        }
        //PARA RECIBIR LA FOTO DE LA CAMARA
      try {
          if (resultCode == RESULT_OK && requestCode == CONS) {
              Bundle ext = data.getExtras();
              bmpFoto = (Bitmap) ext.get("data");
              Drawable dra = new BitmapDrawable(getResources(), bmpFoto);
              btnFoto.setImageDrawable(dra);
              //DATO PARA LA BASE DE DATOS
              datoBdFoto = codificarImagen(bmpFoto);

          }
      }
      catch(Exception e){

      }

        if (requestCode == MY_REQUEST_CODE && resultCode == Pdf417ScanActivity.RESULT_OK) {
            // First, obtain recognition result
            RecognitionResults results = data.getParcelableExtra(Pdf417ScanActivity.EXTRAS_RECOGNITION_RESULTS);
            // Get scan results array. If scan was successful, array will contain at least one element.
            // Multiple element may be in array if multiple scan results from single image were allowed in settings.
            BaseRecognitionResult[] resultArray = results.getRecognitionResults();

            // Each recognition result corresponds to active recognizer. As stated earlier, there are 4 types of
            // recognizers available (PDF417, Bardecoder, ZXing and USDL), so there are 4 types of results
            // available.

            StringBuilder sb = new StringBuilder();


            for(BaseRecognitionResult res : resultArray) {
                if(res instanceof Pdf417ScanResult) { // check if scan result is result of Pdf417 recognizer
                    result = (Pdf417ScanResult) res;
                    // getStringData getter will return the string version of barcode contents
                    String barcodeData = result.getStringData();

                    // isUncertain getter will tell you if scanned barcode contains some uncertainties
                    boolean uncertainData = result.isUncertain();
                    // getRawData getter will return the raw data information object of barcode contents
                    BarcodeDetailedData rawData = result.getRawData();
                    // BarcodeDetailedData contains information about barcode's binary layout, if you
                    // are only interested in raw bytes, you can obtain them with getAllData getter
                    byte[] rawDataBuffer = rawData.getAllData();

                    // if data is URL, open the browser and stop processing result
                    if(checkIfDataIsUrlAndCreateIntent(barcodeData)) {
                        return;
                    } else {
                        // add data to string builder
                        sb.append("PDF417 scan data");

                        if (uncertainData) {
                            sb.append("This scan data is uncertain!\n\n");
                        }
                        sb.append(" string data:\n");
                        sb.append(barcodeData);
                        if (rawData != null) {
                            sb.append("\n\n");
                            sb.append("PDF417 raw data:\n");
                            sb.append(rawData.toString());

                            sb.append("\n");
                            sb.append("PDF417 raw data merged:\n");
                            sb.append("{");
                            for (int i = 0; i < rawDataBuffer.length; ++i) {
                                sb.append((int) rawDataBuffer[i] & 0x0FF);
                                if (i != rawDataBuffer.length - 1) {
                                    sb.append(", ");

                                }
                            }
                            BarcodeElement[] dato = rawData.getElements();
                           // Toast.makeText(DatosActivity.this, "Datos: "+mostrar[5], Toast.LENGTH_LONG).show();
                            String[] datos = deco.decodificarCedula(rawData.toString());
                            for(int i = 0; i < datos.length; i++){
                                if(datos[i] == null){
                                    datos[i] = " ";
                                }
                            }
                            p2.setText(datos[1]+ " " + datos[2]);
                            p1.setText(datos[3]+ " " + datos[4]);
                            p4.setText(datos[0]);
                            p5.setText(datos[5]);
                            sb.append("}\n\n\n");
                        }
                    }
                } else if(res instanceof BarDecoderScanResult) { // check if scan result is result of BarDecoder recognizer
                    BarDecoderScanResult result = (BarDecoderScanResult) res;
                    // with getBarcodeType you can obtain barcode type enum that tells you the type of decoded barcode
                    BarcodeType type = result.getBarcodeType();
                    // as with PDF417, getStringData will return the string contents of barcode
                    String barcodeData = result.getStringData();
                    if(checkIfDataIsUrlAndCreateIntent(barcodeData)) {
                        return;
                    } else {
                        sb.append(type.name());
                        sb.append(" string data:\n");
                        sb.append(barcodeData);
                        sb.append("\n\n\n");
                    }
                } else if(res instanceof ZXingScanResult) { // check if scan result is result of ZXing recognizer
                    ZXingScanResult result= (ZXingScanResult) res;
                    // with getBarcodeType you can obtain barcode type enum that tells you the type of decoded barcode
                    BarcodeType type = result.getBarcodeType();
                    // as with PDF417, getStringData will return the string contents of barcode
                    String barcodeData = result.getStringData();
                    if(checkIfDataIsUrlAndCreateIntent(barcodeData)) {
                        return;
                    } else {
                        sb.append(type.name());
                        sb.append(" string data:\n");
                        sb.append(barcodeData);
                        sb.append("\n\n\n");
                    }
                } else if(res instanceof USDLScanResult) { // check if scan result is result of US Driver's Licence recognizer
                    USDLScanResult result = (USDLScanResult) res;

                    // USDLScanResult can contain lots of information extracted from driver's licence
                    // you can obtain information using the getField method with keys defined in
                    // USDLScanResult class

                    String name = result.getField(USDLScanResult.kCustomerFullName);
                    Log.i(TAG, "Customer full name is " + name);

                    sb.append(result.getTitle());
                    sb.append("\n\n");
                    sb.append(result.toString());
                }
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            startActivity(Intent.createChooser(intent, getString(R.string.UseWith)));
        }
    }

public String obtenerApellido(BarcodeDetailedData rawData){
    String datoEnviar = "";
    mostrar = rawData.toString().split("Element #");

    int codificacion = mostrar.length;
    Toast.makeText(DatosActivity.this, "Cantidad de Elementos #"+codificacion, Toast.LENGTH_SHORT).show();
    if(codificacion < 20){

        String nombre[] = mostrar[5].split("UTF-8 decoded");
        String datoTemp = nombre[1];
        datoEnviar = compararLetras(datoTemp);
    }
    else {
      //  Toast.makeText(DatosActivity.this, "Segunda Opción", Toast.LENGTH_SHORT).show();

        String quitarCedula = mostrar[5];
        String nombre[] = mostrar[7].split("UTF-8 decoded");
        String datoTemp = nombre[1];
        datoEnviar = compararLetras(datoTemp);
        String nombre2[] = mostrar[9].split("UTF-8 decoded");
       // Toast.makeText(DatosActivity.this, "Mostrar 9"+nombre2[1], Toast.LENGTH_SHORT).show();
        datoEnviar = datoEnviar + " "+compararLetras(nombre2[1]);

    }
    return datoEnviar;

}

    public String compararLetras(String datoTemp){
        String datoEnviar = "";
        for (int i = 0; i < datoTemp.length(); i++) {
            Character compara = datoTemp.charAt(i);
            if ((compara >= 'a' && compara <= 'z') || (compara >= 'A' && compara <= 'Z')) {
                datoEnviar = datoEnviar + "" + compara;
            }
        }
        return datoEnviar;
    }
    public String comparaNumeros(String datoTemp){
        String datoEnviar ="";
        for(int i = 0; i < datoTemp.length();i++){
            Character compara = datoTemp.charAt(i);
            if((compara >= '0' && compara <= '9')){
                datoEnviar = datoEnviar+""+compara;
            }
        }
        return datoEnviar;
    }
    public String obtenerNombre(BarcodeDetailedData rawData){
        String datoEnviar = "";
        mostrar = rawData.toString().split("Element #");

        int codificacion = mostrar.length;
      //  Toast.makeText(DatosActivity.this, "Cantidad de Elementos #"+codificacion, Toast.LENGTH_SHORT).show();
        if(codificacion < 20){

            String nombre[] = mostrar[7].split("UTF-8 decoded");
            String datoTemp = nombre[1];
            datoEnviar = compararLetras(datoTemp);
        }
        else {
           // Toast.makeText(DatosActivity.this, "Segunda Opción", Toast.LENGTH_SHORT).show();

            String quitarCedula = mostrar[5];
            String nombre[] = mostrar[11].split("UTF-8 decoded");
            String datoTemp = nombre[1];
            datoEnviar = compararLetras(datoTemp);
            String nombre2[] = mostrar[13].split("UTF-8 decoded");
           // Toast.makeText(DatosActivity.this, "Mostrar 9"+nombre2[1], Toast.LENGTH_SHORT).show();
            datoEnviar = datoEnviar + " "+compararLetras(nombre2[1]);

        }
        return datoEnviar;

    }

    public String obtenerDocumento(BarcodeDetailedData rawData, String barcodeData){
        String datoEnviar = "";
        mostrar = rawData.toString().split("Element #");
        int codificacion = mostrar.length;
        Toast.makeText(DatosActivity.this, "Cantidad de Elementos #"+codificacion, Toast.LENGTH_SHORT).show();
        if(codificacion < 20) {
            String nombre[] = mostrar[5].split("UTF-8 decoded");
            Toast.makeText(DatosActivity.this, "Datos: " + mostrar.length, Toast.LENGTH_LONG).show();
            String datoTemp = nombre[1];
            datoEnviar = comparaNumeros(datoTemp);
            datoEnviar = datoEnviar.substring(datoEnviar.length()-8,datoEnviar.length());
        }
        else{
            String nombre[] = mostrar[7].split("UTF-8 decoded");
            Toast.makeText(DatosActivity.this, "Datos: " + mostrar.length, Toast.LENGTH_LONG).show();
            String datoTemp = nombre[1];
            datoEnviar = comparaNumeros(datoTemp);
            //datoEnviar = datoEnviar.substring(datoEnviar.length()-10,datoEnviar.length());
        }
        return datoEnviar;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fecha) {
            showDatePicker(DATE_INIT, year, month, day);
        }else if (id==R.id.btn_addComNombre){
            cargarDialog();
        }else if (id==R.id.btn_addComApellido){
            cargarDialog();
        }else if (id==R.id.btn_addComDocumento){
            cargarDialog();
        }else if (id==R.id.btn_addComFecha){
            cargarDialog();
        }else if (id==R.id.btn_addComCoord){
            cargarDialog();
        }else if (id==R.id.btn_addComEstudio){
            cargarDialog();
        }else if (id==R.id.btn_addComOpina){
            cargarDialog();
        }else if (id==R.id.btn_addComCole){
            cargarDialog();
        }else if (id==R.id.btn_addComEmail){
            cargarDialog();
        }else if (id==R.id.btn_addComTrama){
            cargarDialog();
        }

    }

    private void cargarDialog() {
        FragmentManager fm = getFragmentManager();
        DialogFragmentDescrip dialogFragment = new DialogFragmentDescrip ();
        dialogFragment.show(fm ,"Descripcion");
    }

    private void showDatePicker(int dateType, int year, int month, int day) {
        this.dateType= dateType;
        final Calendar c = Calendar.getInstance();

        if(year<999) {
            int yearS = c.get(Calendar.YEAR);
            int monthS = c.get(Calendar.MONTH);
            int dayS = c.get(Calendar.DAY_OF_MONTH);
            dialog = new DatePickerDialog(this, this, yearS, monthS, dayS);
        }else {
            dialog = new DatePickerDialog(this, this, year, month, day);
        }
        dialog.getDatePicker().setMaxDate(c.getTime().getTime());
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int yearS, int monthOfYearS, int dayOfMonthS) {
        if (dateType == DATE_INIT) {
            year = yearS;
            month = monthOfYearS;
            day = dayOfMonthS;
            p5.setText(pad(month+1) + "/" + pad(day) + "/" +pad(year));
        }
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
