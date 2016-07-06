package general.me.edu.dgtmovil.datos;

/**
 * Created by Jennyfer Lopez on 17/06/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import general.me.edu.dgtmovil.objetos.Afiliado;
import general.me.edu.dgtmovil.objetos.Formulario;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;
import general.me.edu.dgtmovil.objetos.Respuesta;
import general.me.edu.dgtmovil.objetos.RespuestaReporte;
import general.me.edu.dgtmovil.objetos.Usuario;


public class GestionDatos {

    public Sentencias sentencias;
    public String placaVisualizar;
    Context context;
    public Afiliado[] listaAfiliados;

    // int idPersona;
    public String nombre;
    public String perfil;
    public String idEntidad;
    public String idPersona;

    public String correo;
    public String contrasenia;


    public GestionDatos(Context context){
        context=context;
    }







    public void crearAfiliado(Afiliado afiliado ){
        if(!existeAfiliado(afiliado.IDENTIFICACION)){
            SQLiteDatabase db = sentencias.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                String consulta = "INSERT INTO AFILIADO (ORDEN,NOMBRE,APELLIDOS,TD ,IDENTIFICACION,SEXO,RECONOCE_COMO ,FN,PARENTESCO ,ESTADO_CIVIL ,CEDULA_CONYUGUE,NUMERO_ORDEN_CONYUGUE ,"+
                        " ORDEN_PADRE ,ARRIENDO_OTRO,DISCAPACIDAD_PERMANENTE ,AFILIADO_A ,EMBARAZADA_HIJOS ,ASISTENCIA_CENTRO_EDUCATIVO ,TIPO_ESTABLECIMIENTO,"+
                        "NIVEL_EDUCATIVO,PORQUE_NO_CONTINUO_ESTUDIOS ,GUSTARIA_SEGUIR_ESTUDIANDO ,PERCIBE_INGRESOS,TOTAL_INGRESOS,ID_ENTIDAD,ENVIADO) values ('" +
                        afiliado.ORDEN + "','" + afiliado.NOMBRE + "','" + afiliado.APELLIDOS + "','" + afiliado.TD + "','" +
                        afiliado.IDENTIFICACION + "','" + afiliado.SEXO + "','" + afiliado.RECONOCE_COMO + "','" + afiliado.FN + "','" +
                        afiliado.PARENTESCO + "','" + afiliado.ESTADO_CIVIL + "','" + afiliado.CEDULA_CONYUGUE + "','" + afiliado.NUMERO_ORDEN_CONYUGUE +
                        "','" + afiliado.ORDEN_PADRE + "','" + afiliado.ARRIENDO_OTRO + "','" + afiliado.DISCAPACIDAD_PERMANENTE + "','" + afiliado.AFILIADO_A +
                        "','" + afiliado.EMBARAZADA_HIJOS + "','" + afiliado.ASISTENCIA_CENTRO_EDUCATIVO + "','" + afiliado.TIPO_ESTABLECIMIENTO + "','" +
                        afiliado.NIVEL_EDUCATIVO + "','" + afiliado.PORQUE_NO_CONTINUO_ESTUDIOS + "','" + afiliado.GUSTARIA_SEGUIR_ESTUDIANDO + "','" + afiliado.PERCIBE_INGRESOS + "','" + afiliado.TOTAL_INGRESOS + "','"+afiliado.ID_ENTIDAD+"','NO')";


                db.execSQL(consulta);
            }

            //Cerramos la base de datos
            db.close();
        }
    }




    public boolean crearPreguta(Pregunta pregunta){

        if(!existePregunta(pregunta.ORDEN,pregunta.ID_FORMULARIO)){

            SQLiteDatabase db = sentencias.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos

            if(db != null)
            {
                try{
                    db.execSQL("INSERT INTO PREGUNTAS " +
                            "(ID_PREGUNTA,CODIGO,DESCRIPCION,ESTADO,ID_GRUPO," +
                            "ORDEN,ID_TIPO_PREGUNTA,ID_FORMULARIO,OBLIGATORIA,ID_TIPODATOPREGUNTA,COLUMNA,EXIGEOBSERVACION,EXIGEHUELLA,EXIGEFOTO)"+
                            "values ('"+pregunta.ID_PREGUNTA+"','"+pregunta.CODIGO+"','"+pregunta.DESCRIPCION+"','"
                            +pregunta.ESTADO+"','"+pregunta.ID_GRUPO+"','"+pregunta.ORDEN+"','"
                            +pregunta.ID_TIPO_PREGUNTA+"','"+pregunta.ID_FORMULARIO+"','"+pregunta.OBLIGATORIA+"','"
                            +pregunta.ID_TIPODATOPREGUNTA+"','"+pregunta.COLUMNA+"','"+pregunta.EXIGEOBSERVACION+"','"+pregunta.EXIGEHUELLA+"','"+pregunta.EXIGEFOTO+"')");
                    return true;
                }
                catch (SQLException ex){
                    return false;
                }
            }
            db.close();
        }
        else{
            //  modificarPregunta(pregunta);
        }

        //Cerramos la base de datos


        return false;
    }

    public Pregunta[] listarPreguntas(String idFormulario)
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        Pregunta[] lista=null;
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery(" SELECT * FROM PREGUNTAS  " +
                        " WHERE ID_FORMULARIO='"+idFormulario+"'  ORDER BY ID_PREGUNTA", null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                lista=new Pregunta[c.getCount()];
                if (c.moveToFirst()) {

                    do {
                        Pregunta temlectura=new Pregunta();
                        temlectura.ID_PREGUNTA =c.getString(0).toString();
                        temlectura.CODIGO = c.getString(1).toString();
                        temlectura.DESCRIPCION = c.getString(2).toString();
                        temlectura.ESTADO =c.getString(3).toString();
                        temlectura.ID_GRUPO = c.getString(4).toString();
                        temlectura.ORDEN = c.getString(5).toString();
                        temlectura.ID_TIPO_PREGUNTA = c.getString(6).toString();
                        temlectura.ID_FORMULARIO = c.getString(7).toString();
                        temlectura.OBLIGATORIA = c.getString(8).toString();
                        temlectura.ID_TIPODATOPREGUNTA =c.getString(9).toString();
                        temlectura.COLUMNA = c.getString(10).toString();
                        temlectura.EXIGEOBSERVACION = c.getString(11).toString();
                        temlectura.EXIGEHUELLA =c.getString(12).toString();
                        temlectura.EXIGEFOTO = c.getString(13).toString();

                        lista[columna]=temlectura;
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return lista;
    }

    //METODO QUE RETORNA RESPUESTAS
       /*

    select datotexto, codigo_formulario, id_preguntaopcion from respuesta r inner join
     formulariorespuesta fr on (fr.id_formulario = r.id_formulariorespuesta) where id_formulario = 98;
     */
    public RespuestaReporte[] listarRespuestas()
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        RespuestaReporte[] lista=null;
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                Cursor c = db.rawQuery("Select datotexto,codigo_formulario, id_preguntaopcion, datobinario from respuesta r inner join preguntas p on (p.id_pregunta = r.id_pregunta) where ID_FORMULARIO= '98' order by CODIGO_FORMULARIO asc, ID_RESPUESTA" , null);
            //Cursor c = db.rawQuery("select datotexto, codigo_formulario, id_preguntaopcion from respuesta r inner join formulariorespuesta fr on (fr.id_formulario = r.id_formulariorespuesta) where codigo_formulario = 98" , null);

                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                lista=new RespuestaReporte[c.getCount()];
                if (c.moveToFirst()) {

                    do {
                        RespuestaReporte va=new RespuestaReporte();
                        va.setDatotexto(c.getString(0).toString());
                        va.setCodigo_formulario(c.getString(1).toString());
                        va.setId_preguntaopcion(c.getString(2).toString());
                        va.setDatobinario(c.getString(3).toString());


                        lista[columna]=va;
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return lista;
    }

    public String listarDatoTextoRespuestaPorPregunta(String codigoFormulario, String codigoPregunta)
    {
        SQLiteDatabase db = sentencias.getWritableDatabase();
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        String lista="";
        try{

            //SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                Cursor c = db.rawQuery("Select DATOTEXTO from RESPUESTA  where ID_PREGUNTA = '"+codigoPregunta+ "' and CODIGO_FORMULARIO = '"+ codigoFormulario+ "'" , null);
                //Cursor c = db.rawQuery("select datotexto, codigo_formulario, id_preguntaopcion from respuesta r inner join formulariorespuesta fr on (fr.id_formulario = r.id_formulariorespuesta) where codigo_formulario = 98" , null);

                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO



                if (c.moveToFirst()) {

                    do {
                        lista=c.getString(0);
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }
        return lista;
    }
    public String listarDatoBinarioRespuestaPorPregunta(String codigoFormulario, String codigoPregunta)
    {
        SQLiteDatabase db = sentencias.getWritableDatabase();
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        String lista="";
        try{

            //SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                Cursor c = db.rawQuery("Select DATOBINARIO from RESPUESTA  where ID_PREGUNTA = '"+codigoPregunta+ "' and CODIGO_FORMULARIO = '"+ codigoFormulario+ "'" , null);
                //Cursor c = db.rawQuery("select datotexto, codigo_formulario, id_preguntaopcion from respuesta r inner join formulariorespuesta fr on (fr.id_formulario = r.id_formulariorespuesta) where codigo_formulario = 98" , null);

                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO



                if (c.moveToFirst()) {

                    do {
                        lista=c.getString(0);
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }
        return lista;
    }


    public String[] listarFormulariosRespuestaPorFormulario(String codigoFormulario)
    {
        SQLiteDatabase db = sentencias.getWritableDatabase();
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        String[] lista=null;
        try{



            if(db!=null)
            {
                Cursor c = db.rawQuery("Select CODIGO from FORMULARIORESPUESTA where ID_FORMULARIO = '"+codigoFormulario+ "'" , null);
                //Cursor c = db.rawQuery("select datotexto, codigo_formulario, id_preguntaopcion from respuesta r inner join formulariorespuesta fr on (fr.id_formulario = r.id_formulariorespuesta) where codigo_formulario = 98" , null);

                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                lista=new String[c.getCount()];
                if (c.moveToFirst()) {

                    do {
                        lista[columna]=c.getString(0).toString();
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        finally {
            db.close();
        }
        return lista;
    }




    public Opcion[] listarOpciones(String idEntidad)
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        Opcion[] lista=null;
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery("SELECT * FROM OPCIONES WHERE ID_ENTIDAD='"+idEntidad+"' " , null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                lista=new Opcion[c.getCount()];
                if (c.moveToFirst()) {

                    do {
                        Opcion va=new Opcion();
                        va.ID=c.getString(0).toString();
                        va.CODIGO=c.getString(1).toString();
                        va.RESPUESTA=c.getString(2).toString();
                        va.COD_PREGUNTA=c.getString(3).toString();

                        lista[columna]=va;
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return lista;
    }

    public Formulario[] listarFormularios(String idEntidad)
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        Formulario[] lista=null;
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery(" SELECT * FROM FORMULARIO WHERE ID_LIDER='"+idEntidad+"' " , null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                lista=new Formulario[c.getCount()];
                if (c.moveToFirst()) {

                    do {
                        Formulario va=new Formulario();
                        va.ID_FORMULARIO=c.getString(0).toString();
                        va.TITULO=c.getString(1).toString();
                        va.DESCRIPCION=c.getString(2).toString();
                        va.ID_LIDER=c.getString(3).toString();

                        lista[columna]=va;
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return lista;
    }



    public boolean modificarPregunta(Pregunta pregunta){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            try{
                db.execSQL("UPDATE PREGUNTAS SET " +
                        " DESCRIPCION='"+pregunta.DESCRIPCION+"',ID_PREGUNTA='"+pregunta.ID_PREGUNTA+
                        "',ESTADO='"+pregunta.ESTADO+"',ID_GRUPO='"+pregunta.ID_GRUPO+"',ID_TIPO_PREGUNTA='"+pregunta.ID_TIPO_PREGUNTA+"' "+
                        " WHERE CODIGO='"+pregunta.CODIGO+"'");

                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }

    public boolean existePregunta(String codigo,String idEntidad)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT CODIGO FROM PREGUNTAS where ORDEN='"+codigo+"' AND ID_FORMULARIO='"+idEntidad+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }


    public boolean crearOpcion(Opcion opcion){

        if(!existeOpcion(opcion.ID)){

            SQLiteDatabase db = sentencias.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos

            if(db != null)
            {
                try{
                    db.execSQL("INSERT INTO OPCIONES " +
                            "(ID,CODIGO,RESPUESTA,COD_PREGUNTA,ID_ENTIDAD)"+
                            "values ('"+opcion.ID+"','"+opcion.CODIGO+"','"+opcion.RESPUESTA+"','"
                            +opcion.COD_PREGUNTA+"','"+opcion.ID_ENTIDAD+"')");
                    return true;
                }
                catch (SQLException ex){
                    return false;
                }
            }
            db.close();
        }
        else{
            modificarOpcion(opcion);
        }

        //Cerramos la base de datos


        return false;
    }


    public boolean eliminarOpciones(){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos

        if(db != null)
        {
            try{
                db.execSQL("DELETE FROM OPCIONES ");

                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }
        db.close();


        //Cerramos la base de datos


        return false;
    }
    public boolean modificarOpcion(Opcion opcion){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            try{
                db.execSQL("UPDATE OPCIONES SET " +
                        " RESPUESTA='"+opcion.RESPUESTA+"'"+
                        " WHERE ID='"+opcion.ID+"'");

                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }

    public boolean existeOpcion(String codigo)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT CODIGO FROM OPCIONES where ID='"+codigo+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }




    public boolean crearRespuesta(Respuesta respuesta){

        if(!existeRespuesta(respuesta.ID_PREGUNTA,respuesta.CODIGO_FORMULARIO)){

            SQLiteDatabase db = sentencias.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            int idRespuesta=getIdMaxRespuesta();
            if(db != null)
            {
                try{
                    db.execSQL("INSERT INTO RESPUESTA " +
                            " (ID_RESPUESTA,ID_PREGUNTA,ID_PREGUNTAOPCION,DATOTEXTO,DATOHUELLA,DATOBINARIO,ID_FORMULARIORESPUESTA,OBSERVACION,CODIGO_FORMULARIO) "+
                            "values ('"+idRespuesta+"','"+respuesta.ID_PREGUNTA+"','"+respuesta.ID_PREGUNTAOPCION+"','"
                            +respuesta.DATOTEXTO+"','"+respuesta.DATOHUELLA+"','"+respuesta.DATOBINARIO+"','"+respuesta.ID_FORMULARIORESPUESTA+"','"+respuesta.OBSERVACION+"','"+respuesta.CODIGO_FORMULARIO+"')");
                    return true;
                }
                catch (SQLException ex){
                    return false;
                }
            }
            db.close();
        }
        else{
            modificarRespuesta(respuesta);
        }

        //Cerramos la base de datos


        return false;
    }



    public boolean modificarRespuesta(Respuesta respuesta){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos

       /*
        if(db != null)
        {
            try{
                db.execSQL("UPDATE RESPUESTA SET " +
                        " COD_RESPUESTA='"+respuesta.COD_RESPUESTA+"',DATO='"+respuesta.DATO+
                        "'"+
                        " WHERE COD_PREGUNTA='"+respuesta.COD_PREGUNTA+"' and CODIGO_ENCUENTA='"+respuesta.CODIGO_ENCUENTA+"'");

                return true;
            }
            catch (SQLException ex){
                return false;
            }


        }



        //Cerramos la base de datos
        db.close();
*/
        return false;
    }

    public boolean existeRespuesta(String codigoP,String encuesta)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID_RESPUESTA FROM RESPUESTA where ID_PREGUNTA='"+codigoP+"' and CODIGO_FORMULARIO='"+encuesta+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }


    public boolean crearFormularioRespuesta(FormularioRespuesta formulario){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        int idFormularioRespuesta=getIdMaxFormularioRespuesta();
        if(db != null)
        {
            try{
                db.execSQL("INSERT INTO FORMULARIORESPUESTA " +
                        "(ID_FORMULARIORESPUESTA,FECHA,OBSERVACIONES,ID_FORMULARIO,FECHA_SISTEMA,LATITUD,LONGITUD,ID_USUARIO,CODIGO,ENVIADO)"+
                        " values ("+idFormularioRespuesta+", '"+formulario.FECHA+"','"+formulario.OBSERVACIONES+"','"+formulario.ID_FORMULARIO+"','"
                        +formulario.FECHA_SISTEMA+"','"+formulario.LATITUD+"','"+formulario.LONGITUD+"','"
                        +formulario.ID_USUARIO+"','"+formulario.CODIGO+"','NO')");
                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }

    public boolean modificarFomularioRespuesta(FormularioRespuesta formulario){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos

        /*
        if(db != null)
        {
            try{
                db.execSQL("UPDATE UBICACION_VIVIENDA SET " +
                        " ID_DEPARTAMENTO='"+ubi.ID_DEPARTAMENTO+"',ID_MUNICIPIO='"+ubi.ID_MUNICIPIO+
                        "',ID_ZONA='"+ubi.ID_ZONA+"',ID_BARRIO='"+ubi.ID_BARRIO+"',MANZANA='"+ubi.MANZANA+"',ESTRATO='"+
                        ubi.ESTRATO+"',DIRECCION='"+ubi.DIRECCION+"',VEREDA='"+ubi.VEREDA+"',TELEFONO='"
                        +ubi.TELEFONO+"',NO_FICHA='"+ubi.NO_FICHA+"',FORMULARIO='"+ubi.FORMULARIO+"',FECHA='"+
                        ubi.FECHA+"',ID_ENTIDAD='"+ubi.ID_ENTIDAD+
                        "',ID_USUARIO='"+ubi.ID_USUARIO+"',CODIGO='"+ubi.IDENTIFICADOR+"',X='"+ubi.X+
                        "',Y='"+ubi.Y+"',OBSERVACION='"+ubi.OBSERVACION+"' WHERE CODIGO='"+ubi.IDENTIFICADOR+"'");

                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

*/
        return false;
    }

    public boolean crearViviendaAfiliado(String encuesta,String identificacion){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        //int idViviendaAfiliado=getIdMaxVA();
        if(db != null)
        {
            try{

                db.execSQL("INSERT INTO VIVIENDA_AFILIADO" +
                        "(CODIGO_ENCUENTA,IDENTIFICACION)"+
                        "values ('"+encuesta+"', '"+identificacion+"')");
                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }

    public boolean agregarColumnas(){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        int idViviendaAfiliado=getIdMaxVA();
        if(db != null)
        {
            try{
                //  db.execSQL("DELETE FROM VIVIENDA_AFILIADO");
                // db.execSQL("DELETE FROM VIVIENDA");
                // db.execSQL("DELETE FROM UBICACION_VIVIENDA");

                // db.execSQL("ALTER TABLE VIVIENDA_AFILIADO ADD COLUMN EMBARAZO TEXT");
                // db.execSQL("ALTER TABLE VIVIENDA_AFILIADO ADD COLUMN ETNIA TEXT");
                // db.execSQL("ALTER TABLE VIVIENDA_AFILIADO ADD COLUMN CONDICION TEXT");


                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }

    public boolean eliminarViviendaAfiliado(String encuesta,String identificacion){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        int idViviendaAfiliado=getIdMaxVA();
        if(db != null)
        {
            try{

                db.execSQL("DELETE FROM  VIVIENDA_AFILIADO" +
                        " WHERE CODIGO_ENCUENTA='"+ encuesta+"' and IDENTIFICACION='"+identificacion+"'");
                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }


    public boolean crearAfiliadoPrograma(String idAfiliado,String idPrograma,String idVivienda){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        int idProgramaAfiliado=getIdMaxAP();
        if(db != null)
        {
            try{

                db.execSQL("INSERT INTO PROGRAMA_AFILIADO" +
                        "(ID,ID_AFILIADO,ID_PROGRAMA,ID_VIVIENDA)"+
                        "values ("+idProgramaAfiliado+", '"+idAfiliado+"','"+idPrograma+"','"+idVivienda+"')");
                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }


    public boolean eliminarAfiliadoPrograma(String idAfiliado,String idPrograma){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        int idProgramaAfiliado=getIdMaxAP();
        if(db != null)
        {
            try{

                db.execSQL("DELETE FROM PROGRAMA_AFILIADO" +
                        " WHERE ID_AFILIADO='"+idAfiliado+"' AND ID_PROGRAMA='"+idPrograma+"'");
                return true;
            }
            catch (SQLException ex){
                return false;
            }
        }



        //Cerramos la base de datos
        db.close();

        return false;
    }


    public boolean crearPrograma(String idP,String nombre){

        if(!existePrograma(idP)){
            SQLiteDatabase db = sentencias.getWritableDatabase();
            //Si hemos abierto correctamente la base de datos
            if(db != null)
            {
                try{

                    db.execSQL("INSERT INTO PROGRAMA" +
                            "(ID,NOMBRE)"+
                            "values ("+idP+", '"+nombre+"')");
                    return true;
                }
                catch (SQLException ex){
                    return false;
                }
            }



            //Cerramos la base de datos
            db.close();
        }
        return false;
    }


    public void editarEnviadoAfiliado(String cedula){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            db.execSQL("UPDATE AFILIADO SET ENVIADO='SI' WHERE IDENTIFICACION='"+cedula+"'");
        }

        //Cerramos la base de datos
        db.close();
    }


    public void editarEnviadoFormularioRespuesta(String codigo ,String enviado){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            db.execSQL("UPDATE FORMULARIORESPUESTA SET ENVIADO='"+enviado+"' WHERE CODIGO='"+codigo+"'");
        }

        //Cerramos la base de datos
        db.close();
    }

    public void editarUbicacionObservacion(String codigoVivienda ,String observacion,String fecha){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            db.execSQL("UPDATE UBICACION_VIVIENDA SET OBSERVACION='"+observacion+"', FECHA='"+fecha+"'  WHERE CODIGO='"+codigoVivienda+"'");
        }

        //Cerramos la base de datos
        db.close();
    }

    public boolean existeAfiliado(String codigo)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT IDENTIFICACION FROM AFILIADO where IDENTIFICACION='"+codigo+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }

    public boolean existeFormularioRespuesta(FormularioRespuesta formulario)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID_FORMULARIORESPUESTA FROM FORMULARIORESPUESTA where CODIGO='"+formulario.CODIGO+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }





    public boolean envioFormularioRespuesta(FormularioRespuesta formularioRespuesta)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID_FORMULARIORESPUESTA FROM FORMULARIORESPUESTA where CODIGO='"+formularioRespuesta.CODIGO+"' AND ENVIADO='SI'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }
    public boolean existePrograma(String id)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID FROM PROGRAMA where ID="+id+"", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }

    public boolean existeProgramaAfiliado(String idAfiliado,String idPrograma)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID FROM PROGRAMA_AFILIADO where ID_AFILIADO='"+idAfiliado+"' and ID_PROGRAMA='"+idPrograma+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }

            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }

    public boolean existeViviendaAfiliadoAux(String encuesta,String identificacion)
    {
        int pos=0;
        int columna=0;
        boolean res=false;
        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT IDENTIFICACION FROM VIVIENDA_AFILIADO where CODIGO_ENCUENTA='"+encuesta+"' and IDENTIFICACION='"+identificacion+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                res=true;
            }



            db.close();
        }
        catch(Exception exp)
        {
            res=false;
        }
        return res;

    }

    public boolean existeVivienda(String codigo)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT CODIGO FROM VIVIENDA where CODIGO='"+codigo+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }
    public FormularioRespuesta getFormularioRespuesta(String codigo)
    {
        int pos=0;
        int columna=0;
        FormularioRespuesta formulario;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT * FROM FORMULARIORESPUESTA where CODIGO='"+codigo+"'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            formulario=new FormularioRespuesta();
            if (c.moveToFirst()) {
                do {
                    formulario.ID_FORMULARIORESPUESTA =c.getString(0);
                    formulario.FECHA =c.getString(1);
                    formulario.OBSERVACIONES =c.getString(2);
                    formulario.ID_FORMULARIO =c.getString(3);
                    formulario.FECHA_SISTEMA =c.getString(4);
                    formulario.LATITUD =c.getString(5);
                    formulario.LONGITUD =c.getString(6);
                    formulario.ID_USUARIO =c.getString(7);
                    formulario.RESPUESTAS =c.getString(8);
                    formulario.CODIGO =c.getString(9);
                    formulario.RESPUESTAS=getRespuestas(formulario.CODIGO);
                } while(c.moveToNext());



            }
            db.close();
        }
        catch(Exception exp)
        {
            return null;
        }

        return  formulario;
    }

    public void crearUsuario(Usuario usuario ){

        SQLiteDatabase db ;
        //Si hemos abierto correctamente la base de datos

        try{

            if(!existeUsuario(usuario.LOGIN)){

                db = sentencias.getWritableDatabase();
                if(db!=null){
                    String sql="INSERT INTO CEN_USUARIOS (ID,NOMBRE,APELLIDO,CEDULA,LOGIN,PASSWORD,ID_PERFIL,ID_PERSONA,ID_ENTIDAD) values ("+usuario.ID+", '"+usuario.NOMBRE+"','"+usuario.APELLIDO+"','"+usuario.CEDULA+"','"+usuario.LOGIN+"','"+usuario.PASSWORD+"','"+usuario.ID_PERFIL+"','"+usuario.ID_PERSONA+"','"+usuario.ID_ENTIDAD+"')";
                    db.execSQL(sql);
                }
            }
            else{
                db = sentencias.getWritableDatabase();
                if(db!=null){
                    String sql="UPDATE CEN_USUARIOS  SET ID='"+usuario.ID+"',NOMBRE='"+usuario.NOMBRE+"',APELLIDO='"+usuario.APELLIDO+"',CEDULA='"+usuario.APELLIDO+"',PASSWORD='"+usuario.PASSWORD+"',ID_PERFIL='"+usuario.ID_PERFIL+"',ID_PERSONA='"+usuario.ID_PERSONA+"',ID_ENTIDAD='"+usuario.ID_ENTIDAD+"' WHERE LOGIN='"+usuario.LOGIN+"'";
                    db.execSQL(sql);
                }
            }

            db.close();
        }


        catch (Exception e) {

        }
    }


    public void crearFormulario(Formulario usuario){

        SQLiteDatabase db ;
        //Si hemos abierto correctamente la base de datos

        try{

            if(!existeFormulario(usuario.ID_FORMULARIO)){

                db = sentencias.getWritableDatabase();
                if(db!=null){
                    String sql="INSERT INTO FORMULARIO (ID_FORMULARIO, TITULO, DESCRIPCION, ID_LIDER) values ("+usuario.ID_FORMULARIO+", '"+usuario.TITULO+"','"+usuario.DESCRIPCION+"','"+usuario.ID_LIDER+"')";
                    db.execSQL(sql);
                }
            }
            else{
                db = sentencias.getWritableDatabase();
                if(db!=null){
                    String sql="UPDATE FORMULARIO  SET TITULO='"+usuario.TITULO+"',DESCRIPCION='"+usuario.DESCRIPCION+"',ID_LIDER='"+usuario.ID_LIDER+"' WHERE LOGIN='"+usuario.ID_FORMULARIO+"'";
                    db.execSQL(sql);
                }
            }

            db.close();
        }


        catch (Exception e) {

        }
    }

    public boolean existeUsuario(String login)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT ID from CEN_USUARIOS WHERE LOGIN='"+login+"'",null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }

    public boolean existeFormulario(String login)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT ID_FORMULARIO from FORMULARIO WHERE ID_FORMULARIO='"+login+"'",null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }


    public FormularioRespuesta[] getFormulariosRespuestaNoEnviada()
    {
        int pos=0;
        int columna=0;
        FormularioRespuesta formulario;
        FormularioRespuesta[] lista;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT * FROM FORMULARIORESPUESTA where ENVIADO='NO'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            lista=new FormularioRespuesta[c.getCount()];
            if (c.moveToFirst()) {
                do {

                    formulario=new FormularioRespuesta();
                    formulario.ID_FORMULARIORESPUESTA =c.getString(0);
                    formulario.FECHA =c.getString(1);
                    formulario.OBSERVACIONES =c.getString(2);
                    formulario.ID_FORMULARIO =c.getString(3);
                    formulario.FECHA_SISTEMA =c.getString(4);
                    formulario.LATITUD =c.getString(5);
                    formulario.LONGITUD =c.getString(6);
                    formulario.ID_USUARIO =c.getString(7);
                    formulario.CODIGO =c.getString(8);
                    formulario.RESPUESTAS=getRespuestas(formulario.CODIGO);
                    lista[pos]=formulario;
                    pos=pos+1;


                } while(c.moveToNext());



            }
            db.close();
        }
        catch(Exception exp)
        {
            return null;
        }

        return  lista;
    }



    public String getRespuestas(String CODIGO_ENCUENTA)
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        Opcion[] lista=null;
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery(" SELECT * FROM RESPUESTA  WHERE CODIGO_FORMULARIO='"+CODIGO_ENCUENTA+"'" , null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                if (c.moveToFirst()) {

                    do {
                        Respuesta res=new Respuesta();
                        res.ID_RESPUESTA =c.getString(0).toString();
                        res.ID_PREGUNTA=c.getString(1).toString();
                        res.ID_PREGUNTAOPCION =c.getString(2).toString();
                        res.DATOTEXTO =c.getString(3).toString();
                        res.DATOHUELLA=c.getString(4).toString();
                        res.DATOBINARIO =c.getString(5).toString();
                        res.ID_FORMULARIORESPUESTA =c.getString(6).toString();
                        res.OBSERVACION=c.getString(7).toString();
                        res.CODIGO_FORMULARIO=c.getString(8).toString();

                        fila=fila+res.ID_RESPUESTA+"'"+res.ID_PREGUNTA+"'"+res.ID_PREGUNTAOPCION+"'"+res.DATOTEXTO+"'"+
                                res.DATOHUELLA+"'"+res.DATOBINARIO+"'"+res.ID_FORMULARIORESPUESTA+"'"+res.OBSERVACION+"'"+res.CODIGO_FORMULARIO+";";



                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return fila;
    }


    public boolean envioVivienda(String codigo)
    {
        int pos=0;
        int columna=0;

        try{
            SQLiteDatabase db = sentencias.getWritableDatabase();
            Cursor c = db.rawQuery(" SELECT CODIGO FROM VIVIENDA where CODIGO='"+codigo+"' and ENVIADO='SI'", null);
            //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
            if(c.getCount()>0){
                return true;
            }
            db.close();
        }
        catch(Exception exp)
        {
            return false;
        }
        return false;
    }

    public Afiliado[] obtenerPersonas(String identificacion)
    {

        String[] datosLecturas=null;
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                if(!identificacion.equals("")){
                    aux=" where IDENTIFICACION='"+identificacion+"' order by IDENTIFICACION asc";
                }
                else{
                    aux=" order by IDENTIFICACION asc";
                }

                Cursor c = db.rawQuery(" SELECT * FROM AFILIADO "+aux, null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
                datosLecturas=new String[c.getCount()];
                listaAfiliados=new Afiliado[c.getCount()];
                if (c.moveToFirst()) {
                    do {
                        Afiliado afil=new Afiliado();


                        afil.ORDEN=c.getString(0);
                        afil.NOMBRE=c.getString(1);
                        afil.APELLIDOS=c.getString(2);
                        afil.TD=c.getString(3);
                        afil.IDENTIFICACION=c.getString(4);
                        afil.SEXO=c.getString(5);
                        afil.RECONOCE_COMO=c.getString(6);
                        afil.FN=c.getString(7);
                        afil.PARENTESCO=c.getString(8);
                        afil.ESTADO_CIVIL=c.getString(9);
                        afil.CEDULA_CONYUGUE=c.getString(10);
                        afil.NUMERO_ORDEN_CONYUGUE=c.getString(11);
                        afil.ORDEN_PADRE=c.getString(12);
                        afil.ARRIENDO_OTRO=c.getString(13);
                        afil.DISCAPACIDAD_PERMANENTE=c.getString(14);
                        afil.AFILIADO_A=c.getString(15);
                        afil.EMBARAZADA_HIJOS=c.getString(16);
                        afil.ASISTENCIA_CENTRO_EDUCATIVO=c.getString(17);
                        afil.TIPO_ESTABLECIMIENTO=c.getString(18);
                        afil.NIVEL_EDUCATIVO=c.getString(19);
                        afil.PORQUE_NO_CONTINUO_ESTUDIOS=c.getString(20);
                        afil.GUSTARIA_SEGUIR_ESTUDIANDO=c.getString(21);
                        afil.PERCIBE_INGRESOS=c.getString(22);
                        afil.TOTAL_INGRESOS=c.getString(23);
                        afil.ID_ENTIDAD=c.getString(24);




                        datosLecturas[columna]=fila;
                        listaAfiliados[columna]=afil;
                        fila="";
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return listaAfiliados;
    }


    public Afiliado[] obtenerAfiliadosNuevos()
    {

        String[] datosLecturas=null;
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {


                Cursor c = db.rawQuery(" SELECT * FROM AFILIADO WHERE ENVIADO='NO'", null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
                datosLecturas=new String[c.getCount()];
                listaAfiliados=new Afiliado[c.getCount()];
                if (c.moveToFirst()) {
                    do {
                        Afiliado afil=new Afiliado();


                        afil.ORDEN=c.getString(0);
                        afil.NOMBRE=c.getString(1);
                        afil.APELLIDOS=c.getString(2);
                        afil.TD=c.getString(3);
                        afil.IDENTIFICACION=c.getString(4);
                        afil.SEXO=c.getString(5);
                        afil.RECONOCE_COMO=c.getString(6);
                        afil.FN=c.getString(7);
                        afil.PARENTESCO=c.getString(8);
                        afil.ESTADO_CIVIL=c.getString(9);
                        afil.CEDULA_CONYUGUE=c.getString(10);
                        afil.NUMERO_ORDEN_CONYUGUE=c.getString(11);
                        afil.ORDEN_PADRE=c.getString(12);
                        afil.ARRIENDO_OTRO=c.getString(13);
                        afil.DISCAPACIDAD_PERMANENTE=c.getString(14);
                        afil.AFILIADO_A=c.getString(15);
                        afil.EMBARAZADA_HIJOS=c.getString(16);
                        afil.ASISTENCIA_CENTRO_EDUCATIVO=c.getString(17);
                        afil.TIPO_ESTABLECIMIENTO=c.getString(18);
                        afil.NIVEL_EDUCATIVO=c.getString(19);
                        afil.PORQUE_NO_CONTINUO_ESTUDIOS=c.getString(20);
                        afil.GUSTARIA_SEGUIR_ESTUDIANDO=c.getString(21);
                        afil.PERCIBE_INGRESOS=c.getString(22);
                        afil.TOTAL_INGRESOS=c.getString(23);
                        afil.ID_ENTIDAD=c.getString(24);
                        datosLecturas[columna]=fila;
                        listaAfiliados[columna]=afil;
                        fila="";
                        columna++;


                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return listaAfiliados;
    }


    public Afiliado[] obtenerPersonasVivienda(String encuesta)
    {


        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                if(!encuesta.equals("")){
                    aux=" where VIVIENDA_AFILIADO.CODIGO_ENCUENTA='"+encuesta+"' order by VIVIENDA_AFILIADO.CODIGO_ENCUENTA asc";
                }
                else{
                    aux=" order by IDENTIFICACION asc";
                }

                String sql=" SELECT AFILIADO.* FROM AFILIADO  " +
                        "INNER JOIN VIVIENDA_AFILIADO ON (AFILIADO.IDENTIFICACION=" +
                        "VIVIENDA_AFILIADO.IDENTIFICACION) "+aux;
                Cursor c = db.rawQuery(sql, null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                listaAfiliados=new Afiliado[c.getCount()];
                if (c.moveToFirst()) {
                    do {
                        Afiliado afil=new Afiliado();


                        afil.ORDEN=c.getString(0);
                        afil.NOMBRE=c.getString(1);
                        afil.APELLIDOS=c.getString(2);
                        afil.TD=c.getString(3);
                        afil.IDENTIFICACION=c.getString(4);
                        afil.SEXO=c.getString(5);
                        afil.RECONOCE_COMO=c.getString(6);
                        afil.FN=c.getString(7);
                        afil.PARENTESCO=c.getString(8);
                        afil.ESTADO_CIVIL=c.getString(9);
                        afil.CEDULA_CONYUGUE=c.getString(10);
                        afil.NUMERO_ORDEN_CONYUGUE=c.getString(11);
                        afil.ORDEN_PADRE=c.getString(12);
                        afil.ARRIENDO_OTRO=c.getString(13);
                        afil.DISCAPACIDAD_PERMANENTE=c.getString(14);
                        afil.AFILIADO_A=c.getString(15);
                        afil.EMBARAZADA_HIJOS=c.getString(16);
                        afil.ASISTENCIA_CENTRO_EDUCATIVO=c.getString(17);
                        afil.TIPO_ESTABLECIMIENTO=c.getString(18);
                        afil.NIVEL_EDUCATIVO=c.getString(19);
                        afil.PORQUE_NO_CONTINUO_ESTUDIOS=c.getString(20);
                        afil.GUSTARIA_SEGUIR_ESTUDIANDO=c.getString(21);
                        afil.PERCIBE_INGRESOS=c.getString(22);
                        afil.TOTAL_INGRESOS=c.getString(23);
                        afil.ID_ENTIDAD=c.getString(24);

                        listaAfiliados[columna]=afil;
                        fila="";
                        columna++;
                        //	pos=pos+11;

                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return listaAfiliados;
    }

    /*
        public ViviendaAfiliado[] obtenerPersonasViviendaObjetos(String encuesta)
        {

            int pos=0;
            int columna=0;
            String fila="";
            String aux="";
            ViviendaAfiliado[] lista=null;
            try{

                SQLiteDatabase db = sentencias.getWritableDatabase();

                if(db!=null)
                {
                    if(!encuesta.equals("")){
                        aux=" where VIVIENDA_AFILIADO.CODIGO_ENCUENTA='"+encuesta+"' order by VIVIENDA_AFILIADO.CODIGO_ENCUENTA asc";
                    }
                    else{
                        aux=" order by IDENTIFICACION asc";
                    }
                    Cursor c = db.rawQuery(" SELECT VIVIENDA_AFILIADO.* FROM AFILIADO  " +
                            "INNER JOIN VIVIENDA_AFILIADO ON (AFILIADO.IDENTIFICACION=" +
                            "VIVIENDA_AFILIADO.IDENTIFICACION) "+aux, null);
                    //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO


                    lista=new ViviendaAfiliado[c.getCount()];
                    if (c.moveToFirst()) {

                        do {
                            ViviendaAfiliado va=new ViviendaAfiliado();
                            va.CODIGO_ENCUENTA=encuesta;
                            va.IDENTIFICACION=c.getString(2).toString();

                            lista[columna]=va;
                            columna++;


                        } while(c.moveToNext());

                    }
                    db.close();


                }



            }catch(Exception exp)
            {
                exp.printStackTrace();
                return null;
            }
            return lista;
        }


    */
    public List<String> obtenerDirecciones()
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";

        List<String> list = new ArrayList<String>();
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery(" SELECT UBICACION_VIVIENDA.DIRECCION,UBICACION_VIVIENDA.FECHA FROM UBICACION_VIVIENDA  " +
                        " ORDER BY UBICACION_VIVIENDA.FECHA DESC ", null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO

                list.add(" ");
                if (c.moveToFirst()) {
                    do {
                        if(c.getString(1)!=null){

                            list.add(c.getString(0));
                        }

                    } while(c.moveToNext());

                }
                db.close();

            }

        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return list;
    }

    public void mostrarMensaje(String mensaje,int tiempo){
        Toast toast4 = Toast.makeText(context,
                mensaje, tiempo);

        toast4.show();
    }

    public String[] obtenerLecturas(String codigo)
    {

        String[] datosLecturas=null;
        int pos=0;
        int columna=0;
        String fila="";
        String aux="";
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {
                if(!codigo.equals("")){
                    aux=" where codigo="+codigo+" order by codigo asc";
                }
                else{
                    aux=" order by codigo asc";
                }
                Cursor c = db.rawQuery(" SELECT ID,FECHA,HORA,RUTA,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO,LECTURA FROM LECTURAS "+aux, null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO
                datosLecturas=new String[c.getCount()];
                if (c.moveToFirst()) {
                    do {
                        for(int i=pos;i<17+pos;i++){
                            fila=fila+ c.getString(i-pos)+" ** ";
                        }
                        datosLecturas[columna]=fila;
                        fila="";
                        columna++;
                        pos=pos+17;

                    } while(c.moveToNext());

                }
                db.close();


            }



        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return datosLecturas;
    }

    public void editarLectura(String codigo,String fecha,String hora,String x,String y,String lect,String enviado){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            db.execSQL("UPDATE LECTURAS SET FECHA='"+fecha+"', HORA='"+hora+
                    "',X='"+x+"',Y='"+y+"',LECTURA='"+lect+"', ESTADO='G',ENVIADO='"+enviado+"' WHERE CODIGO='"+codigo+"'");
        }

        //Cerramos la base de datos
        db.close();
    }

    public int getIdMax21112() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID_FORMULARIORESPUESTA) FROM FORMULARIORESPUESTA",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }

    public int getIdMaxRespuesta() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID_RESPUESTA) FROM RESPUESTA",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }

    public int getIdMaxVA() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID) FROM VIVIENDA_AFILIADO",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }


    public int getIdMaxAFiliado() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM AFILIADO",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }


    public int getIdMaxFormularioRespuesta() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID_FORMULARIORESPUESTA) FROM FORMULARIORESPUESTA",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }

    public int getIdMaxAP() {
        int idMax=0;
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID) FROM PROGRAMA_AFILIADO",null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idMax= c.getInt(0);
            } while(c.moveToNext());
            return idMax+1;
        }
        db.close();
        return 1;
    }

    public int contarUbicacionesNoEnviadas()
    {
        SQLiteDatabase db = sentencias.getWritableDatabase();
        Cursor c = db.rawQuery(" SELECT COUNT(ID_FORMULARIORESPUESTA) FROM FORMULARIORESPUESTA WHERE ENVIADO='NO'",null);


        int idRes=0;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idRes=c.getInt(0);

            } while(c.moveToNext());
            return idRes;
        }

        db.close();
        return 0;
    }

    public int consultarAgente(String login,String password){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        String[] args = new String[] {login,password};
        Cursor c = db.rawQuery(" SELECT id,nombre,apellido,placa FROM agente WHERE login=? and password=? ", args);
        int id=0;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                id=c.getInt(0);
                String nombre= c.getString(1);
                String apellido = c.getString(2);
            } while(c.moveToNext());
            return id;
        }
        db.close();
        return 0;
    }

    public int existeComparendo(String id){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        String[] args = new String[] {id};
        Cursor c = db.rawQuery(" SELECT id, placa from comparendo WHERE id=?", args);


        int idRes=0;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idRes=c.getInt(0);
                placaVisualizar= c.getString(1);
            } while(c.moveToNext());
            return idRes;
        }

        db.close();
        return 0;
    }

    public void editarComparendoEnviado(int id){

        SQLiteDatabase db = sentencias.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            db.execSQL("UPDATE comparendo set enviado='SI' where id = " + String.valueOf(id));

        }
        //Cerramos la base de datos
        db.close();
    }
    private byte[] obtenerFirmaComparendo(int id)
    {
        byte[] firma = null;
        File carpetafotos = new File(Environment.getExternalStorageDirectory() , "Malparqueo");


        String inicio = String.valueOf(id) + "_firma";

        String fin=".jpg";
        File[] archivosfotos = carpetafotos.listFiles(new Filtro(inicio,fin));

        if(archivosfotos!=null && archivosfotos.length>0)
        {
            File tempf = archivosfotos[0];
            if(tempf.exists())
            {
                firma = convertirbytes(tempf);
            }

        }

        return firma;
    }

    private List<byte[]> obtenerFotosComparendo(int id)
    {
        List<byte[]> fotos = null;
        File carpetafotos = new File(Environment.getExternalStorageDirectory() , "Malparqueo");


        String inicio = String.valueOf(id) + "_foto_";

        String fin=".jpg";
        File[] archivosfotos = carpetafotos.listFiles(new Filtro(inicio,fin));

        if(archivosfotos!=null && archivosfotos.length>0)
        {
            fotos = new ArrayList<byte[]>();
            for(int i=0;i<archivosfotos.length;i++)
            {

                File tempf = archivosfotos[i];
                if(tempf.exists())
                {
                    //byte[] temp = convertirarchivoByteArray(tempf.getPath());
                    byte[] temp = convertirbytes(tempf);
                    if(temp!=null && temp.length>0)
                        fotos.add(temp);
                }
            }

        }

        return fotos;
    }

    private class Filtro implements FilenameFilter {

        String inicio,fin;
        public Filtro(String start,String end)
        { this.inicio = start; this.fin = end; }
        @Override
        public boolean accept(File directorio, String nombreArchivo) {

            if(nombreArchivo.indexOf(this.inicio)!=-1 && nombreArchivo.endsWith(this.fin))
                return true;

            return false;
        }
    }

    public byte[] convertirbytes(File archivo)
    {
        //File file = new File(ruta);
        File file = archivo;
        FileInputStream fileInputStream=null;
        byte[] b = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char)b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
            b=null;
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
            b=null;
        }
        finally
        {
            if(fileInputStream!=null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

        return b;

    }

    private byte[] convertirarchivoByteArray(String nombre) {
        InputStream input;
        byte[] myArchivo = null;
        byte[] tmp = new byte[1000];
        int leidos;
        try {

            //System.out.println("nombre: " + nombre);
            input = new FileInputStream(nombre);
            leidos = input.read(tmp);
            while (leidos >= 0) {
                myArchivo = unirArrayBytes(myArchivo, tmp, leidos);
                tmp = new byte[1000];
                leidos = input.read(tmp);
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myArchivo;
    }

    private byte[] unirArrayBytes(byte[] principal, byte[] agregar, int nuevos) {
        if (nuevos > 0) {
            byte[] tmp;
            if (principal == null || principal.length == 0)
                return agregar;
            tmp = new byte[principal.length + nuevos];
            for (int i = 0; i < principal.length; i++) {
                tmp[i] = principal[i];
            }
            for (int i = principal.length; i < principal.length + nuevos; i++) {
                tmp[i] = agregar[i - principal.length];
            }
            return tmp;
        }
        return principal;
    }

    public String consultarUsuario(String login,String password){
        SQLiteDatabase db = sentencias.getWritableDatabase();
        //	String[] args = new String[] {login,password};
        String[] args = new String[] {login};
        //	Cursor c = db.rawQuery(" SELECT NOMBRE,APELLIDO,ID_PERFIL,ID,ID_ENTIDAD FROM CEN_USUARIOS WHERE LOGIN=? and PASSWORD=? ", args);
        Cursor c = db.rawQuery("SELECT ID, NOMBRE, APELLIDO,ID_PERFIL,ID_ENTIDAD, PASSWORD FROM CEN_USUARIOS WHERE LOGIN=?", args);
        String idUsuario="";
        int id=0;
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                idUsuario= c.getString(0);
                nombre = c.getString(1)+" "+c.getString(2);
                perfil = c.getString(2);
                idEntidad=c.getString(4);
                contrasenia = c.getString(5);
            } while(c.moveToNext());
            return idUsuario;
        }
        db.close();
        return "0";
    }

    public List<String> obtenerViviendas(String idEntidad)
    {

        int pos=0;
        int columna=0;
        String fila="";
        String aux="";

        List<String> list = new ArrayList<String>();
        try{

            SQLiteDatabase db = sentencias.getWritableDatabase();

            if(db!=null)
            {

                Cursor c = db.rawQuery(" SELECT UBICACION_VIVIENDA.CODIGO,UBICACION_VIVIENDA.FECHA FROM UBICACION_VIVIENDA  WHERE ID_ENTIDAD='"+idEntidad+"' " +
                        " ORDER BY UBICACION_VIVIENDA.FECHA DESC ", null);
                //,USO_ESTRATO,NOMBRE,DIRECCION_BARRIO,CODIGO,MEDIDOR,ULT_LECTURA,PROMEDIO,DIGITOS,X,Y,ENVIADO,ESTADO

                list.add(" ");

                if (c.moveToFirst()) {
                    do {

                        if(c.getString(1)!=null )
                            list.add(c.getString(0)+". "+c.getString(1));

                    } while(c.moveToNext());

                }
                db.close();

            }

        }catch(Exception exp)
        {
            exp.printStackTrace();
            return null;
        }
        return list;
    }
}
