package general.me.edu.dgtmovil.objetos;

/**
 * Created by ADMIN on 01/07/2016.
 */

public class ReporteEstudiantes {
    String documento;
    String nombres;
    String apellidos;
    String curso;
    String firma;

    public ReporteEstudiantes(String documento, String nombres, String apellidos, String curso, String firma) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.curso = curso;
        this.firma = firma;
    }

    public ReporteEstudiantes() {
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
