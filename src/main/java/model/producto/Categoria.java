package model.producto;

public class Categoria {

    private int id;
    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;

    // Constructor para categoría nueva
    public Categoria(String nombre,
                     String descripcion,
                     EstadoCategoria estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Constructor para recuperar categoría de la BD
    public Categoria(int id,
                     String nombre,
                     String descripcion,
                     EstadoCategoria estado) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Sin setter porque SQLite asigna el ID
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoCategoria getEstado() {
        return estado;
    }

    public void setEstado(EstadoCategoria estado) {
        this.estado = estado;
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
