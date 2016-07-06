package general.me.edu.dgtmovil.dgtmovil;

/**
 * Created by ADMIN on 17/06/2016.
 */

public class ItemFormulario {

    String titulo;
    String descripcion;

    public ItemFormulario(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public ItemFormulario() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
