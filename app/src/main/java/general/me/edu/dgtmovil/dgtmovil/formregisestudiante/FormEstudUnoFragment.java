package general.me.edu.dgtmovil.dgtmovil.formregisestudiante;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import android.view.View.OnClickListener;
import general.me.edu.dgtmovil.DialogFragmentDescrip;
import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.dgtmovil.MenuPrincipal;
import general.me.edu.dgtmovil.dgtmovil.Persistencia;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;
import general.me.edu.dgtmovil.objetos.Respuesta;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormEstudUnoFragment extends Fragment implements  OnClickListener, DatePickerDialog.OnDateSetListener{

    public static EditText p1, p2, p4,p6,p10,p11, et_longitud;
    public static Spinner p3,p7,p8,p9;
    TextView lp1,lp2 ,lp3,lp4 ,lp5,lp6 ,lp7,lp8 ,lp9,lp10 ,lp11, tv_longitud;
    String codigoFormulario;


    String datoBdFoto;
    String datoBdFirma;


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
    public static TextView obli_uno, obli_dos, obli_tres, obli_cuatro;
    //VARIABLES PRUEBA CODIGO OPCION
    ArrayList<String> codigosOpciones = new ArrayList<String>();
    int idTemp;
    private int year;
    private int month;
    private int day;
    Button btn_fecha;
    int hour;
    public static String spn1, spn2, spn3;
    int dateType;
    public static final int DATE_INIT =998;
    DatePickerDialog dialog;
    public static TextView p5;
    public FormEstudUnoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_form_estud_uno, container, false);
        obli_uno=(TextView)view.findViewById(R.id.p3_obligatorio);
        obli_dos=(TextView) view.findViewById(R.id.p7_obligatorio);
        obli_tres=(TextView) view.findViewById(R.id.p8_obligatorio);

        addDescUno=(ImageView) view.findViewById(R.id.btn_addComNombre);
        addDescUno.setOnClickListener(this);
        addDesDos=(ImageView)view.findViewById(R.id.btn_addComApellido);
        addDesDos.setOnClickListener(this);
        addDesTres=(ImageView)view.findViewById(R.id.btn_addComDocumento);
        addDesTres.setOnClickListener(this);
        addDescuatro=(ImageView)view.findViewById(R.id.btn_addComFecha);
        addDescuatro.setOnClickListener(this);
        addDesCinco=(ImageView)view.findViewById(R.id.btn_addComCoord);
        addDesCinco.setOnClickListener(this);
        addDesSeis=(ImageView)view.findViewById(R.id.btn_addComEstudio);
        addDesSeis.setOnClickListener(this);
        addDesSiete=(ImageView)view.findViewById(R.id.btn_addComOpina);
        addDesSiete.setOnClickListener(this);


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        p1 = (EditText) view.findViewById(R.id.p1);
        p2 = (EditText) view.findViewById(R.id.p2);
        p4 = (EditText) view.findViewById(R.id.p4);
        p5 = (TextView) view.findViewById(R.id.p5);
        p6 = (EditText) view.findViewById(R.id.p6);
        p10 = (EditText) view.findViewById(R.id.p10);
        p11 = (EditText) view.findViewById(R.id.p11);
        et_longitud = (EditText) view.findViewById(R.id.txt_longitud);
        p3=  (Spinner) view.findViewById(R.id.p3);
        p7=  (Spinner) view.findViewById(R.id.p7);
        p8=  (Spinner) view.findViewById(R.id.p8);

        lp1=  (TextView) view.findViewById(R.id.lp1);
        lp2=  (TextView) view.findViewById(R.id.lp2);
        lp3=  (TextView) view.findViewById(R.id.lp3);
        lp4=  (TextView) view.findViewById(R.id.lp4);
        lp5=  (TextView) view.findViewById(R.id.lp5);
        lp6=  (TextView) view.findViewById(R.id.lp6);
        lp7=  (TextView) view.findViewById(R.id.lp7);
        lp8=  (TextView) view.findViewById(R.id.lp8);
        tv_longitud=  (TextView) view.findViewById(R.id.l_longitud);

        btn_fecha=(Button)view.findViewById(R.id.btn_fecha);
        btn_fecha.setOnClickListener(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        //Construimos el mensaje a mostrar

        idUsuario=bundle.getString("idUsuario");
        idPerfil=bundle.getString("perfil");
        idEntidad=bundle.getString("idEntidad");
        idFormulario=bundle.getString("idFormulario");
        gestionDatos=new GestionDatos(getActivity());
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;

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


            fijarOpciones("3",p3);
            fijarOpciones("7",p7);
            fijarOpciones("8",p8);

            p6.setText(MenuPrincipal.latitud);
            et_longitud.setText(MenuPrincipal.longitud);
        }

        p3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn1= ""+getCodigoOpcion(0, p3.getSelectedItemPosition()-1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        p7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn2= ""+getCodigoOpcion(1, p7.getSelectedItemPosition()-1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        p8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spn3= ""+getCodigoOpcion(2, p8.getSelectedItemPosition()-1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
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
            p4.setError("campo obligatorio");
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
                mypersistencia = new Persistencia(getActivity(),sentencias);

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
        Toast toast4 = Toast.makeText(getActivity(),
                mensaje, tiempo);

        toast4.show();
    }

    public void crearBaseDatos() {
        sentencias = new Sentencias(getActivity(), "DBDGT", null, 2);

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
            adapterOp = new ArrayAdapter<Object>(getActivity(),
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


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fecha) {
            showDatePicker(DATE_INIT, year, month, day);
        } else if (id == R.id.btn_addComNombre) {
            cargarDialog();
        } else if (id == R.id.btn_addComApellido) {
            cargarDialog();
        } else if (id == R.id.btn_addComDocumento) {
            cargarDialog();
        } else if (id == R.id.btn_addComFecha) {
            cargarDialog();
        } else if (id == R.id.btn_addComCoord) {
            cargarDialog();
        } else if (id == R.id.btn_addComEstudio) {
            cargarDialog();
        } else if (id == R.id.btn_addComOpina) {
            cargarDialog();
        }

    }

    private void cargarDialog() {
        DialogFragmentDescrip dialogFragment = new DialogFragmentDescrip ();
        dialogFragment.show(getActivity().getFragmentManager() ,"Descripcion");

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



