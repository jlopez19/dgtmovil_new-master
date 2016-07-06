package general.me.edu.dgtmovil.dgtmovil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.objetos.Formulario;
import general.me.edu.dgtmovil.objetos.Usuario;

import static general.me.edu.dgtmovil.R.id.BtnBoton6;


public class MainActivity extends AppCompatActivity {

    Sentencias sentencias = null;
    GestionDatos gestionDatos;
    Persistencia mypersistencia;
    TextView textToast;
    View layout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gestionDatos=new GestionDatos(getApplicationContext());
        crearBaseDatos();

        gestionDatos.sentencias = sentencias;
        mypersistencia = new Persistencia(getApplicationContext(),sentencias);

        //if(!gestionDatos.existePrograma("1")){
        //registrarProgramas();
        //	gestionDatos.agregarColumnas();
        //}
        final EditText txtLogin = (EditText) findViewById(R.id.editText1);
        final EditText txtPassword = (EditText) findViewById(R.id.editText2);
        final Button btnAceptar = (Button) findViewById(R.id.BtnBoton5);

        ///////*******************************************CODIGO PARA RECUPERAR CONTRASENIA
        final Button btnRecuperarContrasenia = (Button) findViewById(BtnBoton6);

        LayoutInflater inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.custom_dialog,(ViewGroup) findViewById(R.id.toast_layout_root));
        textToast = (TextView) layout.findViewById(R.id.texto);
        //obtenerUsuarios();
        //obtenerFormularios();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtLogin.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);

        //CODIGO BOTON RECUPERAR CONTRASEÑA
        btnRecuperarContrasenia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String idA = gestionDatos.consultarUsuario(txtLogin.getText()
                            .toString(), txtPassword.getText().toString());

                    String toEmails = "joseluissanchezceballos@gmail.com";
                    List<String> toEmailList = Arrays.asList(toEmails
                            .split("\\s*,\\s*"));
                    new SendMailTask(MainActivity.this).execute("preducacion2016@gmail.com",
                            "123456preducacion", toEmailList, "Contraseña","123");
                  /*  GMail sender = new GMail("yulagarces@gmail.com", "befree1455*");
                    sender.sendMail("Contraseña",
                            "Aquí va tu contraseña nuevamente",
                            "yulagarces@gmail.com",
                            "user@yahoo.com");*/
                    Toast.makeText(MainActivity.this, "Enviando mensaje al correo electrónico: ", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: "+ e.toString(), Toast.LENGTH_LONG).show();
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        // Implementamos el evento
        btnAceptar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    //	BD_backup();
                }
                catch(Exception ex){

                }

                // Creamos el Intent
                Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);

                // Creamos la informaciï¿½n a pasar entre actividades
                Bundle b = new Bundle();
                String idA = gestionDatos.consultarUsuario(txtLogin.getText()
                        .toString(), txtPassword.getText().toString());

                if (!idA.equals("0")) {
                    b.putString("idUsuario", idA);
                    b.putString("nombre", gestionDatos.nombre);
                    b.putString("perfil", gestionDatos.perfil);
                    b.putString("idEntidad", gestionDatos.idEntidad);

                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast toast4 = Toast.makeText(getApplicationContext(),
                            "Datos Invalidos", Toast.LENGTH_SHORT);
                    toast4.show();
                }

            }
        });

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("Usuarios");
        menu.add("Actualizar");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.toString().equals("Usuarios")){
            item.setIcon(R.drawable.ico_configuracion);
            Toast.makeText(this, "Sincronizando Usuarios", Toast.LENGTH_SHORT).show();
            obtenerUsuarios();
            obtenerFormularios();
        }
        else if(item.toString().equals("Actualizar")){
            item.setIcon(R.drawable.ico_salir);
            String link = "http://173.201.37.144:8105/install/SALUD.zip";
            Intent intent = null;
            intent = new Intent(intent.ACTION_VIEW,Uri.parse(link));
            startActivity(intent);
            //  descargarArchivo();
        }

        return super.onOptionsItemSelected(item);
    }


    public void descargarArchivo(){
        try {
            //primero especificaremos el origen de nuestro archivo a descargar utilizando
            //la ruta completa
            URL url = new URL("http://173.201.37.144:8105/Medicina/SALUD.zip");

            //establecemos la conexiï¿½n con el destino
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //establecemos el mï¿½todo jet para nuestra conexiï¿½n
            //el mï¿½todo setdooutput es necesario para este tipo de conexiones
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //por ï¿½ltimo establecemos nuestra conexiï¿½n y cruzamos los dedos <img src="http://www.insdout.com/wp-includes/images/smilies/icon_razz.gif" alt=":P" class="wp-smiley">
            urlConnection.connect();

            //vamos a establecer la ruta de destino para nuestra descarga
            //para hacerlo sencillo en este ejemplo he decidido descargar en
            //la raï¿½z de la tarjeta SD
            File SDCardRoot = Environment.getExternalStorageDirectory();

            //vamos a crear un objeto del tipo de fichero
            //donde descargaremos nuestro fichero, debemos darle el nombre que
            //queramos, si quisieramos hacer esto mas completo
            //cogerï¿½amos el nombre del origen
            File file = new File(SDCardRoot,"SALUD.zip");

            //utilizaremos un objeto del tipo fileoutputstream
            //para escribir el archivo que descargamos en el nuevo
            FileOutputStream fileOutput = new FileOutputStream(file);

            //leemos los datos desde la url
            InputStream inputStream = urlConnection.getInputStream();

            //obtendremos el tamaï¿½o del archivo y lo asociaremos a una
            //variable de tipo entero
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;

            //creamos un buffer y una variable para ir almacenando el
            //tamaï¿½o temporal de este
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            //ahora iremos recorriendo el buffer para escribir el archivo de destino
            //siempre teniendo constancia de la cantidad descargada y el total del tamaï¿½o
            //con esto podremos crear una barra de progreso
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                //podrï¿½amos utilizar una funciï¿½n para ir actualizando el progreso de lo
                //descargado
                //  actualizaProgreso(downloadedSize, totalSize);

            }
            //cerramos
            fileOutput.close();

            //y gestionamos errores
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void obtenerUsuarios(){
        if(existeConexion()){
            mypersistencia = new Persistencia(getApplicationContext(),sentencias);
            mypersistencia.execute("9");
            try{
                //  mypersistencia.wait();
                mypersistencia.get();
                Usuario[] listaUsuarios=mypersistencia.listaUsuarios;
                Usuario lect;
                if(listaUsuarios!=null && listaUsuarios.length>0){
                    for(int i=0;i<listaUsuarios.length;i++){
                        lect=listaUsuarios[i];
                        int aux=i+1;
                        //	mostrarMensaje("Guardando "+aux+" de "+listaUsuarios.length, 1);
                        gestionDatos.crearUsuario(lect);
                    }
                }
                else{
                    mostrarMensaje("No hay datos Usuarios", 3);
                }




            }
            catch(Exception ex){

            }
            mostrarMensaje("Proceso Terminado", 3);
        }

        else{
            mostrarMensaje("No hay conexion con el server", 3);
        }

    }

    public void obtenerFormularios(){
        if(existeConexion()){
            mypersistencia = new Persistencia(getApplicationContext(),sentencias);
            mypersistencia.execute("21");
            try{
                //  mypersistencia.wait();
                mypersistencia.get();
                Formulario[] listaUsuarios=mypersistencia.listaFormularios;
                Formulario lect;
                if(listaUsuarios!=null && listaUsuarios.length>0){
                    for(int i=0;i<listaUsuarios.length;i++){
                        lect=listaUsuarios[i];
                        int aux=i+1;
                        //	mostrarMensaje("Guardando "+aux+" de "+listaUsuarios.length, 1);
                        gestionDatos.crearFormulario(lect);
                    }
                }
                else{
                    mostrarMensaje("No hay datos Formularios", 3);
                }




            }
            catch(Exception ex){

            }
            mostrarMensaje("Formularios Cargados", 3);
        }



        else{
            mostrarMensaje("No hay conexion con el server", 3);
        }

    }


    public void crearBaseDatos() {
        sentencias = new Sentencias(this, "DBDGT", null, 2);

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


    public void mostrarMensaje(String mensaje,int tiempo){
        textToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }



    public static void BD_backup() throws IOException {

        //	String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        try{

            final String inFileName = "/data/data/com.example.slfs/databases/DBDGT";

            File dbFile = new File(inFileName);

            FileInputStream fis = null;



            fis = new FileInputStream(dbFile);




            File d = new File(Environment
                    .getExternalStorageDirectory(), "APS");


            if (!d.exists()) {

                d.mkdir();

            }

            String outFileName = d.getAbsolutePath() + "/DBDGT";



            OutputStream output = new FileOutputStream(outFileName);



            byte[] buffer = new byte[1024];

            int length;

            while ((length = fis.read(buffer)) > 0) {

                output.write(buffer, 0, length);

            }



            output.flush();

            output.close();

            fis.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


    private void copyDataBase()
    {

        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try
        {
            myInput =getApplicationContext().getAssets().open("DBDGT");
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput =new FileOutputStream("/data/data/com.example.slfs/databases/DBDGT");
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();



        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
