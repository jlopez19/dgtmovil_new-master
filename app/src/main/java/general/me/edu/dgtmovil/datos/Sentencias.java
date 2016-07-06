package general.me.edu.dgtmovil.datos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Jennyfer Lopez on 17/06/2016.
 */

public class Sentencias  extends SQLiteOpenHelper {


    Context contexto;
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateAgente = "CREATE TABLE agente (id INTEGER, nombre TEXT,apellido TEXT,cedula TEXT,placa TEXT,login TEXT,password TEXT)";
    String sqlCreateUsuarios = "CREATE TABLE CEN_USUARIOS (ID INTEGER, NOMBRE TEXT,APELLIDO TEXT,CEDULA TEXT," +
            "LOGIN TEXT,PASSWORD TEXT,ID_PERFIL TEXT,ID_PERSONA TEXT,ID_ENTIDAD TEXT)";
    // String sqlCreateUbicacionVivienda = "CREATE TABLE  UBICACION_VIVIENDA (ID INTEGER,ID_DEPARTAMENTO TEXT,ID_MUNICIPIO TEXT,ID_ZONA TEXT,ID_BARRIO TEXT,MANZANA TEXT ,ESTRATO TEXT ,DIRECCION TEXT,VEREDA TEXT,TELEFONO TEXT,NO_FICHA TEXT,FORMULARIO TEXT,FECHA TEXT,ID_ENTIDAD TEXT,ID_USUARIO TEXT,CODIGO TEXT,X TEXT,Y TEXT,OBSERVACION TEXT,ENVIADO TEXT)";
    String sqlCreatePreguntas = "CREATE TABLE  PREGUNTAS (ID_PREGUNTA TEXT,CODIGO TEXT," +
            "DESCRIPCION TEXT,ESTADO TEXT,ID_GRUPO TEXT,ORDEN TEXT,ID_TIPO_PREGUNTA TEXT," +
            "ID_FORMULARIO TEXT,OBLIGATORIA TEXT,ID_TIPODATOPREGUNTA TEXT,COLUMNA TEXT,EXIGEOBSERVACION TEXT,EXIGEHUELLA TEXT," +
            "EXIGEFOTO TEXT)";
    String sqlCreateOpciones = "CREATE TABLE  OPCIONES (ID TEXT,CODIGO TEXT,RESPUESTA TEXT,COD_PREGUNTA TEXT,ID_ENTIDAD TEXT)";
    String sqlCreateRespuestas = "CREATE TABLE  RESPUESTA (ID_RESPUESTA TEXT,ID_PREGUNTA TEXT,ID_PREGUNTAOPCION TEXT," +
            "DATOTEXTO TEXT,DATOHUELLA TEXT,DATOBINARIO TEXT,ID_FORMULARIORESPUESTA TEXT,OBSERVACION TEXT,CODIGO_FORMULARIO TEXT)";
    String sqlCreateAfiliado = "CREATE TABLE  AFILIADO (ORDEN TEXT, NOMBRE TEXT, APELLIDOS TEXT, TD  TEXT, IDENTIFICACION TEXT, SEXO TEXT, RECONOCE_COMO  TEXT, FN TEXT, PARENTESCO  TEXT, ESTADO_CIVIL  TEXT, CEDULA_CONYUGUE TEXT, NUMERO_ORDEN_CONYUGUE  TEXT, ORDEN_PADRE  TEXT, ARRIENDO_OTRO TEXT, DISCAPACIDAD_PERMANENTE  TEXT, AFILIADO_A  TEXT, EMBARAZADA_HIJOS  TEXT, ASISTENCIA_CENTRO_EDUCATIVO  TEXT, TIPO_ESTABLECIMIENTO TEXT, NIVEL_EDUCATIVO TEXT, PORQUE_NO_CONTINUO_ESTUDIOS  TEXT, GUSTARIA_SEGUIR_ESTUDIANDO  TEXT, PERCIBE_INGRESOS TEXT, TOTAL_INGRESOS TEXT,ID_ENTIDAD TEXT ,ENVIADO TEXT)";
    String sqlCreateFormularioRespuesta = "CREATE TABLE  FORMULARIORESPUESTA " +
            "(ID_FORMULARIORESPUESTA TEXT,FECHA TEXT,OBSERVACIONES TEXT,ID_FORMULARIO TEXT,FECHA_SISTEMA TEXT,LATITUD TEXT,LONGITUD TEXT,ID_USUARIO TEXT,CODIGO TEXT,ENVIADO TEXT)";


    String sqlCreateFormulario = "CREATE TABLE FORMULARIO (ID_FORMULARIO TEXT, TITULO TEXT, DESCRIPCION TEXT, ID_LIDER TEXT)";



    public Sentencias(Context contexto, String nombre,
                      CursorFactory factory, int version) {

        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //	Toast toast4 = Toast.makeText(null,
        //		"Creando Tablas", Toast.LENGTH_SHORT);
        //toast4.show();
        try{
            db.execSQL(sqlCreateAgente);//crearAgente
            db.execSQL(sqlCreateUsuarios);//viviendaAfiliado
            // db.execSQL(sqlCreateUbicacionVivienda);//viviendaAfiliado
            db.execSQL(sqlCreatePreguntas);//crearAgente
            db.execSQL(sqlCreateOpciones);//viviendaAfiliado
            db.execSQL(sqlCreateRespuestas);//viviendaAfiliado
            db.execSQL(sqlCreateAfiliado);
            db.execSQL(sqlCreateFormularioRespuesta);//viviendaAfiliado
            db.execSQL(sqlCreateFormulario);
            // db.execSQL(sqlCreateAfiliado);//crearAfiliado
            // db.execSQL(sqlCreateVivienda);//crearCasa
            // db.execSQL(sqlCreateProgramaAfiliado);//programaAfiliado

            // (( db.execSQL(sqlCreateProgramas);//viviendaAfiliado

            // db.execSQL(sqlCreateVacunacion);//viviendaAfiliado
            // db.execSQL(sqlCreateVacuna);//viviendaAfiliado
            // db.execSQL(sqlCreateGestantes);//viviendaAfiliado

        }
        catch(Exception e){
            System.out.println("no se creo "+e.getMessage());
        }

    }


 /*
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
       // db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        //db.execSQL(sqlCreate);
    }
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // If you need to add a column

    }

}
