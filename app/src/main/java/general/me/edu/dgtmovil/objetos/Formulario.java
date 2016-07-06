package general.me.edu.dgtmovil.objetos;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by ADMIN on 18/06/2016.
 */

public class Formulario implements KvmSerializable {
    public String ID_FORMULARIO;
    public String TITULO;
    public String DESCRIPCION;
    public String ID_LIDER;


    @Override
    public Object getProperty(int arg0) {

        switch(arg0)
        {
            case 0:
                return ID_FORMULARIO ;
            case 1:
                return TITULO ;
            case 2:
                return DESCRIPCION ;
            case 3:
                return ID_LIDER ;
                   }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
        switch(ind)
        {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_FORMULARIO";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TITULO";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DESCRIPCION";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ID_LIDER";
                break;


            default:break;
        }
    }

    @Override
    public void setProperty(int ind, Object val) {
        switch(ind)
        {
            case 0:
                ID_FORMULARIO = val.toString();
                break;
            case 1:
                TITULO = val.toString();
                break;
            case 2:
                DESCRIPCION = val.toString();
                break;
            case 3:
                ID_LIDER= val.toString();
                break;


            default:
                break;
        }
    }
}
