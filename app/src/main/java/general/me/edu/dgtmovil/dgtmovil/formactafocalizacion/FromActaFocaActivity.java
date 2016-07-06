package general.me.edu.dgtmovil.dgtmovil.formactafocalizacion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.dgtmovil.MenuPrincipal;
import general.me.edu.dgtmovil.dgtmovil.Persistencia;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;
import general.me.edu.dgtmovil.objetos.Respuesta;

import android.view.View.OnClickListener;
public class FromActaFocaActivity extends AppCompatActivity implements  OnClickListener,  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    public static EditText p7,p8,p9,p10, p11, p12, p13, p14, p15, p16;
    public static Spinner p4,p5,p6,p17;
    TextView lp1,lp2 ,lp3,lp4 ,lp5,lp6 ,lp7,lp8 ,lp9,lp10 ,lp11, lp12, lp13, lp14, lp15, lp16, lp17;
    String []mostrar;
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
    String codigoFormulario;
    Button bt_guardar;
    Toolbar toolbar;
    int dateType;
    public static final int DATE_INIT =998;
    DatePickerDialog dialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public static final int TIME_INIT = 0;
    public static final int TIME_END = 1;

    Button timeI, timeF, btn_fecha;
    int hI, mI, hF, mF;
    int timeType;
    public static TextView p1, p2, p3;

    private int year;
    private int month;
    private int day;

    ArrayList<String> codigosOpciones = new ArrayList<String>();
    int idTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_acta_foca);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT - Acta Focalización");
        p1 = (TextView) findViewById(R.id.p1);
        p2 = (TextView) findViewById(R.id.p2);
        p3 = (TextView) findViewById(R.id.p3);
        p7 = (EditText) findViewById(R.id.p7);
        p8 = (EditText) findViewById(R.id.p8);
        p9 = (EditText) findViewById(R.id.p9);
        p10 = (EditText) findViewById(R.id.p10);
        p11 = (EditText) findViewById(R.id.p11);
        p12 = (EditText) findViewById(R.id.p12);
        p13 = (EditText) findViewById(R.id.p13);
        p14 = (EditText) findViewById(R.id.p14);
        p15 = (EditText) findViewById(R.id.p15);
        p16 = (EditText) findViewById(R.id.p16);
        p4 = (Spinner) findViewById(R.id.p4);
        p5 = (Spinner) findViewById(R.id.p5);
        p6 = (Spinner) findViewById(R.id.p6);
        p17 = (Spinner) findViewById(R.id.p17);

        lp1 = (TextView) findViewById(R.id.lp1);
        lp2 = (TextView) findViewById(R.id.lp2);
        lp3 = (TextView) findViewById(R.id.lp3);
        lp4 = (TextView) findViewById(R.id.lp4);
        lp5 = (TextView) findViewById(R.id.lp5);
        lp6 = (TextView) findViewById(R.id.lp6);
        lp7 = (TextView) findViewById(R.id.lp7);
        lp8 = (TextView) findViewById(R.id.lp8);
        lp9 = (TextView) findViewById(R.id.lp9);
        lp10 = (TextView) findViewById(R.id.lp10);
        lp11 = (TextView) findViewById(R.id.lp11);
        lp12 = (TextView) findViewById(R.id.lp12);
        lp13 = (TextView) findViewById(R.id.lp13);
        lp14 = (TextView) findViewById(R.id.lp14);
        lp15 = (TextView) findViewById(R.id.lp15);
        lp16 = (TextView) findViewById(R.id.lp16);
        lp17 = (TextView) findViewById(R.id.lp17);

        bt_guardar=  (Button) findViewById(R.id.buttonGuardar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = getIntent().getExtras();

        btn_fecha=(Button)findViewById(R.id.btn_fecha);
        timeI=(Button)findViewById(R.id.btn_horaInicial);
        timeF=(Button)findViewById(R.id.btn_horaFinal);

        btn_fecha.setOnClickListener(this);
        timeI.setOnClickListener(this);
        timeF.setOnClickListener(this);

        idUsuario = bundle.getString("idUsuario");
        idPerfil = bundle.getString("perfil");
        idEntidad = bundle.getString("idEntidad");
        idFormulario = bundle.getString("idFormulario");
        gestionDatos = new GestionDatos(this);
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;


        preguntas = gestionDatos.listarPreguntas(idFormulario);
        opciones = gestionDatos.listarOpciones(idEntidad);

        if (preguntas != null && preguntas.length > 0) {
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
            pre = getPregunta("12");
            lp12.setText(pre.DESCRIPCION);
            pre = getPregunta("13");
            lp13.setText(pre.DESCRIPCION);
            pre = getPregunta("14");
            lp14.setText(pre.DESCRIPCION);
            pre = getPregunta("15");
            lp15.setText(pre.DESCRIPCION);
            pre = getPregunta("16");
            lp16.setText(pre.DESCRIPCION);
            pre = getPregunta("17");
            lp17.setText(pre.DESCRIPCION);


            fijarOpciones("4", p4);
            fijarOpciones("5", p5);
            fijarOpciones("6", p6);
            fijarOpciones("17", p17);

            //  p6.setText(MenuPrincipal.latitud+" "+MenuPrincipal.longitud);

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

        ArrayList<Respuesta> listaRespuestas=new ArrayList<Respuesta>();

        obtenerCodigoFormulario();

        FormularioRespuesta formulario=new FormularioRespuesta();
        formulario.FECHA=fecha;
        formulario.OBSERVACIONES="";
        formulario.ID_FORMULARIO=idFormulario;
        formulario.LATITUD= MenuPrincipal.latitud;
        formulario.LONGITUD=MenuPrincipal.longitud;
        formulario.ID_USUARIO=idUsuario+"";
        formulario.CODIGO=codigoFormulario;


        Respuesta re=null;
        Pregunta pre = null;

        re=new Respuesta();
        pre = getPregunta("1");
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
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p3.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("4");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(0, p4.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p4);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("5");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(1, p5.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p5);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("6");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(2, p6.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p6);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("7");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p7.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("8");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p8.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("9");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p9.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("10");
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
        re.DATOTEXTO=p12.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("13");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p13.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("14");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p14.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("15");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p15.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("16");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p16.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);


        re=new Respuesta();
        pre = getPregunta("17");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(3, p17.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p17);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
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
                mypersistencia = new Persistencia(this,sentencias);

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
        Toast toast4 = Toast.makeText(this,
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
            adapterOp = new ArrayAdapter<Object>(this, R.layout.spinner_item, list);

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
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fecha) {
            showDatePicker(DATE_INIT, year, month, day);
        }else if (id == R.id.btn_horaInicial) {
            showTimePicker(TIME_INIT, hI, mI);
        } else if (id == R.id.btn_horaFinal) {
            showTimePicker(TIME_END, hF, mF);
        }

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
            p1.setText(pad(month+1) + "/" + pad(day) + "/" +pad(year));
        }
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void showTimePicker(int timeType, int h, int m) {
        this.timeType = timeType;
        TimePickerDialog dialog;

        if (h < 0) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            dialog = new TimePickerDialog(this, this, hour, minute,
                    DateFormat.is24HourFormat(this));
        } else {
            dialog = new TimePickerDialog(this, this, h, m,
                    DateFormat.is24HourFormat(this));
        }
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (timeType == TIME_INIT) {
            hI = hourOfDay;
            mI = minute;
            p2.setText(pad(hourOfDay) + ":" + pad(minute));
        } else {
            hF = hourOfDay;
            mF = minute;
            p3.setText(pad(hourOfDay) + ":" + pad(minute));
        }
    }

}
