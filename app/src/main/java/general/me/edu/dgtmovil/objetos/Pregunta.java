package general.me.edu.dgtmovil.objetos;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Pregunta implements KvmSerializable{


	public String ID_PREGUNTA ;
	public String CODIGO  ;
	public String DESCRIPCION  ;
	public String ESTADO  ;
	public String ID_GRUPO ;
	public String ORDEN  ;
	public String ID_TIPO_PREGUNTA  ;
	public String ID_FORMULARIO  ;
	public String OBLIGATORIA  ;
	public String ID_TIPODATOPREGUNTA  ;
	public String COLUMNA ;
	public String EXIGEOBSERVACION  ;
	public String EXIGEHUELLA  ;
	public String EXIGEFOTO  ;

    @Override
  	public Object getProperty(int arg0) {
  	 
  	    switch(arg0)
  	        {
  	        case 0:
  	            return ID_PREGUNTA ;
  	        case 1:
  	            return CODIGO ;
  	        case 2:
  	            return DESCRIPCION ;
  	        case 3:
  	            return ESTADO ;
  	        case 4:
  	            return ID_GRUPO ;
  	        case 5:
  	            return ORDEN ;
  	        case 6:
  	            return ID_TIPO_PREGUNTA;
				case 7:
					return ID_FORMULARIO ;
				case 8:
					return OBLIGATORIA ;
				case 9:
					return ID_TIPODATOPREGUNTA ;
				case 10:
					return COLUMNA ;
				case 11:
					return EXIGEOBSERVACION ;
				case 12:
					return EXIGEHUELLA ;
				case 13:
					return EXIGEFOTO;
  	        }
  	 
  	    return null;
  	}
  	
  	@Override
  	public int getPropertyCount() {
  	    return 14;
  	}
  	
  	@Override
  	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
  	    switch(ind)
  	        {
  	        case 0:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_PREGUNTA";
  	            break;
  	        case 1:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "CODIGO";
  	            break;
  	        case 2:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "DESCRIPCION";
  	            break;
  	        case 3:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ESTADO";
  	            break;
  	        case 4:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_GRUPO";
  	            break;
  	        case 5:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ORDEN";
  	            break;
  	        case 6:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_TIPO_PREGUNTA";
  	            break;
				case 7:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "ID_FORMULARIO";
					break;
				case 8:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "OBLIGATORIA";
					break;
				case 9:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "ID_TIPODATOPREGUNTA";
					break;
				case 10:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "COLUMNA";
					break;
				case 11:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "EXIGEOBSERVACION";
					break;
				case 12:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "EXIGEHUELLA";
					break;
				case 13:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "EXIGEFOTO";
					break;
  	     
  	        default:break;
  	        }
  	}

  	@Override
  	public void setProperty(int ind, Object val) {
  	    switch(ind)
  	        {
  	        case 0:
				ID_PREGUNTA = val.toString();
  	            break;
  	        case 1:
  	        	CODIGO = val.toString();
  	            break;
  	        case 2:
				DESCRIPCION = val.toString();
  	            break;
  	        case 3:
				ESTADO= val.toString();
  	            break;
  	        case 4:
				ID_GRUPO =  val.toString();
  	            break;
  	        case 5:
				ORDEN = val.toString();
  	            break;
  	        case 6:
				ID_TIPO_PREGUNTA = val.toString();
  	            break;
				case 7:
					ID_FORMULARIO = val.toString();
					break;
				case 8:
					OBLIGATORIA = val.toString();
					break;
				case 9:
					ID_TIPODATOPREGUNTA = val.toString();
					break;
				case 10:
					COLUMNA= val.toString();
					break;
				case 11:
					EXIGEOBSERVACION =  val.toString();
					break;
				case 12:
					EXIGEHUELLA = val.toString();
					break;
				case 13:
					EXIGEFOTO = val.toString();
					break;
  	        default:
  	            break;
  	        }
  	}
    
	
}
