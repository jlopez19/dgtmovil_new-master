package general.me.edu.dgtmovil.objetos;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Respuesta implements KvmSerializable{

	 public String ID_RESPUESTA ;
     public String ID_PREGUNTA ;
     public String ID_PREGUNTAOPCION ;
     public String DATOTEXTO ;
     public String DATOHUELLA;
     public String DATOBINARIO ;
	public String ID_FORMULARIORESPUESTA ;
	public String OBSERVACION;
	public String CODIGO_FORMULARIO ;
     
    @Override
  	public Object getProperty(int arg0) {
  	 
  	    switch(arg0)
  	        {
  	        case 0:
  	            return ID_RESPUESTA ;
  	        case 1:
  	            return ID_PREGUNTA ;
  	        case 2:
  	            return ID_PREGUNTAOPCION ;
  	        case 3:
  	            return DATOTEXTO ;
  	        case 4:
	            return DATOHUELLA ;
	        case 5:
	            return DATOBINARIO ;
				case 6:
					return ID_FORMULARIORESPUESTA ;
				case 7:
					return OBSERVACION ;
				case 8:
					return CODIGO_FORMULARIO ;
  	        }
  	 
  	    return null;
  	}
  	
  	@Override
  	public int getPropertyCount() {
  	    return 9;
  	}
  	
  	@Override
  	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
  	    switch(ind)
  	        {
  	        case 0:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_RESPUESTA";
  	            break;
  	        case 1:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_PREGUNTA";
  	            break;
  	        case 2:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_PREGUNTAOPCION";
  	            break;
  	        case 3:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "DATOTEXTO";
  	            break;
  	        case 4:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "DATOHUELLA";
	            break;
	        case 5:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "DATOBINARIO";
	            break;
				case 6:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "ID_FORMULARIORESPUESTA";
					break;
				case 7:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "OBSERVACION";
					break;
				case 8:
					info.type = PropertyInfo.STRING_CLASS;
					info.name = "CODIGO_FORMULARIO";
					break;

  	       
  	        default:break;
  	        }
  	}

  	@Override
  	public void setProperty(int ind, Object val) {
  	    switch(ind)
  	        {
  	        case 0:
				ID_RESPUESTA = val.toString();
  	            break;
  	        case 1:
				ID_PREGUNTA = val.toString();
  	            break;
  	        case 2:
				ID_PREGUNTAOPCION = val.toString();
  	            break;
  	        case 3:
				DATOTEXTO= val.toString();
  	            break;
  	        case 4:
				DATOHUELLA = val.toString();
	            break;
	        case 5:
				DATOBINARIO= val.toString();
	            break;
				case 6:
					ID_FORMULARIORESPUESTA = val.toString();
					break;
				case 7:
					OBSERVACION= val.toString();
					break;
				case 8:
					CODIGO_FORMULARIO = val.toString();
					break;

  	       
  	        
  	        default:
  	            break;
  	        }
  	}
    
	
}
