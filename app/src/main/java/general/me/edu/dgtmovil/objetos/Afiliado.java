package general.me.edu.dgtmovil.objetos;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;



public class Afiliado implements KvmSerializable {

    public String ORDEN;
    public String NOMBRE;
    public String APELLIDOS;
    public String TD;
    public String IDENTIFICACION;
    public String SEXO;
    public String RECONOCE_COMO;
    public String FN;
    public String PARENTESCO;
    public String ESTADO_CIVIL;
    public String CEDULA_CONYUGUE;
    public String NUMERO_ORDEN_CONYUGUE;
    public String ORDEN_PADRE;
    public String ARRIENDO_OTRO;
    public String DISCAPACIDAD_PERMANENTE;
    public String AFILIADO_A;
    public String EMBARAZADA_HIJOS;
    public String ASISTENCIA_CENTRO_EDUCATIVO;
    public String TIPO_ESTABLECIMIENTO;
    public String NIVEL_EDUCATIVO;
    public String PORQUE_NO_CONTINUO_ESTUDIOS;
    public String GUSTARIA_SEGUIR_ESTUDIANDO;
    public String PERCIBE_INGRESOS;
    public String TOTAL_INGRESOS;
    public String ID_ENTIDAD;


    @Override
    public Object getProperty(int arg0) {

        switch(arg0)
        {
            case 0:
                return ORDEN;
            case 1:
                return NOMBRE;
            case 2:
                return APELLIDOS;
            case 3:
                return TD;
            case 4:
                return IDENTIFICACION;
            case 5:
                return SEXO;
            case 6:
                return RECONOCE_COMO;
            case 7:
                return FN;
            case 8:
                return PARENTESCO;
            case 9:
                return ESTADO_CIVIL;
            case 10:
                return CEDULA_CONYUGUE;
            case 11:
                return NUMERO_ORDEN_CONYUGUE;
            case 12:
                return ORDEN_PADRE;
            case 13:
                return ARRIENDO_OTRO;
            case 14:
                return DISCAPACIDAD_PERMANENTE;
            case 15:
                return AFILIADO_A;
            case 16:
                return EMBARAZADA_HIJOS;
            case 17:
                return ASISTENCIA_CENTRO_EDUCATIVO;
            case 18:
                return TIPO_ESTABLECIMIENTO;
            case 19:
                return NIVEL_EDUCATIVO;
            case 20:
                return PORQUE_NO_CONTINUO_ESTUDIOS;
            case 21:
                return GUSTARIA_SEGUIR_ESTUDIANDO;
            case 22:
                return PERCIBE_INGRESOS;
            case 23:
                return TOTAL_INGRESOS;
            case 24:
                return ID_ENTIDAD;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 25;
    }




    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        switch(ind)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ORDEN";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "NOMBRE";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "APELLIDOS";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TD";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "IDENTIFICACION";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SEXO";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RECONOCE_COMO";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FN";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PARENTESCO";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ESTADO_CIVIL";
                break;
            case 10:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CEDULA_CONYUGUE";
                break;

            case 11:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "NUMERO_ORDEN_CONYUGUE";
                break;
            case 12:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ORDEN_PADRE";
                break;
            case 13:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ARRIENDO_OTRO";
                break;
            case 14:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DISCAPACIDAD_PERMANENTE";
                break;
            case 15:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "AFILIADO_A";
                break;
            case 16:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EMBARAZADA_HIJOS";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ASISTENCIA_CENTRO_EDUCATIVO";
                break;
            case 18:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TIPO_ESTABLECIMIENTO";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "NIVEL_EDUCATIVO";
                break;
            case 20:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PORQUE_NO_CONTINUO_ESTUDIOS";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "GUSTARIA_SEGUIR_ESTUDIANDO";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PERCIBE_INGRESOS";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TOTAL_INGRESOS";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_ENTIDAD";
                break;

            default:break;
        }
    }



    @Override
    public void setProperty(int ind, Object val) {
        switch(ind)
        {
            case 0:
                ORDEN = val.toString();
                break;
            case 1:
                NOMBRE = val.toString();
                break;
            case 2:
                APELLIDOS = val.toString();
                break;
            case 3:
                TD= val.toString();
                break;
            case 4:
                IDENTIFICACION =  val.toString();
                break;
            case 5:
                SEXO = val.toString();
                break;
            case 6:
                RECONOCE_COMO = val.toString();
                break;
            case 7:
                FN = val.toString();
                break;
            case 8:
                PARENTESCO = val.toString();
                break;
            case 9:
                ESTADO_CIVIL= val.toString();
                break;
            case 10:
                CEDULA_CONYUGUE =  val.toString();
                break;
            case 11:
                NUMERO_ORDEN_CONYUGUE = val.toString();
                break;
            case 12:
                ORDEN_PADRE = val.toString();
                break;
            case 13:
                ARRIENDO_OTRO = val.toString();
                break;
            case 14:
                DISCAPACIDAD_PERMANENTE = val.toString();
                break;
            case 15:
                AFILIADO_A = val.toString();
                break;
            case 16:
                EMBARAZADA_HIJOS = val.toString();
                break;
            case 17:
                ASISTENCIA_CENTRO_EDUCATIVO = val.toString();
                break;
            case 18:
                TIPO_ESTABLECIMIENTO = val.toString();
                break;
            case 19:
                NIVEL_EDUCATIVO = val.toString();
                break;
            case 20:
                PORQUE_NO_CONTINUO_ESTUDIOS = val.toString();
                break;
            case 21:
                GUSTARIA_SEGUIR_ESTUDIANDO = val.toString();
                break;
            case 22:
                PERCIBE_INGRESOS = val.toString();
                break;
            case 23:
                TOTAL_INGRESOS = val.toString();
                break;
            case 24:
                ID_ENTIDAD = val.toString();
                break;
            default:
                break;
        }
    }


}
