package general.me.edu.dgtmovil.dgtmovil.formpreinscripcion;
import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class FormUnoFragment extends Fragment implements OnClickListener, DatePickerDialog.OnDateSetListener{

    public static EditText p1, p7, p8,p5,p12,p11, p13, p15;
    public static Spinner p3,p2,p4,p6,p10, p14;
    TextView lp1,lp2 ,lp3,lp4 ,lp5,lp6 ,lp7,lp8 ,lp9,lp10 ,lp11, lp12, lp13, lp14, lp15;
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
    public static String spn1, spn2, spn3, spn4, spn5, spn6;
    private int year;
    private int month;
    private int day;
    Button btn_fecha;
    int hour;
    int dateType;
    public static final int DATE_INIT =998;
    DatePickerDialog dialog;
    public static TextView p9;

    ArrayList<String> codigosOpciones = new ArrayList<String>();
    int idTemp;

    public FormUnoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_uno, container, false);

        p1 = (EditText) view.findViewById(R.id.p1);
        p2 = (Spinner) view.findViewById(R.id.p2);
        p4 = (Spinner) view.findViewById(R.id.p4);
        p5 = (EditText) view.findViewById(R.id.p5);
        p6 = (Spinner) view.findViewById(R.id.p6);
        p10 = (Spinner) view.findViewById(R.id.p10);
        p11 = (EditText) view.findViewById(R.id.p11);
        p3 = (Spinner) view.findViewById(R.id.p3);
        p14 = (Spinner) view.findViewById(R.id.p14);
        p7 = (EditText) view.findViewById(R.id.p7);
        p8 = (EditText) view.findViewById(R.id.p8);
        p9 = (TextView) view.findViewById(R.id.p9);
        p11 = (EditText) view.findViewById(R.id.p11);
        p12 = (EditText) view.findViewById(R.id.p12);
        p13 = (EditText) view.findViewById(R.id.p13);
        p15 = (EditText) view.findViewById(R.id.p15);

        lp1 = (TextView) view.findViewById(R.id.lp1);
        lp2 = (TextView) view.findViewById(R.id.lp2);
        lp3 = (TextView) view.findViewById(R.id.lp3);
        lp4 = (TextView) view.findViewById(R.id.lp4);
        lp5 = (TextView) view.findViewById(R.id.lp5);
        lp6 = (TextView) view.findViewById(R.id.lp6);
        lp7 = (TextView) view.findViewById(R.id.lp7);
        lp8 = (TextView) view.findViewById(R.id.lp8);
        lp9 = (TextView) view.findViewById(R.id.lp9);
        lp10 = (TextView) view.findViewById(R.id.lp10);
        lp11 = (TextView) view.findViewById(R.id.lp11);
        lp12 = (TextView) view.findViewById(R.id.lp12);
        lp13 = (TextView) view.findViewById(R.id.lp13);
        lp14 = (TextView) view.findViewById(R.id.lp14);
        lp15 = (TextView) view.findViewById(R.id.lp15);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = getActivity().getIntent().getExtras();

        idUsuario = bundle.getString("idUsuario");
        idPerfil = bundle.getString("perfil");
        idEntidad = bundle.getString("idEntidad");
        idFormulario = bundle.getString("idFormulario");
        gestionDatos = new GestionDatos(getContext());
        crearBaseDatos();
        gestionDatos.sentencias = sentencias;

        btn_fecha=(Button)view.findViewById(R.id.btn_fecha);
        btn_fecha.setOnClickListener(this);

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


            fijarOpciones("2", p2);
            fijarOpciones("3", p3);
            fijarOpciones("4", p4);
            fijarOpciones("6", p6);
            fijarOpciones("10", p10);
            fijarOpciones("14", p14);

            p2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn1= ""+getCodigoOpcion(0, p2.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            p3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn2= ""+getCodigoOpcion(1, p3.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            p4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn3= ""+getCodigoOpcion(2, p4.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            p6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn4= ""+getCodigoOpcion(3, p6.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            p10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn5= ""+getCodigoOpcion(4, p10.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            p14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spn6= ""+getCodigoOpcion(5, p14.getSelectedItemPosition()-1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
          //  p6.setText(MenuPrincipal.latitud+" "+MenuPrincipal.longitud);

        }

   /*     bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFormulario();
            }
        });*/
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
        formulario.LATITUD=MenuPrincipal.latitud;
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
        re.DATOTEXTO=getValorCombo(p2);
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
        re.DATOTEXTO=getValorCombo(p3);
        re.DATOHUELLA="";
        re.DATOBINARIO="";
        re.ID_FORMULARIORESPUESTA="0";
        re.OBSERVACION="";
        re.CODIGO_FORMULARIO=codigoFormulario;
        listaRespuestas.add(re);

        re=new Respuesta();
        pre = getPregunta("4");
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
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
        re.ID_PREGUNTA=pre.ID_PREGUNTA;
        re.ID_PREGUNTAOPCION="0";
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
        re.DATOTEXTO=getValorCombo(p10);
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
        re.DATOTEXTO=getValorCombo(p14);
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
        int id = v.getId();

        if (id == R.id.btn_fecha) {
            showDatePicker(DATE_INIT, year, month, day);
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
            p9.setText(pad(month+1) + "/" + pad(day) + "/" +pad(year));
        }
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


}
