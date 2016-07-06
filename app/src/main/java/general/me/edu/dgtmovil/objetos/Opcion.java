package general.me.edu.dgtmovil.objetos;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Opcion implements KvmSerializable{

	
    
    
    public String ID ;
    public String CODIGO ;
    public String RESPUESTA ;
    public String COD_PREGUNTA ;
    public String ID_ENTIDAD ;
    
    
    @Override
  	public Object getProperty(int arg0) {
  	 
  	    switch(arg0)
  	        {
  	        case 0:
  	            return ID ;
  	        case 1:
  	            return CODIGO ;
  	        case 2:
  	            return RESPUESTA ;
  	        case 3:
  	            return COD_PREGUNTA ;
  	      case 4:
	            return ID_ENTIDAD ;
  	      
  	        }
  	 
  	    return null;
  	}
  	
  	@Override
  	public int getPropertyCount() {
  	    return 5;
  	}
  	
  	@Override
  	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
  	    switch(ind)
  	        {
  	        case 0:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID";
  	            break;
  	        case 1:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "CODIGO";
  	            break;
  	        case 2:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "RESPUESTA";
  	            break;
  	        case 3:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "COD_PREGUNTA";
  	            break;
  	      case 4:
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
  	        	ID = val.toString();
  	            break;
  	        case 1:
  	        	CODIGO = val.toString();
  	            break;
  	        case 2:
  	        	RESPUESTA = val.toString();
  	            break;
  	        case 3:
  	        	COD_PREGUNTA= val.toString();
  	            break;
  	      case 4:
	        	ID_ENTIDAD= val.toString();
	            break;
  	        
  	        default:
  	            break;
  	        }
  	}
    
	
}
