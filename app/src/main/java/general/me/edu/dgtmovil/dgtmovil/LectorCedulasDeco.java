package general.me.edu.dgtmovil.dgtmovil;

/**
 * Created by ADMIN on 23/06/2016.
 */
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LectorCedulasDeco {

    public String[] decodificarCedula(String lector){
        String elementos[] = lector.split("Element #");
        System.out.println("Numero de elementos: "+ (elementos.length -1));
        //  System.out.println("Elemento 0 tiene: "+ elementos[1]);
        String cadena[];
        String cadena2[];
        ArrayList<String[]> datos = new ArrayList<String[]>();
        String imprimirFinal= "";
        boolean parar = false;

        for(int i = 4; i < 17 && !parar; i++){

            cadena = elementos[i].split("Length = ");
            String recibeC = quitarLlaves(cadena[1]);


            ArrayList<String> temporal = separaComas(recibeC);

            for(int k = 0; k < temporal.size(); k++){
                System.out.println(temporal.get(k));
            }

            imprimirFinal = imprimirFinal + convierteArrayByte(temporal);

            System.out.println("Esta es la palabra Final: "+ imprimirFinal);

        }

        return formatearCadena(imprimirFinal);
    }
    public String quitarLlaves(String cadena){
        String convierte ="";
        boolean para = false;
        for(int i = 0; i < cadena.length(); i++){
            Character letra = cadena.charAt(i);
            if(letra == '{'){
                para = true;
            }
            if(letra == '}'){
                para = false;
            }
            if(para){
                convierte = convierte + letra;
            }

        }
        convierte = convierte.substring(1, convierte.length());
        return convierte;
    }

    public ArrayList<String> separaComas(String convierte){
        String dato[];
        ArrayList<String> lector = new ArrayList<String>();
        String temporal="";
        boolean cero = false;
        boolean numero = false;

        dato = convierte.split(",");


        for(int j = 0; j < dato.length; j++){
            String temp = dato[j].trim();

            if(!temp.equals("0")){
                numero = true;
                cero = false;
                temporal = temporal + dato[j];
            }
            if(temp.equals("0") && numero){
                numero = false;
                lector.add(temporal);
                temporal = "";
            }
            if(j == dato.length - 1 && numero){
                lector.add(temporal);
                temporal = "";
            }
        }
        System.out.println("");
        return lector;
    }

    public String convierteArrayByte(ArrayList<String> dato){
        String envia = "";
        String temp[];
        for(int i = 0; i < dato.size(); i++){
            String datoTemp = dato.get(i).trim();
            temp = datoTemp.split(" ");

            for(int j = 0; j < temp.length; j++){

                temp[j] = temp[j].trim();
                try{
                    if(Integer.parseInt(temp[j])>127){
                        temp[j] = ""+47;
                    }}
                catch(NumberFormatException e){
                    temp[j] = "48";

                }

            }
            envia = envia + " "+convierteByte(temp);
        }
        return envia;
    }

    public String convierteByte(String[] recibe){
        byte[] datoEnviar;
        String textoLimpio = "";
        datoEnviar = new byte[recibe.length];
        for(int i = 0; i < recibe.length; i++){
            datoEnviar[i] = Byte.parseByte(recibe[i].trim());
        }
        try {
            textoLimpio = new String(datoEnviar, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LectorCedulasDeco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return textoLimpio;
    }

    public boolean validarCadena(String imprimir){
        boolean valido = true;
        if(imprimir.equals(" ")){
            valido = true;
        }
        return valido;
    }

    public String[] formatearCadena(String cadena){
        String resultado[] = new String[8];
        String temp[] = cadena.split(" ");
        int contador = 0;
        int cuentaDonde = 0;
        boolean completo = false;
        boolean sumar = false;
        for(int i = 0; i < temp.length && !completo; i++){

            if(temp[i].length() > 0){
                if(contador > 7){
                    completo = false;
                }
                System.out.println("Dato "+ i + ": "+ temp[i]);
                String compara = temp[i];
                String comparaNum = comparaNumeros(compara);
                System.out.println("Datos en Numeros: "+ comparaNum);
                String comparaLetras = compararLetras(compara);
                System.out.println("Datos en Letras: "+ comparaLetras);
                if(comparaNum.length() > 5 && comparaLetras.length() > 0 && compara.indexOf("0F")<0 && compara.indexOf("0M")<0 && compara.indexOf("/")<0 ){
                    resultado[0] = validarCedula(comparaNum);
                    contador++;
                    System.out.println("Ingreso hasta aquí: *********************");
                    resultado[1] = comparaLetras;
                    contador++;
                }
                if((compara.indexOf("0F1") != -1) || (compara.indexOf("0F2")!=-1) || (compara.indexOf("0M1")!=-1) ||(compara.indexOf("0F2") != -1)){
                    resultado[5] = obtenerFechaNacimiento(compara);
                    resultado[6] = obtenerGenero(compara);
                }
                if(comparaNum.length() == 0 && cuentaDonde == 0 && comparaLetras.length()>0){

                    resultado[2] = comparaLetras;
                    sumar = true;
                }
                if(comparaNum.length() == 0 && cuentaDonde == 1 && comparaLetras.length()>0){

                    resultado[3] = comparaLetras;
                    sumar = true;
                }
                if(comparaNum.length() == 0 && cuentaDonde == 2 && comparaLetras.length()>0){

                    resultado[4] = comparaLetras;
                    sumar = true;
                }
                if(comparaNum.length() == 0 && comparaLetras.length() == 2 && (comparaLetras.indexOf("O+")>=0)){
                    resultado[7] = comparaLetras;
                    completo = true;
                }

                if(sumar){
                    cuentaDonde++;
                }


            }

        }
        for(int i = 0; i <8; i++){
            if(resultado[i] != null){
                System.out.println("El resultado en: "+i+ "es: "+resultado[i]);
            }
        }
        return resultado;
    }

    public String validarCedula(String cedula){
        String res = "";
        if(cedula.length() > 9){
            res= cedula.substring(cedula.length()-10,cedula.length());
            res= quitarCero(res);
        }
        return res;
    }
    public String quitarCero(String quitarCero){
        String res = quitarCero;

        for(int i = 0; i < quitarCero.length(); i++){
            if(res.indexOf("0")== 0){
                res = res.substring(1,res.length());
            }
        }


        return res;
    }

    public String obtenerFechaNacimiento(String cadena){
        String fecha = "";

        fecha = cadena.substring(6, 8)+ "/" +cadena.substring(8, 10)+"/"+ cadena.subSequence(2, 6) ;

        return fecha;
    }

    public String obtenerGenero(String cadena){
        String genero = "";
        genero = cadena.substring(1, 2);
        // System.out.println("Genero: "+genero);
        return genero;
    }
    public String compararLetras(String datoTemp){
        String datoEnviar = "";
        for (int i = 0; i < datoTemp.length(); i++) {
            Character compara = datoTemp.charAt(i);
            if ((compara >= 'a' && compara <= 'z') || (compara >= 'A' && compara <= 'Z') || (compara == '/') || (compara == '+')) {
                datoEnviar = datoEnviar + "" + compara;
            }
        }

        return datoEnviar;
    }
    public String comparaNumeros(String datoTemp){
        String datoEnviar ="";
        for(int i = 0; i < datoTemp.length();i++){
            Character compara = datoTemp.charAt(i);
            if((compara >= '0' && compara <= '9')){
                datoEnviar = datoEnviar+""+compara;
            }
        }

        return datoEnviar;
    }



}
