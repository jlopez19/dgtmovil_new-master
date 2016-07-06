package general.me.edu.dgtmovil.objetos;

/**
 * Created by ADMIN on 30/06/2016.
 */

public class RespuestaReporte {
    String datotexto, codigo_formulario, id_preguntaopcion, datobinario;

    public RespuestaReporte() {

    }

    public RespuestaReporte(String datotexto, String codigo_formulario, String id_preguntaopcion, String datobinario) {
        this.datotexto = datotexto;
        this.codigo_formulario = codigo_formulario;
        this.id_preguntaopcion = id_preguntaopcion;
        this.datobinario = datobinario;
    }

    public String getDatobinario() {
        return datobinario;
    }

    public void setDatobinario(String datobinario) {
        this.datobinario = datobinario;
    }

    public String getDatotexto() {
        return datotexto;
    }

    public void setDatotexto(String datotexto) {
        this.datotexto = datotexto;
    }

    public String getCodigo_formulario() {
        return codigo_formulario;
    }

    public void setCodigo_formulario(String codigo_formulario) {
        this.codigo_formulario = codigo_formulario;
    }

    public String getId_preguntaopcion() {
        return id_preguntaopcion;
    }

    public void setId_preguntaopcion(String id_preguntaopcion) {
        this.id_preguntaopcion = id_preguntaopcion;
    }
}
