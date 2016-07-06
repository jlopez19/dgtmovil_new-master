package general.me.edu.dgtmovil.dgtmovil;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.objetos.Afiliado;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;

public class Reprocesar extends AppCompatActivity {

    Sentencias sentencias = null;
    String enviados = "0";

    TextView txtcantcomparendos = null;
    TextView txtmensajereproceso = null;
    TextView resultado = null;
    Button btnReprocesar = null;
    ImageView btnSalir = null;
    ProgressBar barraprogreso=null;
    GestionDatos gestionDatos=null;
    Persistencia mypersistencia;
    Per myPer;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprocesar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT - Reprocesar");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gestionDatos=new GestionDatos(getApplicationContext());
        txtcantcomparendos = (TextView) findViewById(R.id.textViewCantComparendos);
        txtmensajereproceso = (TextView) findViewById(R.id.textViewMensajeReproceso);
        resultado = (TextView) findViewById(R.id.resultado);

        btnReprocesar = (Button) findViewById(R.id.buttonReprocesar);
        btnSalir = (ImageView) findViewById(R.id.imageButtonSalirReproceso);
        barraprogreso = (ProgressBar)findViewById(R.id.progressBarAgentes);

        crearBaseDatos();
        gestionDatos.sentencias=sentencias;
        myPer =new Per(sentencias);
        mypersistencia=new Persistencia(getApplicationContext(),sentencias);
        inicializar();

        btnSalir.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });




        btnReprocesar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                reprocesar();

            }
        });

    }


    //mostrarMensaje("Proceso Terminado", 3);


    public void reprocesar(){
        //   new Thread(new Runnable() {
        //  public void run() {
        //    enableTestButton(false);

        //  mostrarMensaje("Proceso Iniciado", 3);
        resultado.setText("ENVIANDO DATOS");
        enableTestButton(false);
        myPer =new Per(sentencias);
        if(myPer.existeConexion()){
            try {
                myPer =new Per(sentencias);
                //	myPer.registrarNuevos();
                myPer.enviarPendientes();
                inicializar();
                resultado.setText("PROCESO TERMINADO");

            } catch (Exception exp) {
                mostrarMensaje("Error al enviar  " + exp.getMessage(), 3);

                inicializar();
                resultado.setText("PROCESO TERMINADO");
            }
        }
        else{
            mostrarMensaje("No hay conexion con el server", 3);
        }

    }
    //}).start();
    //}


    private void enableTestButton(final boolean enabled) {
        runOnUiThread(new Runnable() {
            public void run() {
                btnReprocesar.setBackgroundColor(Color.RED);
            }
        });
    }

    private void inicializar() {

        GestionDatos mygestiondatos = new GestionDatos(getApplication());
        mygestiondatos.sentencias = this.sentencias;

        int cantUbicaciones = mygestiondatos.contarUbicacionesNoEnviadas();
        txtcantcomparendos.setText(" Numero de Formularios:"+cantUbicaciones);

        if (cantUbicaciones==0) {
            txtmensajereproceso.setText("NO ES NECESARIO REPROCESAR");
            btnReprocesar.setEnabled(false);
        } else {
            txtmensajereproceso.setText("Es necesario reprocesar");
            btnReprocesar.setEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //	getMenuInflater().inflate(R.menu.reprocesar, menu);
        return true;
    }

    private void crearBaseDatos() {
        sentencias = new Sentencias(this, "DBDGT", null, 2);

    }

    public void mostrarMensaje(String mensaje, int tiempo) {
        //Toast toast4 = Toast.makeText(getApplicationContext(), mensaje, tiempo);

        //toast4.show();
        resultado.setText(mensaje);
    }


    //*****************************************************************
    private class Per extends AsyncTask<String, Integer, String>{

        Sentencias sentencias;
        public Per(Sentencias sent)
        {
            this.sentencias = sent;
        }


        @Override
        protected void onPreExecute()
        {
            btnReprocesar.setText("Reprocesando...");
            btnSalir.setEnabled(false);
            btnReprocesar.setEnabled(false);
            barraprogreso.setVisibility(ProgressBar.VISIBLE);

        }

        @Override
        protected void onPostExecute(String result)
        {
            enviados=result;
            barraprogreso.setVisibility(ProgressBar.INVISIBLE);
            btnReprocesar.setText("Reprocesar");

            mostrarMensaje(String.valueOf(enviados)
                    + " comparendos enviados", 3);

            btnSalir.setEnabled(true);

            inicializar();

        }


        @Override
        protected String doInBackground(String... params) {
            String result="true";
            //	registrarNuevos();
            //enviarPendientes();
            return result;
        }


        public boolean enviarPendientes(){

            FormularioRespuesta[] listaFormularioRespuesta=gestionDatos.getFormulariosRespuestaNoEnviada();
            int r=0;
            try{
                mostrarMensaje("Proceso Iniciado", 3);

                r=0;
                if(listaFormularioRespuesta!=null && listaFormularioRespuesta.length>0){
                    for(int i=0;i<listaFormularioRespuesta.length;i++){
                        r=i+1;
                        FormularioRespuesta formulario=listaFormularioRespuesta[i];
                        mypersistencia = new Persistencia(getApplicationContext(),sentencias);
                        mypersistencia.formulario=formulario;

                        mypersistencia.execute("3");
                        String[] res=mypersistencia.get();
                        if(res!=null && res.length>0){
                            if(res[0].equals("true")){// envio bien editar estado y enviado
                                gestionDatos.editarEnviadoFormularioRespuesta(formulario.CODIGO,"SI");
                                mostrarMensaje("Enviado Ubicaciones "+r+" de "+listaFormularioRespuesta.length,3);

                            }
                        }
                    }
                }

            }
            catch(Exception e){

            }


            return true;
        }

        public boolean existeConexion(){
            try{
                mypersistencia = new Persistencia(getApplicationContext(),sentencias);
                mypersistencia.execute("4");
                String[] res=mypersistencia.get();
                if(res!=null && res.length>0){
                    if(res[0].equals("true")){// envio bien editar estado y enviado
                        return true;
                    }
                    else{
                        return  false;
                    }
                }
            }
            catch(Exception e){
                return false;
            }
            return false;
        }








        public void registrarNuevos(){
            try{
                Afiliado[] listaAfiliados=gestionDatos.obtenerAfiliadosNuevos();

                for(int i=0;i<listaAfiliados.length;i++){

                    Afiliado afil=listaAfiliados[i];
                    mypersistencia = new Persistencia(getApplicationContext(),sentencias);
                    mypersistencia.afiliado=afil;
                    mypersistencia.execute("10");
                    String[] res=mypersistencia.get();
                    if(res!=null && res.length>0){
                        if(res[0].equals("true")){// envio bien editar estado y enviado
                            gestionDatos.editarEnviadoAfiliado(afil.IDENTIFICACION);

                        }

                    }
                }
            }
            catch(Exception e){

            }
        }
    }


    //*****************************************************************

}


