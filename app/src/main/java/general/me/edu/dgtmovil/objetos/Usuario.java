package general.me.edu.dgtmovil.objetos;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Usuario implements KvmSerializable{

	public String ID ;
    public String NOMBRE;
    public String APELLIDO ;
    public String CEDULA;
    public String LOGIN;
    public String PASSWORD ;
    public String ID_PERFIL;
    public String ID_PERSONA;
    public String ID_ENTIDAD;
   
  
  
   
    
    @Override
  	public Object getProperty(int arg0) {
  	 
  	    switch(arg0)
  	        {
  	        case 0:
  	            return ID;
  	        case 1:
  	            return NOMBRE;
  	        case 2:
  	            return APELLIDO;
  	        case 3:
  	            return CEDULA;
  	        case 4:
  	            return LOGIN;
  	        case 5:
  	            return PASSWORD;
  	        case 6:
  	            return ID_PERFIL;
  	         case 7:
	            return ID_PERSONA;
  	       case 8:
	            return ID_ENTIDAD;
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
  	            info.name = "ID";
  	            break;
  	        case 1:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "NOMBRE";
  	            break;
  	        case 2:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "APELLIDO";
  	            break;
  	        case 3:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "CEDULA";
  	            break;
  	        case 4:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "LOGIN";
  	            break;
  	        case 5:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "PASSWORD";
  	            break;
  	        case 6:
  	            info.type = PropertyInfo.STRING_CLASS;
  	            info.name = "ID_PERFIL";
  	            break;
  	        case 7:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "ID_PERSONA";
	            break;
  	        case 8:
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
  	        	NOMBRE = val.toString();
  	            break;
  	        case 2:
  	        	APELLIDO = val.toString();
  	            break;
  	        case 3:
  	        	CEDULA= val.toString();
  	            break;
  	        case 4:
  	        	LOGIN =  val.toString();
  	            break;
  	        case 5:
  	        	PASSWORD = val.toString();
  	            break;
  	        case 6:
  	        	ID_PERFIL = val.toString();
  	            break;
  	        case 7:
  	    	    ID_PERSONA = val.toString();
	            break;
  	      case 8:
	    	    ID_ENTIDAD = val.toString();
	            break;
  	       
  	        default:
  	            break;
  	        }
  	}
    
	
}
