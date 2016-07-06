package general.me.edu.dgtmovil.objetos;

/**
 * Created by Jennyfer Lopez on 29/06/2016.
 */

public class Asistentes {

    String nombres;
    String cargo;
    String firma;

    public Asistentes( String nombres, String cargo, String firma) {
        this.nombres = nombres;
        this.cargo = cargo;
        this.firma = firma;
    }

    public Asistentes() {
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
