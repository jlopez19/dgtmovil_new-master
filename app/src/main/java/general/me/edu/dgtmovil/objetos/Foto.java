package general.me.edu.dgtmovil.objetos;

import java.util.Hashtable;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Foto implements KvmSerializable {

	public String placa;
	public String fecha;
	public String hora;
	public byte[] foto1;
	public byte[] firma;
	public String lugar;
	public String codigoinfraccion;
	public String idagente;
	public String nombreagente;
	public byte[] foto2;
	public byte[] foto3;
	public byte[] foto4;
	public byte[] foto5;
	public String x;
	public String y;
	public int id;
	
	@Override
	public Object getProperty(int arg0) {
	 
	    switch(arg0)
	        {
	        case 0:
	            return placa;
	        case 1:
	            return fecha;
	        case 2:
	            return hora;
	        case 3:
	            return foto1;
	        case 4:
	            return firma;
	        case 5:
	            return lugar;
	        case 6:
	            return codigoinfraccion;
	        case 7:
	            return idagente;
	        case 8:
	            return nombreagente;
	        case 9:
	            return foto2;
	        case 10:
	            return foto3;
	        case 11:
	            return foto4;
	        case 12:
	            return foto5;
	        case 13:
	            return x;
	        case 14:
	            return y;
	        case 15:
	            return id;
	            
	        }
	 
	    return null;
	}
	
	@Override
	public int getPropertyCount() {
	    return 16;
	}
	
	@Override
	public void getPropertyInfo(int ind, Hashtable ht, PropertyInfo info) {
	    switch(ind)
	        {
	        case 0:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "placa";
	            break;
	        case 1:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "fecha";
	            break;
	        case 2:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "hora";
	            break;
	        case 3:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "foto1";
	            break;
	        case 4:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "firma";
	            break;
	        case 5:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "lugar";
	            break;
	        case 6:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "codigoinfraccion";
	            break;
	        case 7:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "idagente";
	            break;
	        case 8:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "nombreagente";
	            break;
	        case 9:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "foto2";
	            break;
	        case 10:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "foto3";
	            break;
	        case 11:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "foto4";
	            break;
	        case 12:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "foto5";
	            break;
	        case 13:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "x";
	            break;
	        case 14:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "y";
	            break;
	        case 15:
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.name = "id";
	            break;
	            
	        default:break;
	        }
	}

	@Override
	public void setProperty(int ind, Object val) {
	    switch(ind)
	        {
	        case 0:
	            placa = val.toString();
	            break;
	        case 1:
	            fecha = val.toString();
	            break;
	        case 3:
	            hora = val.toString();
	            break;
	        case 4:
	            foto1= (byte[])val;
	            break;
	        case 5:
	            firma = (byte[])val;
	            break;
	        case 6:
	            lugar = val.toString();
	            break;
	        case 7:
	            codigoinfraccion = val.toString();
	            break;
	        case 8:
	            idagente = val.toString();
	            break;
	        case 9:
	            foto2 = (byte[])val;
	            break;
	        case 10:
	            foto3 = (byte[])val;
	            break;
	        case 11:
	            foto4 = (byte[])val;
	            break;
	        case 12:
	            foto5 = (byte[])val;
	            break;
	        case 13:
	            x = val.toString();
	            break;
	        case 14:
	            y = val.toString();
	            break;
	        case 15:
	            id = Integer.parseInt(val.toString());
	            break;
	        default:
	            break;
	        }
	}
	
}


