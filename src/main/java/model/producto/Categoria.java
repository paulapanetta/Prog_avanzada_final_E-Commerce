package model.producto;

public class Categoria {

    private int id;
    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;

    public Categoria(String nombre,
                     String descripcion,
                     EstadoCategoria estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Categoria(int id,
                     String nombre,
                     String descripcion,
                     EstadoCategoria estado) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoCategoria getEstado() {return estado;}

    public void setEstado(EstadoCategoria estado) {
        this.estado = estado;
    }

    public void mostrarInformacion() {
        System.out.printf(
                "[%d] %-20s | %-45s | Estado: %s%n",
                id,
                nombre,
                descripcion,
                estado
        );
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }

}