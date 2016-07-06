package general.me.edu.dgtmovil.objetos;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by Pedro on 23/06/2016.
 */

public class FormularioRespuesta implements KvmSerializable {


    public String ID_FORMULARIORESPUESTA;
    public String FECHA ;
    public String OBSERVACIONES ;
    public String ID_FORMULARIO ;
    public String FECHA_SISTEMA ;
    public String LATITUD;
    public String LONGITUD ;
    public String ID_USUARIO ;
    public String RESPUESTAS ;
    public String CODIGO ;



    @Override
    public Object getProperty(int arg0) {

        switch(arg0)
        {
            case 0:
                return ID_FORMULARIORESPUESTA;
            case 1:
                return FECHA;
            case 2:
                return OBSERVACIONES;
            case 3:
                return ID_FORMULARIO;
            case 4:
                return FECHA_SISTEMA;
            case 5:
                return LATITUD;
            case 6:
                return LONGITUD;
            case 7:
                return ID_USUARIO;
            case 8:
                return RESPUESTAS;
            case 9:
                return CODIGO;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 10;
    }

    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        switch(ind)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_FORMULARIORESPUESTA";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FECHA";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "OBSERVACIONES";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_FORMULARIO";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FECHA_SISTEMA";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LATITUD";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LONGITUD";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_USUARIO";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RESPUESTAS";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CODIGO";
                break;
            default:break;
        }
    }

    @Override
    public void setProperty(int ind, Object val) {
        switch(ind)
        {
            case 0:
                ID_FORMULARIORESPUESTA = val.toString();
                break;
            case 1:
                FECHA = val.toString();
                break;
            case 2:
                OBSERVACIONES = val.toString();
                break;
            case 3:
                ID_FORMULARIO= val.toString();
                break;
            case 4:
                FECHA_SISTEMA =  val.toString();
                break;
            case 5:
                LATITUD = val.toString();
                break;
            case 6:
                LONGITUD = val.toString();
                break;
            case 7:
                ID_USUARIO = val.toString();
                break;
            case 8:
                RESPUESTAS = val.toString();
            case 9:
                CODIGO = val.toString();
                break;

            default:
                break;
        }
    }


}



