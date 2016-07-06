package general.me.edu.dgtmovil.dgtmovil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import general.me.edu.dgtmovil.R;
import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.dgtmovil.formactafocalizacion.FromActaFocaActivity;
import general.me.edu.dgtmovil.dgtmovil.formactaforomunpal.ActaForoMunpalActivity;
import general.me.edu.dgtmovil.dgtmovil.formmesaseguridad.FormMesaSeguridadActivity;
import general.me.edu.dgtmovil.dgtmovil.formmesatecnica.FormMesaTecnicaActivity;
import general.me.edu.dgtmovil.dgtmovil.formpreinscripcion.CargarDatosActivity;
import general.me.edu.dgtmovil.dgtmovil.formregisestudiante.RegistarEstudianteActivity;
import general.me.edu.dgtmovil.objetos.Formulario;

public class FormulariosActivity extends AppCompatActivity {

    String idUsuario;
    Sentencias sentencias;

    String idEntidad = "";
    String nombre;
    GestionDatos gestionDatos;

    ListView listadoFormularios;
    public Formulario formularios[];
    AdaptadorFormulario adaptadorFormulario;
 //   TextView txtTerritorio;
    ArrayList<ItemFormulario> arregloFormularios;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT - Formularios");
        getSupportActionBar().setIcon(R.drawable.ico_cgt);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gestionDatos = new GestionDatos(getApplicationContext());
        crearBaseDatos();

        gestionDatos.sentencias = sentencias;
        Bundle bundle = this.getIntent().getExtras();

        listadoFormularios = (ListView) findViewById(R.id.lst_formularios);
       // txtTerritorio = (TextView) findViewById(R.id.txt_territorio);
        idUsuario = bundle.getString("idUsuario");
        idEntidad = bundle.getString("idEntidad");
        nombre = bundle.getString("nombre");
        this.setTitle("" + bundle.getString("nombre"));
        formularios = gestionDatos.listarFormularios(idEntidad);
        arregloFormularios = new ArrayList<ItemFormulario>();
        Toast.makeText(getApplicationContext(), "Entidad: "+formularios.length, Toast.LENGTH_SHORT).show();
        arregloFormularios = convertirArray(formularios);
    //    txtTerritorio.setText(idUsuario);
        adaptadorFormulario = new AdaptadorFormulario(FormulariosActivity.this, R.layout.item_formulario,arregloFormularios);
        listadoFormularios.setAdapter(adaptadorFormulario);
        listadoFormularios.setOnItemClickListener(irFormulario);
    }

   AdapterView.OnItemClickListener irFormulario = new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           String idFormulario = formularios[position].ID_FORMULARIO;
           Bundle b = new Bundle();
           b.putString("idUsuario", idUsuario);
           b.putString("idEntidad", idEntidad);
           b.putString("nombre", nombre);
           b.putString("idFormulario", idFormulario);

           switch (idFormulario) {
               case "19": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, FormMesaTecnicaActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               case "18": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, FromActaFocaActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               case "20": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, FormMesaSeguridadActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               case "21": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, ActaForoMunpalActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               case "98": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, RegistarEstudianteActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               case "31": {
                   Intent goDatosActivity = new Intent(FormulariosActivity.this, CargarDatosActivity.class);
                   goDatosActivity.putExtras(b);
                   startActivity(goDatosActivity);
                   break;
               }
               default:
                   Toast.makeText(getApplication(), "Formulario en proceso", Toast.LENGTH_LONG).show();
                   break;
           }
       }
   };


    public ArrayList<ItemFormulario> convertirArray(Formulario[] formulario){
        ArrayList<ItemFormulario> formularioTemp = new ArrayList<ItemFormulario>();
        ItemFormulario item;
        for(int i = 0; i < formulario.length; i++){
            item = new ItemFormulario();
            item.setTitulo(formulario[i].TITULO);
            item.setDescripcion(formulario[i].DESCRIPCION);
            formularioTemp.add(item);
        }
        return  formularioTemp;
    }
    public void crearBaseDatos() {
        sentencias = new Sentencias(this, "DBDGT", null, 2);

    }

}
