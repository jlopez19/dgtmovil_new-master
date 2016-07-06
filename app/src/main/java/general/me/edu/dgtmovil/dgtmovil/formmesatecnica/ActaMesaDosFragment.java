package general.me.edu.dgtmovil.dgtmovil.formmesatecnica;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
import general.me.edu.dgtmovil.dgtmovil.CaptureSignature;
import general.me.edu.dgtmovil.dgtmovil.DatosActivity;
import general.me.edu.dgtmovil.dgtmovil.MenuPrincipal;
import general.me.edu.dgtmovil.dgtmovil.Persistencia;
import general.me.edu.dgtmovil.dgtmovil.formpreinscripcion.FormUnoFragment;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;
import general.me.edu.dgtmovil.objetos.Respuesta;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActaMesaDosFragment extends Fragment implements OnClickListener, DatePickerDialog.OnDateSetListener{

    public static final int DATE_INIT =998;
    public static final int DATE_END =999;
    public static final int DATE_REU =997;

    private int year,yearF, yearR;
    private int month,monthF, monthR;
    private int day, dayF, dayR;

    DatePickerDialog dialog;
    public static TextView p23, p24, p25;
    Button btn_fechaCompro, btn_fechaReali, btn_fechaReunion;
    int dateType;

    public static final int SIGNATURE_ACTIVITY = 1;
    EditText p16, p17, p18,p19,p20,p21,p22;
    Spinner p26;
    TextView lp16,lp17 ,lp18,lp19 ,lp20,lp21 ,lp22 ,lp23,lp24 ,lp25, lp26;
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
    String codigoFormulario, activity;
    Button btn_guardar;
    String datoBdFirma, datoBdFirmaDos, datoBdFirmaTres, datoBdFirmaCuatro, datoBdFirmaCinco, datoBdFirmaSeis;
    ArrayList<String> codigosOpciones = new ArrayList<String>();
    int idTemp;
    ImageView firmaUno, firmaDos, firmaTres, firmaCuatro, firmaCinco, firmaSeis;

    public ActaMesaDosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_acta_mesa_dos, container, false);
        btn_guardar = (Button) view.findViewById(R.id.buttonGuardar);

        p16 = (EditText) view.findViewById(R.id.p16);
        p17 = (EditText) view.findViewById(R.id.p17);
        p18 = (EditText) view.findViewById(R.id.p18);
        p19 = (EditText) view.findViewById(R.id.p19);
        p20 = (EditText) view.findViewById(R.id.p20);
        p21 = (EditText) view.findViewById(R.id.p21);
        p22 = (EditText) view.findViewById(R.id.p22);
        p24 = (TextView) view.findViewById(R.id.p24);
        p23 = (TextView) view.findViewById(R.id.p23);
        p25 = (TextView) view.findViewById(R.id.p25);
        p26 = (Spinner) view.findViewById(R.id.p26);

        lp16 = (TextView) view.findViewById(R.id.lp16);
        lp17 = (TextView) view.findViewById(R.id.lp17);
        lp18 = (TextView) view.findViewById(R.id.lp18);
        lp19 = (TextView) view.findViewById(R.id.lp19);
        lp20 = (TextView) view.findViewById(R.id.lp20);
        lp21 = (TextView) view.findViewById(R.id.lp21);
        lp22 = (TextView) view.findViewById(R.id.lp22);
        lp23 = (TextView) view.findViewById(R.id.lp23);
        lp24 = (TextView) view.findViewById(R.id.lp24);
        lp25 = (TextView) view.findViewById(R.id.lp25);
        lp26 = (TextView) view.findViewById(R.id.lp26);

        btn_fechaCompro=(Button) view.findViewById(R.id.btn_fechaCompro);
        btn_fechaReali=(Button) view.findViewById(R.id.btn_fechaReali);
        btn_fechaReunion=(Button) view.findViewById(R.id.btn_fechaReunion);

        btn_fechaCompro.setOnClickListener(this);
        btn_fechaReali.setOnClickListener(this);
        btn_fechaReunion.setOnClickListener(this);

        firmaUno = (ImageView) view.findViewById(R.id.firma_uno);
        firmaDos = (ImageView) view.findViewById(R.id.firma_dos);
        firmaTres = (ImageView) view.findViewById(R.id.firma_tres);
        firmaCuatro = (ImageView) view.findViewById(R.id.firma_cuatro);
        firmaCinco= (ImageView) view.findViewById(R.id.firma_cinco);
        firmaSeis= (ImageView) view.findViewById(R.id.firma_seis);

        firmaUno.setOnClickListener(this);
        firmaDos.setOnClickListener(this);
        firmaTres.setOnClickListener(this);
        firmaCuatro.setOnClickListener(this);
        firmaCinco.setOnClickListener(this);
        firmaSeis.setOnClickListener(this);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = getActivity().getIntent().getExtras();

        idUsuario = bundle.getString("idUsuario");
        idPerfil = bundle.getString("perfil");
        idEntidad = bundle.getString("idEntidad");
        idFormulario = bundle.getString("idFormulario");
        gestionDatos = new GestionDatos(getContext());
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;


        preguntas = gestionDatos.listarPreguntas(idFormulario);
        opciones = gestionDatos.listarOpciones(idEntidad);

        if (preguntas != null && preguntas.length > 0) {
            Pregunta pre = getPregunta("16");
            lp16.setText(pre.DESCRIPCION);
            pre = getPregunta("17");
            lp17.setText(pre.DESCRIPCION);
            pre = getPregunta("18");
            lp18.setText(pre.DESCRIPCION);
            pre = getPregunta("19");
            lp19.setText(pre.DESCRIPCION);
            pre = getPregunta("20");
            lp20.setText(pre.DESCRIPCION);
            pre = getPregunta("21");
            lp21.setText(pre.DESCRIPCION);
            pre = getPregunta("22");
            lp22.setText(pre.DESCRIPCION);
            pre = getPregunta("23");
            lp23.setText(pre.DESCRIPCION);
            pre = getPregunta("24");
            lp24.setText(pre.DESCRIPCION);
            pre = getPregunta("25");
            lp25.setText(pre.DESCRIPCION);
            pre = getPregunta("26");
            lp26.setText(pre.DESCRIPCION);


            fijarOpciones("26", p26);

            //  p6.setText(MenuPrincipal.latitud+" "+MenuPrincipal.longitud);

        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFormulario();
            }
        });
        return  view;

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
        re.DATOTEXTO= ActaMesaUnoFragment.p1.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p2.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p3.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("4");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+ActaMesaUnoFragment.spn1;
        re.DATOTEXTO=getValorCombo(ActaMesaUnoFragment.p4);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("5");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+ActaMesaUnoFragment.spn2;
        re.DATOTEXTO=getValorCombo(ActaMesaUnoFragment.p5);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("6");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+ActaMesaUnoFragment.spn3;
        re.DATOTEXTO=getValorCombo(ActaMesaUnoFragment.p6);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("7");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+ActaMesaUnoFragment.spn4;
        re.DATOTEXTO=getValorCombo(ActaMesaUnoFragment.p7);
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
        re.DATOTEXTO=ActaMesaUnoFragment.p8.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p9.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p10.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p11.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p12.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p13.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p14.getText().toString();
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
        re.DATOTEXTO=ActaMesaUnoFragment.p15.getText().toString();
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
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p17.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("18");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p18.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("19");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p19.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("20");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p20.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("21");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p21.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("22");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p22.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("23");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p23.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);


        re=new Respuesta();
        pre = getPregunta("24");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p24.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("25");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO=p25.getText().toString();
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("26");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION=""+getCodigoOpcion(0, p26.getSelectedItemPosition()-1);
        re.DATOTEXTO=getValorCombo(p26);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("27");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirma;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("28");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirmaDos;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("29");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirmaTres;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("30");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirmaCuatro;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("31");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirmaCinco;
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario ;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("32");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
        re.DATOTEXTO="";
        re.DATOHUELLA="";
        re.DATOBINARIO=datoBdFirmaSeis;
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
                mypersistencia = new Persistencia(getContext(),sentencias);

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
        Toast toast4 = Toast.makeText(getContext(),
                mensaje, tiempo);

        toast4.show();
    }

    public void crearBaseDatos() {
        sentencias = new Sentencias(getContext(), "DBDGT", null, 2);

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
            adapterOp = new ArrayAdapter<Object>(getContext(), R.layout.spinner_item, list);

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
        int id =v.getId();
        Bundle b = new Bundle();
        if (id==R.id.firma_uno){
            b.putString("img", "imgUno");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        } else if (id==R.id.firma_dos){
            b.putString("img", "imgDos");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        }  else if (id==R.id.firma_tres){
            b.putString("img", "imgTres");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        }   else if (id==R.id.firma_cuatro){
            b.putString("img", "imgCuatro");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        } else if (id==R.id.firma_cinco){
            b.putString("img", "imgCinco");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        } else if (id==R.id.firma_seis){
            b.putString("img", "imgSeis");
            Intent intent = new Intent(getContext(), CaptureSignature.class);
            intent.putExtras(b);
            startActivityForResult(intent, SIGNATURE_ACTIVITY);
        }  else if(v.getId()== R.id.btn_fechaCompro) {
            showDatePicker(DATE_INIT, year, month, day);
        }else if (v.getId()== R.id.btn_fechaReali){
            showDatePicker(DATE_END, yearF, monthF, dayF);
        }else if (v.getId()== R.id.btn_fechaReunion){
            showDatePicker(DATE_REU, yearR, monthR, dayR);
        }
    }


    private void showDatePicker(int dateType, int year, int month, int day) {
        this.dateType= dateType;
        final Calendar c = Calendar.getInstance();

        if(year<999) {
            int yearS = c.get(Calendar.YEAR);
            int monthS = c.get(Calendar.MONTH);
            int dayS = c.get(Calendar.DAY_OF_MONTH);
            dialog = new DatePickerDialog(getActivity(), this, yearS, monthS, dayS);
        }else {
            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
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
            p23.setText(pad(month+1) + "/" + pad(day) + "/" +pad(year));
        } else if (dateType == DATE_END){
            yearF = yearS;
            monthF = monthOfYearS;
            dayF = dayOfMonthS;
            p24.setText(pad(monthF+1) + "/" + pad(dayF) + "/" +pad(yearF));
        }else if (dateType == DATE_REU){
            yearR = yearS;
            monthR = monthOfYearS;
            dayR = dayOfMonthS;
            p25.setText(pad(monthR+1) + "/" + pad(dayR) + "/" +pad(yearR));
        }
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);

    }
    public String codificarImagen(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (requestCode == SIGNATURE_ACTIVITY && resultCode == CaptureSignature.RESULT_OK ) {

                Bitmap imagenFirma = null;
                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                String img= bundle.getString("img");

                if (status.equalsIgnoreCase("done") && img.equals("imgUno")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirma = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirma));
                        datoBdFirma = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                    firmaUno.setImageDrawable(dra);
                    }
                else if (status.equalsIgnoreCase("done") && img.equals("imgDos")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirmaDos = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirmaDos));
                        datoBdFirmaDos = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                        firmaDos.setImageDrawable(dra);

                    }else if (status.equalsIgnoreCase("done") && img.equals("imgTres")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirmaTres = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirmaTres));
                        datoBdFirmaTres = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                        firmaTres.setImageDrawable(dra);


                    }else if (status.equalsIgnoreCase("done") && img.equals("imgCuatro")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirmaCuatro = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirmaCuatro));
                        datoBdFirmaCuatro = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                        firmaCuatro.setImageDrawable(dra);
                    }else if (status.equalsIgnoreCase("done") && img.equals("imgCinco")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirmaCinco = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirmaCinco));
                        datoBdFirmaCinco = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                        firmaCinco.setImageDrawable(dra);
                    }else if (status.equalsIgnoreCase("done") && img.equals("imgSeis")) {
                    //DATO PARA ALMACENAR EN LA BD
                    datoBdFirmaSeis = bundle.getString("imagen");

                    try {
                        imagenFirma = BitmapFactory.decodeStream(getActivity().openFileInput(datoBdFirmaSeis));
                        datoBdFirmaSeis = codificarImagen(imagenFirma);
                        // imagenFirma.toString();//createImageFromBitmap(imagenFirma);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // firma.setImageBitmap(imagenFirma);

                    Drawable dra= new BitmapDrawable(getResources(), imagenFirma);
                        firmaSeis.setImageDrawable(dra);
                    }
                    // firma.setBackground(Drawable.createFromPath(String.valueOf(imagenFirma)));
                   // Toast toast = Toast.makeText(getActivity(), "Signature capture successful!", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.TOP, 105, 50);
                    //toast.show();

                }
            }
        catch (Exception e){

        }
    }
}
