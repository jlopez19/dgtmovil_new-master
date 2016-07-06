package general.me.edu.dgtmovil.dgtmovil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import general.me.edu.dgtmovil.R;


public class MenuPrincipal extends AppCompatActivity {


    ImageButton imagenNuevo;
    ImageButton imagenGlecturas;
    ImageButton imagenConfiguracion;
    ImageButton imagenSalir;
    ImageButton imagenReportar;

    String idUsuario;
    String idPerfil;
    String idEntidad = "";
    String nombre = "";
    TextView nombreEntidad;
    public static String latitud;
    public static String longitud;
    public static String coordenadas;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CGT");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        idUsuario = bundle.getString("idUsuario");
        idPerfil = bundle.getString("perfil");
        idEntidad = bundle.getString("idEntidad");
        nombre = bundle.getString("nombre");

        this.setTitle("" + bundle.getString("nombre"));

        imagenNuevo = (ImageButton) this.findViewById(R.id.imageView1);
        imagenGlecturas = (ImageButton) this.findViewById(R.id.imageView2);
        imagenConfiguracion = (ImageButton) this.findViewById(R.id.imageView4);
        imagenSalir = (ImageButton) this.findViewById(R.id.imageView5);
        nombreEntidad = (TextView) findViewById(R.id.txt_territorio);
        imagenReportar = (ImageButton) this.findViewById(R.id.imageView6);
        LocationManager milocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener milocListener = new MiLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        milocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, milocListener);


        // Toast.makeText(getApplicationContext(), "Entidad: " + idEntidad, Toast.LENGTH_SHORT).show();
        // nombreEntidad.setText("" + idUsuario);

        if (idPerfil.equals("3")) {
            imagenGlecturas.setEnabled(false);
        }

        imagenNuevo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;
                //if (idEntidad.equals("1")) {
                intent = new Intent(MenuPrincipal.this, FormulariosActivity.class);
                //}

                // Creamos la informaciï¿½n a pasar entre actividades
                Bundle b = new Bundle();
                b.putString("idUsuario", idUsuario);
                b.putString("idEntidad", idEntidad);
                b.putString("nombre", nombre);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        imagenGlecturas.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Sincronizar.class);

                // Creamos la informaciï¿½n a pasar entre actividades
                Bundle b = new Bundle();
                //	b.putInt("idAgente", idAgente);
                b.putString("idUsuario", idUsuario);
                b.putString("idEntidad", idEntidad);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        imagenReportar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ReportesActivity.class);

                // Creamos la informaciï¿½n a pasar entre actividades
                startActivity(intent);
            }
        });


        imagenSalir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, MainActivity.class);

                // Creamos la informaciï¿½n a pasar entre actividades
                Bundle b = new Bundle();
                //	b.putInt("idAgente", idAgente);

                intent.putExtras(b);
                startActivity(intent);
            }


        });


        imagenConfiguracion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Reprocesar.class);
                startActivity(intent);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //	getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MenuPrincipal Page") // TODO: Define a title for the content shown.
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


    public class MiLocationListener implements LocationListener

    {

        public void onLocationChanged(Location loc)

        {
            longitud = loc.getLongitude() + "";
            latitud = loc.getLatitude() + "";

            //	String coordenadas = loc.getLatitude()  + loc.getLongitude();
            if (latitud.length() > 5) {
                coordenadas = latitud;
                setTitle("CGT GPS: OK");
                //setTitle("Menu "+coordenadas);
            } else {
                latitud = "";
                longitud = "";
                coordenadas = "No hay señal de Gps";
                //setTitle("Menu - No hay señal de Gps");
            }
            //Toast.makeText(getApplicationContext(), coordenadas,
            //	Toast.LENGTH_LONG).show();

            // fijarTitulo();
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Desactivado",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Activo",
                    Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}
