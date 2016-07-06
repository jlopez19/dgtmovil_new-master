package general.me.edu.dgtmovil.dgtmovil;



import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import general.me.edu.dgtmovil.datos.GestionDatos;
import general.me.edu.dgtmovil.datos.Sentencias;
import general.me.edu.dgtmovil.objetos.Afiliado;
import general.me.edu.dgtmovil.objetos.Formulario;
import general.me.edu.dgtmovil.objetos.FormularioRespuesta;
import general.me.edu.dgtmovil.objetos.Foto;
import general.me.edu.dgtmovil.objetos.Opcion;
import general.me.edu.dgtmovil.objetos.Pregunta;

import general.me.edu.dgtmovil.objetos.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class Persistencia extends AsyncTask<String, Integer, String[]>{
	
	Sentencias sentencias;
	Context context;
	public Afiliado[] listaAfiliados;
	public Pregunta[] listaPreguntas;
	public Opcion[] listaOpciones;
	public Usuario[] listaUsuarios;
	public Formulario[] listaFormularios;
	public String[] listaAfiliadosTabla=null;
	public String[] listaFormulariosTabla = null;
	public String ri;
	public String rf;
	public FormularioRespuesta formulario;
	//public ViviendaAfiliado[] viviendaAfiliado;
	public String fi;
	public String ff;
	public String base;
	public String sql;
	public String idInforme="";
	public Afiliado afiliado;
	GestionDatos gestionDatos;
	String idEntidad="";
	
	public Persistencia(Context context,Sentencias sent)
	{
		this.context=context;
		this.sentencias = sent;
		
	//  this.sentencias = sent;
	}
	
	@Override
	protected String[] doInBackground(String... params) {

		gestionDatos = new GestionDatos(context);
		
		gestionDatos.sentencias = this.sentencias;
		
		String op=params[0];
		if(op.equals("1")){
		cargarAfiliados(ri, rf, fi,ff,base);
		}
		else if(op.equals("20")){
			listarPreguntas(idEntidad);
			listarOpciones(idEntidad);
			}
		else if(op.equals("3")){
			listaAfiliadosTabla=new String[1];
			listaAfiliadosTabla[0]="false";
			String res=registrarFormularioRespuesta(formulario);
			listaAfiliadosTabla[0]=res;
			}
		else if(op.equals("4")){
			listaAfiliadosTabla=new String[1];
			listaAfiliadosTabla[0]="false";
			String res=hayConexion();
			listaAfiliadosTabla[0]=res;
			}

	
		else if(op.equals("9")){
			listarUsuarios();
			listaAfiliadosTabla=new String[1];
			listaAfiliadosTabla[0]="true";
			}


		else if(op.equals("21")){
			listarFormularios();
			listaFormulariosTabla=new String[1];
			listaFormulariosTabla[0]="true";
		}
		
		else if(op.equals("10")){
			String res=registrarAfiliado(afiliado);
			listaAfiliadosTabla=new String[1];
			listaAfiliadosTabla[0]=res;
			}
		else if(op.equals("11")){
			// registrarNuevos();
     		enviarPendientes();	
			listaAfiliadosTabla=new String[1];
			listaAfiliadosTabla[0]="true";
			}
		return listaAfiliadosTabla;
	}
		
	
	
	
	public Usuario[] listarUsuarios()
	{
		
		String NAMESPACE = "http://tempuri.org/";
		String URL="http://173.201.37.144:6201/ServiciosDGT.asmx";
	//	String URL="http://10.0.2.2:15718/ServiciosCN.asmx";
		int c=0;
		String METHOD_NAME = "listarUsuariosCN";
		String SOAP_ACTION = "http://tempuri.org/listarUsuariosCN";
		
		String resultado="false";
		String fila="";
		try
		{
		//	mostrarMensaje("antes1", 10);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);    	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        transporte.call(SOAP_ACTION, envelope);
	        SoapObject resSoap =(SoapObject)envelope.getResponse();
	        
	        
	        listaUsuarios = new Usuario[resSoap.getPropertyCount()];
	        
	        
	        for (int i = 0; i < listaUsuarios.length; i++)
	        {
        	SoapObject ic = (SoapObject)resSoap.getProperty(i);	 
	               Usuario sit = new Usuario();
	               sit.ID = ic.getProperty(0).toString();
	               sit.NOMBRE = ic.getProperty(1).toString();
	               sit.APELLIDO = ic.getProperty(2).toString();
	               sit.CEDULA = ic.getProperty(3).toString();
	               sit.LOGIN = ic.getProperty(4).toString();
	               sit.PASSWORD = ic.getProperty(5).toString();
	               sit.ID_PERFIL = ic.getProperty(6).toString();
	               sit.ID_PERSONA = ic.getProperty(7).toString();   
	               sit.ID_ENTIDAD = ic.getProperty(8).toString();   
	               listaUsuarios[i] = sit;	             
	               fila="";
	           }
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
	    return listaUsuarios;
		
		
	}

	public Formulario[] listarFormularios()
	{

		String NAMESPACE = "http://tempuri.org/";
		String URL="http://173.201.37.144:6201/ServiciosDGT.asmx";
		//	String URL="http://10.0.2.2:15718/ServiciosCN.asmx";
		int c=0;
		String METHOD_NAME = "listarFormularios";
		String SOAP_ACTION = "http://tempuri.org/listarFormularios";

		String resultado="false";
		String fila="";
		try
		{
			//	mostrarMensaje("antes1", 10);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);



			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			new MarshalBase64().register(envelope);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			transporte.call(SOAP_ACTION, envelope);
			SoapObject resSoap =(SoapObject)envelope.getResponse();


			listaFormularios = new Formulario[resSoap.getPropertyCount()];


			for (int i = 0; i < listaFormularios.length; i++)
			{
				SoapObject ic = (SoapObject)resSoap.getProperty(i);
				Formulario sit = new Formulario();
				sit.ID_FORMULARIO = ic.getProperty(0).toString();
				sit.TITULO = ic.getProperty(1).toString();
				sit.DESCRIPCION = ic.getProperty(2).toString();
				sit.ID_LIDER = ic.getProperty(3).toString();
				listaFormularios[i] = sit;
				fila="";
			}


		}
		catch(Exception exp)
		{
			resultado="false";
			exp.printStackTrace();
		}
		return listaFormularios;


	}
	
	public String[] cargarAfiliados(String ri,String rf,String redadi,String redadf,String db)
	{
		
		String NAMESPACE = "http://tempuri.org/";
		String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
		//String URL="http://10.0.2.2:56129/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "listarAfiliados";
		String SOAP_ACTION = "http://tempuri.org/listarAfiliados";
		
		String resultado="false";
		String fila="";
		try
		{
		//	mostrarMensaje("antes1", 10);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("ri");  //Nombre del objeto que recibe el servicio
			prop.setValue(ri);
			prop.setType(ri.getClass());
			request.addProperty(prop);
			prop = new PropertyInfo();
			prop.setName("rf");  //Nombre del objeto que recibe el servicio
			prop.setValue(rf);
			prop.setType(rf.getClass());
			request.addProperty(prop);
			prop = new PropertyInfo();
			prop.setName("redadi");  //Nombre del objeto que recibe el servicio
			prop.setValue(redadi);
			prop.setType(redadi.getClass());
			request.addProperty(prop);
			prop = new PropertyInfo();
			prop.setName("redadf");  //Nombre del objeto que recibe el servicio
			prop.setValue(redadf);
			prop.setType(redadf.getClass());
			request.addProperty(prop);
			prop = new PropertyInfo();
			prop.setName("db");  //Nombre del objeto que recibe el servicio
			prop.setValue(db);
			prop.setType(db.getClass());
			request.addProperty(prop);
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);    	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        transporte.call(SOAP_ACTION, envelope);
	        SoapObject resSoap =(SoapObject)envelope.getResponse();
	        
	        
	        listaAfiliados = new Afiliado[resSoap.getPropertyCount()];
	        listaAfiliadosTabla=new String[resSoap.getPropertyCount()];
	        
	        for (int i = 0; i < listaAfiliados.length; i++)
	        {
	               SoapObject ic = (SoapObject)resSoap.getProperty(i);	 
	               Afiliado afil = new Afiliado();
	               afil.ORDEN=ic.getProperty(0).toString();
			    	afil.NOMBRE=ic.getProperty(1).toString();
			    	afil.APELLIDOS=ic.getProperty(2).toString();
			    	afil.TD=ic.getProperty(3).toString();
			    	afil.IDENTIFICACION=ic.getProperty(4).toString();
			    	afil.SEXO=ic.getProperty(5).toString();
			    	afil.RECONOCE_COMO=ic.getProperty(6).toString();
			        afil.FN=ic.getProperty(7).toString();
			        afil.PARENTESCO=ic.getProperty(8).toString();
			        afil.ESTADO_CIVIL=ic.getProperty(9).toString();
			        afil.CEDULA_CONYUGUE=ic.getProperty(10).toString();
			        afil.NUMERO_ORDEN_CONYUGUE=ic.getProperty(11).toString();
			    	afil.ORDEN_PADRE=ic.getProperty(12).toString();
			    	afil.ARRIENDO_OTRO=ic.getProperty(13).toString();
			    	afil.DISCAPACIDAD_PERMANENTE=ic.getProperty(14).toString();
			    	afil.AFILIADO_A=ic.getProperty(15).toString();
			    	afil.EMBARAZADA_HIJOS=ic.getProperty(16).toString();
			    	afil.ASISTENCIA_CENTRO_EDUCATIVO=ic.getProperty(17).toString();
			        afil.TIPO_ESTABLECIMIENTO=ic.getProperty(18).toString();
			        afil.NIVEL_EDUCATIVO=ic.getProperty(19).toString();
			        afil.PORQUE_NO_CONTINUO_ESTUDIOS=ic.getProperty(20).toString();
			        afil.GUSTARIA_SEGUIR_ESTUDIANDO=ic.getProperty(21).toString();
			        afil.PERCIBE_INGRESOS=ic.getProperty(22).toString();
			        afil.TOTAL_INGRESOS=ic.getProperty(23).toString();
			        afil.ID_ENTIDAD=ic.getProperty(24).toString();
			        
	               
	           
	               
	               listaAfiliados[i] = afiliado;
	             
	           
	           }
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
	    return listaAfiliadosTabla;
		
		
	}
	
	
	public String registrarFormularioRespuesta(FormularioRespuesta formulario)
	{
		//"http://tempuri.org/listarUsuarios 15718
		String NAMESPACE = "http://tempuri.org/";
	    String URL="http://173.201.37.144:6201/ServiciosDGT.asmx";
	    //String URL="http://10.0.2.2:61048/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "registrarFormularioRespuesta";
		String SOAP_ACTION = "http://tempuri.org/registrarFormularioRespuesta";
		
		String resultado="false";
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			
			prop.setName("formulario");  //Nombre del objeto que recibe el servicio
			prop.setValue(formulario);
			prop.setType(formulario.getClass());
			
			request.addProperty(prop);
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	         envelope.addMapping(NAMESPACE, "FormularioRespuesta", formulario.getClass());  //Nombre de la clase en el servicio
		      	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        
	        transporte.call(SOAP_ACTION, envelope);
	        
	        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	        
	        resultado = response.toString();
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
		
	//	registrarImagenes(ubicacion);
		
	    return resultado;
		
		
	}
	
	
	public String registrarAfiliado(Afiliado afiliado)
	{
		//"http://tempuri.org/listarUsuarios 15718
		String NAMESPACE = "http://tempuri.org/";
	    String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
	    //String URL="http://10.0.2.2:61048/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "registrarAfiliado";
		String SOAP_ACTION = "http://tempuri.org/registrarAfiliado";
		
		String resultado="false";
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("afiliado");  //Nombre del objeto que recibe el servicio
			prop.setValue(afiliado);
			prop.setType(afiliado.getClass());
			
			request.addProperty(prop);
			
		
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        envelope.addMapping(NAMESPACE, "Afiliado", afiliado.getClass());  //Nombre de la clase en el servicio 
	 	      	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        
	        transporte.call(SOAP_ACTION, envelope);
	        
	        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	        
	        resultado = response.toString();
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
		

	    return resultado;
		
		
	}
	

	public String hayConexion()
	{
		//"http://tempuri.org/listarUsuarios 15718
		String NAMESPACE = "http://tempuri.org/";
		String URL = "http://173.201.37.144:6201/ServiciosDGT.asmx";
	    //String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
		//String URL="http://10.0.2.2:15718/ServiciosUsuarios.asmx";
		int c=0;
		String METHOD_NAME = "hayConexion";
		String SOAP_ACTION = "http://tempuri.org/hayConexion";
		
		String resultado="false";
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        
	        transporte.call(SOAP_ACTION, envelope);
	        
	        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	        
	        resultado = response.toString();
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
				
	    return resultado;
			
	}

	/*
	public String registrarViviendaAfiliadoProgramas(UbicacionVivienda ubicacion,ViviendaAfiliado[] viviendaAfilidadoa)
	{
		
		String listav=""; 
		String listap="";
		
	
		
	//	for(int i=0;i<viviendaAfilidadoa.length;i++){
		//	ViviendaAfiliado va=viviendaAfilidadoa[i];
			//listav=listav+va.ID_CASA+";"+va.ID_AFILIADO+";"+va.PARENTESCO+";"+va.EMBARAZADA+";"+va.ETNIA+";"+va.CONDICION+"*";
		//}
		
	//	ubicacion.VIVIENDA_AFILIADO=listav;
		
		//"http://tempuri.org/listarUsuarios 15718
		String NAMESPACE = "http://tempuri.org/";
		   String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
	 // String URL="http://10.0.2.2:61048/ServiciosCen/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "registrarViviendaAfiliado";
		String SOAP_ACTION = "http://tempuri.org/registrarViviendaAfiliado";
		
		String resultado="false";
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("ubicacion");  //Nombre del objeto que recibe el servicio
			prop.setValue(ubicacion);
			prop.setType(ubicacion.getClass());
			
			request.addProperty(prop);
			
		
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        envelope.addMapping(NAMESPACE, "UbicacionVivienda", ubicacion.getClass());  //Nombre de la clase en el servicio 
	     	     	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        
	        transporte.call(SOAP_ACTION, envelope);
	        
	        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	        
	        resultado = response.toString();
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
		
		registrarImagenesPersona(viviendaAfilidadoa);
		
	    return resultado;
		
		
	}

	*/
	
	 public void mostrarMensaje(String mensaje,int tiempo){
	  	  Toast toast4 = Toast.makeText(context,
						mensaje, tiempo);
		  
				toast4.show(); 
	    }
	 
	 private byte[] obtenerImagenComparendo(String nombre)
	    {
	    	byte[] firma = null;
	  //      mostrarMensaje("1", 3);
	    	File carpetaImagenes = new File(
					Environment.getExternalStorageDirectory(), "Censo");
	    //	mostrarMensaje("2", 3);
			File archivofirma = new File(carpetaImagenes, nombre);
			//mostrarMensaje("3", 3);
          //   mostrarMensaje(carpetaImagenes+"/"+nombre, 3);
	    	if(archivofirma.exists())
	    	{
	    		//mostrarMensaje("4", 3);
	    		firma =  convertirbytes(archivofirma);
	    	//	mostrarMensaje("5", 3);
	    	//	mostrarMensaje(firma.toString(), 3);
	    	}
			
	    
	    	return firma;
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
	
	 /*
	 public void registrarImagenes(UbicacionVivienda vivienda){
		  Foto ftemp = new Foto();
    	  ftemp.id = 1;
    	  ftemp.fecha = "";//
    	  ftemp.hora = "";//lectura.HORA;
    	  if(vivienda.ID_ENTIDAD.equals("1")){
    	  ftemp.lugar = "Caloto";
    	  }
    	  else if(vivienda.ID_ENTIDAD.equals("2")){
        	  ftemp.lugar = "Mujeres";
        	  }
    	  ftemp.placa = vivienda.IDENTIFICADOR;
    	  ftemp.codigoinfraccion = "";
    	  ftemp.x="";
    	  ftemp.y="";
          ftemp.foto1 =obtenerImagenComparendo(vivienda.IDENTIFICADOR+"_foto_casa.jpg"); 
          ftemp.foto2 =obtenerImagenComparendo(vivienda.IDENTIFICADOR+"_foto_contador.jpg"); 
          ftemp.foto3 =obtenerImagenComparendo(vivienda.IDENTIFICADOR+"_foto_error.jpg"); 
          enviarFotoComparendo(ftemp);
	 }
	 
	 public void registrarImagenesPersona(ViviendaAfiliado[] viviendaAfiliado){
		 ViviendaAfiliado va;
		 for(int i=0;i<viviendaAfiliado.length;i++){
	      va=viviendaAfiliado[i];
		 Foto ftemp = new Foto();     	  
   	     ftemp.id = 1;
   	     ftemp.fecha = "";//
   	     ftemp.hora = "";//lectura.HORA;
   	     ftemp.lugar = "";
   	     ftemp.placa = va.IDENTIFICACION;
   	     ftemp.codigoinfraccion = "";
   	     ftemp.x="";
   	     ftemp.y="";
         ftemp.foto1 =obtenerImagenComparendo(va.IDENTIFICACION+"_foto_persona.jpg"); 

         enviarFotoPersona(ftemp);
		 }
	 }
	 
	 
	 
	 public String enviarFotoPersona(Foto f)
		{
			
			String NAMESPACE = "http://tempuri.org/";
			//String URL="http://10.0.2.2:15718/ServiciosUsuarios.asmx";
			String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
			int c=0;
			String METHOD_NAME = "crearFotoPersona";
			String SOAP_ACTION = "http://tempuri.org/crearFotoPersona";
			
			String resultado="false";
			try
			{
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		        			
				PropertyInfo prop = new PropertyInfo();
				prop.setName("fotocomparendo");
				prop.setValue(f);
				prop.setType(f.getClass());
				
				request.addProperty(prop);
				
				
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        new MarshalBase64().register(envelope);
		        envelope.dotNet = true;
		        envelope.setOutputSoapObject(request);
		        envelope.addMapping(NAMESPACE, "Fotocomparendo", f.getClass());
		        	        
		        HttpTransportSE transporte = new HttpTransportSE(URL);
		        
		        transporte.call(SOAP_ACTION, envelope);
		        
		        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
		        
		        resultado = response.toString();
		        
		        
			}
			catch(Exception exp)
			{
				resultado="false";	
				exp.printStackTrace();
			}
		    return resultado;
			
			
		}
		
	 
	public String enviarFotoComparendo(Foto f)
	{
		
		String NAMESPACE = "http://tempuri.org/";
		//String URL="http://10.0.2.2:15718/ServiciosUsuarios.asmx";
		String URL="http://173.201.37.144:8101/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "crearFotoMedicina";
		String SOAP_ACTION = "http://tempuri.org/crearFotoMedicina";
		
		String resultado="false";
		try
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("fotocomparendo");
			prop.setValue(f);
			prop.setType(f.getClass());
			
			request.addProperty(prop);
			
	
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        envelope.addMapping(NAMESPACE, "Fotocomparendo", f.getClass());
	        	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        
	        transporte.call(SOAP_ACTION, envelope);
	        
	        SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	        
	        resultado = response.toString();
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
	    return resultado;
		
		
	}

	*/
	
	
	
	public String[] listarPreguntas(String idEntidad)
	{
		
		String NAMESPACE = "http://tempuri.org/";
		String URL="http://173.201.37.144:6201/ServiciosDGT.asmx";
		//String URL="http://10.0.2.2:15718/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "listarPreguntas";
		String SOAP_ACTION = "http://tempuri.org/listarPreguntas";
		
		String resultado="false";
		String fila="";
		try
		{
		//	mostrarMensaje("antes1", 10);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("idEntidad");  //Nombre del objeto que recibe el servicio
			prop.setValue(idEntidad);
			prop.setType(idEntidad.getClass());
			request.addProperty(prop);
			
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);    	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        transporte.call(SOAP_ACTION, envelope);
	        SoapObject resSoap =(SoapObject)envelope.getResponse();
	        
	        
	        listaPreguntas = new Pregunta[resSoap.getPropertyCount()];
	        listaAfiliadosTabla=new String[resSoap.getPropertyCount()];
	        
	        for (int i = 0; i < listaPreguntas.length; i++)
	        {
	             
	        	
	        	
	        	SoapObject ic = (SoapObject)resSoap.getProperty(i);	
	        	Pregunta vacuna = new Pregunta();

				vacuna.ID_PREGUNTA =ic.getProperty(0).toString();
				vacuna.CODIGO = ic.getProperty(1).toString();
				vacuna.DESCRIPCION = ic.getProperty(2).toString();
				vacuna.ESTADO =ic.getProperty(3).toString();
				vacuna.ID_GRUPO = ic.getProperty(4).toString();
				vacuna.ORDEN = ic.getProperty(5).toString();
				vacuna.ID_TIPO_PREGUNTA =ic.getProperty(6).toString();
				vacuna.ID_FORMULARIO = ic.getProperty(7).toString();
				vacuna.OBLIGATORIA = ic.getProperty(8).toString();
				vacuna.ID_TIPODATOPREGUNTA =ic.getProperty(9).toString();
				vacuna.COLUMNA = ic.getProperty(10).toString();
				vacuna.EXIGEOBSERVACION = ic.getProperty(11).toString();
				vacuna.EXIGEHUELLA =ic.getProperty(12).toString();
				vacuna.EXIGEFOTO = ic.getProperty(13).toString();
	           
	               listaPreguntas[i] = vacuna;
	               listaAfiliadosTabla[i]=fila+" ** 0";
	               fila="";
	           }
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
	    return listaAfiliadosTabla;
		
		
	}
	
	public String[] listarOpciones(String idEntidad)
	{
		
		String NAMESPACE = "http://tempuri.org/";
		String URL="http://173.201.37.144:6201/ServiciosDGT.asmx";
		//String URL="http://10.0.2.2:15718/ServiciosCen.asmx";
		int c=0;
		String METHOD_NAME = "listarOpciones";
		String SOAP_ACTION = "http://tempuri.org/listarOpciones";
		
		String resultado="false";
		String fila="";
		try
		{
		//	mostrarMensaje("antes1", 10);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	        			
			PropertyInfo prop = new PropertyInfo();
			prop.setName("idEntidad");  //Nombre del objeto que recibe el servicio
			prop.setValue(idEntidad);
			prop.setType(idEntidad.getClass());
			request.addProperty(prop);
			
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        new MarshalBase64().register(envelope);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);    	        
	        HttpTransportSE transporte = new HttpTransportSE(URL);
	        transporte.call(SOAP_ACTION, envelope);
	        SoapObject resSoap =(SoapObject)envelope.getResponse();
	        
	        
	        listaOpciones = new Opcion[resSoap.getPropertyCount()];
	        listaAfiliadosTabla=new String[resSoap.getPropertyCount()];
	        
	        for (int i = 0; i < listaOpciones.length; i++)
	        {
	             
	        	
	        	
	        	SoapObject ic = (SoapObject)resSoap.getProperty(i);	
	        	Opcion vacuna = new Opcion();
	              
	   
	            	   vacuna.ID  =  ic.getProperty(0).toString();
	            	   vacuna.CODIGO  =  ic.getProperty(1).toString();
	            	   vacuna.RESPUESTA  =  ic.getProperty(2).toString();
	            	   vacuna.COD_PREGUNTA  =  ic.getProperty(3).toString();
	            	   vacuna.ID_ENTIDAD  =  ic.getProperty(4).toString();
	            	  
	           
	            	   listaOpciones[i] = vacuna;
	               listaAfiliadosTabla[i]=fila+" ** 0";
	               fila="";
	           }
	        
	        
		}
		catch(Exception exp)
		{
			resultado="false";	
			exp.printStackTrace();
		}
	    return listaAfiliadosTabla;
		
		
	}
	
	public void registrarNuevos(){
		try{
		Afiliado[] listaAfiliados=gestionDatos.obtenerAfiliadosNuevos();
	
		for(int i=0;i<listaAfiliados.length;i++){
	
			Afiliado afil=listaAfiliados[i];
			String res=registrarAfiliado(afil);
             if(res!=null ){
           	if(res.equals("true")){// envio bien editar estado y enviado
             gestionDatos.editarEnviadoAfiliado(afil.ID_ENTIDAD);	    	       		   		
    
	}	
			
		}
		}
		}
		catch(Exception e){
			
		}
	}
	
public boolean enviarPendientes(){
		
			FormularioRespuesta[] listaFormulariosRespuestas=gestionDatos.getFormulariosRespuestaNoEnviada();
		int r=0;
		try{
	
			r=0;
			if(listaFormulariosRespuestas!=null && listaFormulariosRespuestas.length>0){
			for(int i=0;i<listaFormulariosRespuestas.length;i++){
			    r=i+1;
				FormularioRespuesta formulario=listaFormulariosRespuestas[i];

		        	
				String res=registrarFormularioRespuesta(formulario);
	        		 if(res!=null ){
	              	if(res.equals("true")){// envio bien editar estado y enviado
                    gestionDatos.editarEnviadoFormularioRespuesta(formulario.CODIGO,"SI");
		
		}
		}
			}
			 }	
			
		
		
		
	
		}
		catch(Exception e){
			
		}
		
		
		
		
		return true;
	}

	
	
	}
	
	

